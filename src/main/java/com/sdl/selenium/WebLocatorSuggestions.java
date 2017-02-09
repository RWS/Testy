package com.sdl.selenium;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class WebLocatorSuggestions {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorSuggestions.class);

    private static boolean suggestAttributes = false;

    private static SearchType[] textGroup = {
            SearchType.EQUALS,
            SearchType.STARTS_WITH,
            SearchType.CONTAINS
    };
    private static SearchType[] childGroup = {
            SearchType.CHILD_NODE,
            SearchType.DEEP_CHILD_NODE_OR_SELF,
            SearchType.DEEP_CHILD_NODE,
            SearchType.CONTAINS_ALL,
            SearchType.CONTAINS_ALL_CHILD_NODES,
            SearchType.CONTAINS_ANY,
            SearchType.HTML_NODE
    };

    public static boolean isSuggestAttributes() {
        return suggestAttributes;
    }

    public static void setSuggestAttributes(boolean suggestAttributes) {
        WebLocatorSuggestions.suggestAttributes = suggestAttributes;
    }

    private static String getMatchedElementsHtml(WebLocator webLocator) {
        String result = "";

        for (WebElement element : webLocator.findElements()) {

            String outerHtml = element.getAttribute("outerHTML");
            String innerHtml = element.getAttribute("innerHTML");
            if (innerHtml.length() < 50) {
                result = result.concat(outerHtml).concat("\n");
            } else {
                result = result.concat(outerHtml.replace(innerHtml, "...")).concat("\n");
            }
        }

        return result.trim();
    }

    public static void getPageSuggestions(WebLocator view) {

        Map<String, WebLocator> webLocatorMap = WebLocatorUtils.webLocatorAsMap(view);

        if (webLocatorMap.size() == 0) {
            getSuggestion(view);
        } else {
            getPageSuggestions(webLocatorMap);
        }
    }

    private static void getPageSuggestions(Map<String, WebLocator> webLocatorMap) {
        boolean allElementsExist = true;

        for (String key : webLocatorMap.keySet()) {

            WebLocator webLocator = webLocatorMap.get(key);
            if (!webLocator.isElementPresent()) {
                allElementsExist = false;
                LOGGER.info("{} not found in page.", key);
                getPageSuggestions(webLocator);
            }
        }

        if (allElementsExist) {
            LOGGER.info("All elements are present in the page.");
        }
    }

    public static WebLocator getSuggestion(WebLocator webLocator) {
        LOGGER.debug("getSuggestion >> enter");
        WebLocator suggestion = getElementSuggestion(webLocator);
        LOGGER.debug("getSuggestion << exit");
        return suggestion;
    }

    private static WebLocator getElementSuggestion(WebLocator originalWebLocator) {

        WebLocator webLocator = getClone(originalWebLocator);
        if(webLocator == null) {
            return null;
        }

        if (webLocator.currentElement != null || webLocator.isElementPresent()) {
            if (webLocator.currentElement.isDisplayed()) {
                LOGGER.debug("The element already exists: {}", WebLocatorUtils.getHtmlTree(webLocator));
            } else {
                LOGGER.info("The element already exists but it is not visible: {}", WebLocatorUtils.getHtmlTree(webLocator));
            }
            return webLocator;
        }

        WebLocator parent = webLocator.getPathBuilder().getContainer();
        if (parent != null && !parent.isElementPresent()) {
            LOGGER.warn("The container ({}) of this webLocator ({}) was not found.", parent.getXPath(), webLocator.getXPath());
            return null;
        }

        //if the WebLocator should have a label check that it actually exists and try to offer suggestions if it doesn't.
        if (webLocator.getPathBuilder().getLabel() != null) {
            WebLocator result = suggestLabelCorrections(webLocator);
            if (result != null) {
//                return getElementSuggestion(result);
                return result;
            }
        }

        SearchType[] solution = suggestTextSearchType(webLocator);
        if (solution != null) {
            LOGGER.warn("Found the element using text search type {}", Arrays.toString(solution));
            webLocator.setSearchTextType(solution);
            return webLocator;
        }

        solution = suggestTitleSearchType(webLocator);
        if (solution != null) {
            LOGGER.warn("Found the element using title search type {}", Arrays.toString(solution));
            webLocator.setSearchTitleType(solution);
            return webLocator;
        }

        if(isSuggestAttributes()) {
            WebLocator locator = suggestAttributeSubsets(webLocator);
            if(locator == null) {
                LOGGER.debug("No suggestions found for {}", originalWebLocator);
            }
            return locator;
        } else {
            LOGGER.debug("No suggestions found for {}", originalWebLocator);
            return null;
        }
    }

    private static WebLocator getClone(WebLocator originalWebLocator) {
        try {
            WebLocator webLocator = originalWebLocator.getClass().newInstance();
            XPathBuilder builder = (XPathBuilder) originalWebLocator.getPathBuilder().clone();
            webLocator.setPathBuilder(builder);
            return webLocator;
        } catch (IllegalAccessException | InstantiationException | CloneNotSupportedException e) {
            LOGGER.error("Error while cloning the WebLocator: " + e.getMessage());
        }
        return null;
    }

    /**
     * @return True if everything is ok and the desired element was found, false otherwise.
     */
    private static WebLocator suggestLabelCorrections(WebLocator webLocator) {

        XPathBuilder xPathBuilder = webLocator.getPathBuilder();

        String label = xPathBuilder.getLabel();

        List<SearchType> searchTypes = xPathBuilder.getSearchLabelType();
        //this is the hardcoded value for the default search label type
        if (searchTypes.isEmpty()) {
            searchTypes.add(SearchType.EQUALS);
        }
        SearchType[] labelSearchTypes = new SearchType[searchTypes.size()];
        searchTypes.toArray(labelSearchTypes);

        WebLocator labelLocator = new WebLocator(xPathBuilder.getContainer())
                .setRenderMillis(0)
                .setText(label, labelSearchTypes)
                .setTag(xPathBuilder.getLabelTag());

        if (labelLocator.isElementPresent()) {

            LOGGER.info("Found the label: {}", getMatchedElementsHtml(labelLocator));

            String tag = webLocator.getPathBuilder().getTag();

            WebLocator labelPosition = new WebLocator(labelLocator)
                    .setElxPath(xPathBuilder.getLabelPosition() + tag);

            if (labelPosition.isElementPresent()) {
                LOGGER.info("'{}' elements found at the specified label position: {}", tag, getMatchedElementsHtml(labelPosition));
            } else {
                LOGGER.info("No '{}' elements found at the specified label position: {}", tag, xPathBuilder.getLabelPosition());

                labelPosition.setElxPath(xPathBuilder.getLabelPosition() + "*");
                if (labelPosition.isElementPresent()) {
                    LOGGER.warn("All elements found at the specified label position: {}", getMatchedElementsHtml(labelPosition));
                    webLocator.setTag("*");
                    return webLocator;
                }
            }

        } else {
            LOGGER.info("Could not find the label '{}' using  tag '{}' and search type '{}'",
                    label, xPathBuilder.getLabelTag(), Arrays.toString(labelSearchTypes));

            SearchType[] solution = suggestTextSearchType(labelLocator);
            if (solution != null) {
                LOGGER.warn("But found it using search types {}", Arrays.toString(solution));
                webLocator.setLabel(label, solution);
                return webLocator;
            } else {
                labelLocator.setTag("*");
                if(labelLocator.isElementPresent()) {
                    LOGGER.warn("But found it using tag *.");
                    webLocator.setLabelTag("*");
                    return webLocator;
                } else {
                    SearchType[] solution2 = suggestTextSearchType(labelLocator);
                    if (solution2 != null) {
                        LOGGER.warn("But found it using tag * and search types {}", Arrays.toString(solution2));
                        webLocator.setLabel(label, solution2);
                        webLocator.setLabelTag("*");
                        return webLocator;
                    }
                }
            }
        }

        return null;
    }

    public static SearchType[] suggestTextSearchType(WebLocator webLocator) {

        for (SearchType textSearchType : textGroup) {

            SearchType[] solution1 = {textSearchType, SearchType.TRIM};
            webLocator.setSearchTextType(solution1);

            if (webLocator.isElementPresent()) {
                return solution1;
            }

            for (SearchType childSearchType : childGroup) {

                SearchType[] solution2 = {textSearchType, childSearchType, SearchType.TRIM};
                webLocator.setSearchTextType(solution2);

                if (webLocator.isElementPresent()) {
                    return solution2;
                }
            }
        }

        return null;
    }

    public static SearchType[] suggestTitleSearchType(WebLocator webLocator) {

        for (SearchType textSearchType : textGroup) {

            SearchType[] solution1 = {textSearchType, SearchType.TRIM};
            webLocator.setSearchTitleType(solution1);

            if (webLocator.isElementPresent()) {
                return solution1;
            }

            for (SearchType childSearchType : childGroup) {

                SearchType[] solution2 = {textSearchType, childSearchType, SearchType.TRIM};
                webLocator.setSearchTitleType(solution2);

                if (webLocator.isElementPresent()) {
                    return solution2;
                }
            }
        }

        return null;
    }

    private static WebLocator suggestAttributeSubsets(WebLocator view) {

        List<String> nonNullFields = getNonNullFields(view.getPathBuilder());

        String[] originalFields = new String[nonNullFields.size()];
        nonNullFields.toArray(originalFields);

        int k = nonNullFields.size() - 1;

        while (k > 0) {
            try {
                proposeSolutions(originalFields, k, 0, new String[k], view);
            } catch (SolutionFoundException e) {
                return view;
            }
            k--;
        }

        return null;
    }

    private static List<String> getNonNullFields(XPathBuilder builder) {

        String[] availableFieldsArray = {
                "id",
                "elPath",
                "baseCls",
                "cls",
                "name",
                "text",
                "style",
                "title",
                "type",
                "visibility",
                "root",
                "tag",
                "position",
                "resultIdx"
        };

        List<String> availableFields = Arrays.asList(availableFieldsArray);

        //create a list of non null fields
        List<String> nonNullFields = new LinkedList<>();

        for (Field f : builder.getClass().getDeclaredFields()) {

            if (availableFields.contains(f.getName())) {

                f.setAccessible(true);

                try {
                    Object value = f.get(builder);
                    if (value != null) {
                        nonNullFields.add(f.getName());
                    }
                } catch (IllegalAccessException e) {
                    //will not get here because of f.setAccessible(true);
                }
            }
        }
        return nonNullFields;
    }

    /**
     * Generates combinations of all objects from the dataSet taken k at a time.
     * Each combination is a possible solution that must be validated.
     */
    private static void proposeSolutions(String[] dataSet, int k, int startPosition, String[] result, WebLocator webLocator) throws SolutionFoundException {

        if (k == 0) {
            validateSolution(webLocator, differences(dataSet, result));
            return;
        }

        for (int i = startPosition; i <= dataSet.length - k; i++) {
            result[result.length - k] = dataSet[i];
            proposeSolutions(dataSet, k - 1, i + 1, result, webLocator);
        }
    }

    /**
     * Nullifies all fields of the builder that are not part of the proposed solution and validates the new xPath.
     */
    private static void validateSolution(WebLocator webLocator, String[] differences) throws SolutionFoundException {

        //all changes will be recorded here in a human readable form
        StringBuilder sb = new StringBuilder();

        //all changes will be kept here and restored after the xPath is generated.
        Map<String, Object> backup = new HashMap<>();

        XPathBuilder builder = webLocator.getPathBuilder();

        for (String fieldName : differences) {
            try {

                Field field = XPathBuilder.class.getDeclaredField(fieldName);
                field.setAccessible(true);

                Object fieldValue = field.get(builder);

                switch (fieldName) {
                    case "root":
                        field.set(builder, "//");
                        break;
                    case "tag":
                        field.set(builder, "*");
                        break;
                    case "visibility":
                        field.set(builder, false);
                        break;
                    case "labelTag":
                        field.set(builder, "label");
                        break;
                    case "labelPosition":
                        field.set(builder, WebLocatorConfig.getDefaultLabelPosition());
                        break;
                    case "position":
                        field.set(builder, -1);
                        break;
                    case "resultIdx":
                        field.set(builder, -1);
                        break;
                    default:
                        field.set(builder, null);
                }

                backup.put(fieldName, fieldValue);
                sb.append(String.format("set%s(\"%s\") and ", capitalize(fieldName), fieldValue));

            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                //this is not a valid solution. nothing to do here.
            }
        }

        int matches = webLocator.size();

        if (matches > 0) {

            //remove last 'and' from the message
            if (sb.lastIndexOf("and") > 0) {
                sb.replace(sb.lastIndexOf("and"), sb.length(), "");
            }

            LOGGER.warn("Found {} matches by removing {}: \n{}", matches, sb.toString(), getMatchedElementsHtml(webLocator));
            throw new SolutionFoundException();
        }

        //restore the original builder from the backup
        for (String fieldName : differences) {
            try {

                Field field = XPathBuilder.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(builder, backup.get(fieldName));

            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SolutionFoundException extends Exception{}

    /**
     * Returns an array that contains all differences between first and second.
     */
    private static String[] differences(String[] first, String[] second) {

        String[] sortedFirst = Arrays.copyOf(first, first.length); // O(n)
        String[] sortedSecond = Arrays.copyOf(second, second.length); // O(m)
        Arrays.sort(sortedFirst); // O(n log n)
        Arrays.sort(sortedSecond); // O(m log m)

        int firstIndex = 0;
        int secondIndex = 0;

        LinkedList<String> diffs = new LinkedList<>();

        while (firstIndex < sortedFirst.length && secondIndex < sortedSecond.length) {
            int compare = (int) Math.signum(sortedFirst[firstIndex].compareTo(sortedSecond[secondIndex]));

            switch (compare) {
                case -1:
                    diffs.add(sortedFirst[firstIndex]);
                    firstIndex++;
                    break;
                case 1:
                    diffs.add(sortedSecond[secondIndex]);
                    secondIndex++;
                    break;
                default:
                    firstIndex++;
                    secondIndex++;
            }
        }

        String[] strDups = new String[diffs.size()];

        return diffs.toArray(strDups);
    }

    private static String capitalize(final String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
