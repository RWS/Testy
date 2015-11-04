package com.sdl.selenium;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Beni Lar on 10/29/2015.
 */
public class WebLocatorSuggestions {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorSuggestions.class);

    private static Long countXPathMatches(XPathBuilder builder) {

        String script = "return (function(t){for(var e=document.evaluate(t,document,null,XPathResult.ORDERED_NODE_ITERATOR_TYPE,null),u=e.iterateNext(),n=0;u;)n++,u=e.iterateNext();return n})(\"" + builder.getXPath() + "\")";

        return (Long) WebLocatorUtils.doExecuteScript(script);
    }

    public static String getXPathMatches(WebLocator view) {

        return getXPathMatches(view.getPathBuilder());
    }

    private static String getXPathMatches(XPathBuilder builder) {

        String script = "return (function(e){for(var n=document.evaluate(e,document,null,XPathResult.ORDERED_NODE_ITERATOR_TYPE,null),t=\"\",l=n.iterateNext();l;)t+=l.outerHTML+\"\\n\",l=n.iterateNext();return t})(\"" + builder.getXPath() + "\")";

        String result = (String) WebLocatorUtils.doExecuteScript(script);

        return result.trim();
    }

    public static void getSuggestions(WebLocator webLocator) {

        if (webLocator.isElementPresent()) {
            LOGGER.info("The element already exists.");
            return;
        }

        WebLocator parent = webLocator.getPathBuilder().getContainer();
        if (parent != null && !parent.isElementPresent()) {
            LOGGER.info("The parent of this webLocator was not found.");
            return;
        }

        //if the WebLocator should have a label check that it actually exists and try to offer suggestions if it doesn't.
        if (webLocator.getPathBuilder().getLabel() != null) {
            suggestLabelCorrections(webLocator);
        }

        SearchType[] solution = suggestTextSearchType(webLocator);
        if (solution != null) {
            LOGGER.info("Found the element using search type {}", Arrays.toString(solution));
        } else {
            suggestAttributeSubsets(webLocator);
        }
    }

    private static void suggestLabelCorrections(WebLocator webLocator) {

        XPathBuilder xPathBuilder = webLocator.getPathBuilder();

        String label = xPathBuilder.getLabel();

        List<SearchType> searchTypes = xPathBuilder.getSearchLabelType();
        //this is the hardcoded value for the default search label type
        if (searchTypes.isEmpty()) {
            searchTypes.add(SearchType.EQUALS);
        }
        SearchType[] labelSearchTypes = new SearchType[searchTypes.size()];
        searchTypes.toArray(labelSearchTypes);

        WebLocator textLocator = new WebLocator(xPathBuilder.getContainer())
                .setText(label, labelSearchTypes)
                .setTag(xPathBuilder.getLabelTag());

        if (textLocator.exists()) {

            LOGGER.info("Found the label: {}", getXPathMatches(textLocator));

            String tag = webLocator.getPathBuilder().getTag();

            WebLocator labelPosition = new WebLocator(textLocator)
                    .setElPath(xPathBuilder.getLabelPosition() + tag);

            if (labelPosition.isElementPresent()) {
                LOGGER.info("'{}' elements found at the specified label position: {}", tag, getXPathMatches(labelPosition));
            } else {
                LOGGER.info("No '{}' elements found at the specified label position: {}", tag, xPathBuilder.getLabelPosition());

                labelPosition.setElPath(xPathBuilder.getLabelPosition() + "*");
                if (labelPosition.isElementPresent()) {
                    LOGGER.info("All elements found at the specified label position: {}", getXPathMatches(labelPosition));
                }
            }

        } else {
            LOGGER.info("Could not find the label '{}' using  tag '{}' and search type '{}'",
                    label, xPathBuilder.getLabelTag(), Arrays.toString(labelSearchTypes));

            SearchType[] solution = suggestTextSearchType(textLocator);
            if (solution != null) {
                LOGGER.info("But found it using search types {}", Arrays.toString(solution));
            }

        }
    }

    public static SearchType[] suggestTextSearchType(WebLocator webLocator) {

        SearchType[] textGroup = {
                SearchType.EQUALS,
                SearchType.CONTAINS,
                SearchType.STARTS_WITH
        };

        SearchType[] childGroup = {
                SearchType.CHILD_NODE,
                SearchType.DEEP_CHILD_NODE_OR_SELF,
                SearchType.DEEP_CHILD_NODE,
                SearchType.CONTAINS_ALL,
                SearchType.CONTAINS_ALL_CHILD_NODES,
                SearchType.CONTAINS_ANY,
                SearchType.HTML_NODE
        };

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

    private static void suggestAttributeSubsets(WebLocator view) {
        XPathBuilder builder = view.getPathBuilder();

        List<String> nonNullFields = getNonNullFields(builder);

        String[] originalFields = new String[nonNullFields.size()];
        nonNullFields.toArray(originalFields);

        int k = nonNullFields.size() - 1;

        StringBuilder suggestions = new StringBuilder();

        while (suggestions.length() == 0 && k > 0) {
            proposeSolutions(originalFields, k, 0, new String[k], builder, suggestions);
            k--;
        }

        LOGGER.info(suggestions.toString());
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
                "infoMessage",
                "container",
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
    private static void proposeSolutions(String[] dataSet, int k, int startPosition, String[] result, XPathBuilder builder, StringBuilder suggestions) {

        if (k == 0) {
            validateSolution(builder, differences(dataSet, result), suggestions);
            return;
        }

        for (int i = startPosition; i <= dataSet.length - k; i++) {
            result[result.length - k] = dataSet[i];
            proposeSolutions(dataSet, k - 1, i + 1, result, builder, suggestions);
        }
    }

    /**
     * Nullifies all fields of the builder that are not part of the proposed solution and validates the new xPath.
     */
    private static void validateSolution(XPathBuilder builder, String[] differences, StringBuilder suggestions) {

        //all changes will be recorded here in a human readable form
        StringBuilder sb = new StringBuilder();

        //all changes will be kept here and restored after the xPath is generated.
        Map<String, Object> backup = new HashMap<>();

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

        Long foundMatches = countXPathMatches(builder);

        if (foundMatches > 0) {

            //remove last 'and' from the message
            if (sb.lastIndexOf("and") > 0) {
                sb.replace(sb.lastIndexOf("and"), sb.length(), "");
            }

            suggestions.append(String.format("Found %s matches by removing %s: ",
                    foundMatches,
                    sb));

            suggestions.append(getXPathMatches(builder));
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
