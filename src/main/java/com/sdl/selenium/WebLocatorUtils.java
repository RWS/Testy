package com.sdl.selenium;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author nmatei
 * @since 2/25/14
 */
public final class WebLocatorUtils extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorUtils.class);

    private WebLocatorUtils(){

    }

    public static String getPageHtmlSource() {
        return executor.getHtmlSource();
    }

    public static Object doExecuteScript(String script, Object... objects) {
        return executor.executeScript(script, objects);
    }


    /**
     * <p>Generate JS script to validate and test xpath of all WebLocator's.
     * Just copy next lines and paste them in firebug, then press Run.</p>
     * <p>Usage example:</p>
     * <pre>{@code
     * WebLocatorUtils.getXPathScript(new LoginView());
     * }</pre>
     * @param view any instance of WebLocator
     * <p>Views must respect the JavaBeans conventions</p>
     * @return script
     */
    public static String getXPathScript(WebLocator view) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n");
        Map<String, WebLocator> map = webLocatorAsMap(view);


        if(map.size() == 0) {
            builder.append(getFirebugXPath(view));

            Long count = countXPathMatches(view);

            if (count == 0) {

                builder.append("\n\nNo matches were found for the xPath above. Here are some suggestions...");
                builder.append(getSuggestions(view));

            } else {

                builder.append(String.format("\n\nFound %s matches for the xPath above.", count));
            }
        } else {
            builder.append("// Run next lines in firebug to see if all elements are present in your page").append("\n\n");
            appendLocatorXPath(builder, "current_view", view);

            for (String locatorName : map.keySet()) {
                WebLocator locator = map.get(locatorName);
                appendLocatorXPath(builder, locatorName, locator);
            }
        }

        builder.append("\n");
        String log = builder.toString();
        LOGGER.info(log);
        return log;
    }

    private static void appendLocatorXPath(StringBuilder builder, String locatorName, WebLocator locator) {
        //builder.append("// ").append(locatorName).append(" : ").append(locator.toString()).append("\n");
        String xpath = getFirebugXPath(locator);
        String xpathLocatorVar = "xpath_" + locatorName;
        builder.append("var ").append(xpathLocatorVar).append(" = ").append(xpath).append(";\n");
        builder.append("if (").append(xpathLocatorVar).append("[0]) {");
        builder.append("  console.info('").append(locatorName).append("', ").append(xpathLocatorVar).append("[0]);\n");
        builder.append("if (").append(xpathLocatorVar).append(".length > 1) console.warn('  found more elements', ").append(xpathLocatorVar).append(");");
        builder.append("} else {\n");
        builder.append("  console.error('").append(locatorName).append("', ' - not found!');\n");
        builder.append("  console.warn(\"  ").append(locator.getXPath()).append("\");\n");
        builder.append("}\n\n");
    }

    private static String getFirebugXPath(WebLocator locator) {
        return "$x(\"" + locator.getXPath() + "\")";
    }

    private static Map<String, WebLocator> webLocatorAsMap(WebLocator webLocator) {
        Map<String, WebLocator> result = new HashMap<>();
        BeanInfo info = null;
        try {
            Class<? extends WebLocator> aClass = webLocator.getClass();
            info = Introspector.getBeanInfo(aClass);
        } catch (IntrospectionException e) {
            LOGGER.error("IntrospectionException", e);
        }
        if (info != null) {
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                Class<?> propertyType = pd.getPropertyType();
                String name = pd.getName();
                if (reader != null && !"class".equals(name) && !"container".equals(name)) {
                    try {
                        Class<?> c = Class.forName(propertyType.getName());
                        if(WebLocator.class.isAssignableFrom(c)){
                            //LOGGER.debug("found locator: {}, cls: {}", name, propertyType.getName());
                            WebLocator locator = null;
                            try {
                                locator = processWebLocator(reader.invoke(webLocator));
                            } catch (IllegalAccessException e) {
                                LOGGER.error("IllegalAccessException", e);
                            } catch (InvocationTargetException e) {
                                LOGGER.error("InvocationTargetException", e);
                            }
                            if (locator != null) {
                                result.put(name, locator);
                            }
                        }
                    } catch (ClassNotFoundException x) {
                    }
                }
            }
        }
        return result;
    }

    private static WebLocator processWebLocator(Object o) {
        WebLocator result = null;
        if (o instanceof WebLocator) {
            result = (WebLocator) o;
        }
        return result;
    }

    //suggestions related code

    public static Long countXPathMatches(WebLocator view) {

        return countXPathMatches(view.getPathBuilder());
    }

    private static Long countXPathMatches(XPathBuilder builder) {

        String script = "return (function(t){for(var e=document.evaluate(t,document,null,XPathResult.ORDERED_NODE_ITERATOR_TYPE,null),u=e.iterateNext(),n=0;u;)n++,u=e.iterateNext();return n})(\"" + builder.getXPath() + "\")";

        return (Long) doExecuteScript(script);
    }

    public static String getXPathMatches(WebLocator view) {

        return getXPathMatches(view.getPathBuilder());
    }

    private static String getXPathMatches(XPathBuilder builder) {

        String script = "return (function(e){for(var n=document.evaluate(e,document,null,XPathResult.ORDERED_NODE_ITERATOR_TYPE,null),t=\"\",l=n.iterateNext();l;)t+=l.outerHTML+\"\\n\",l=n.iterateNext();return t})(\"" + builder.getXPath() + "\")";

        return (String) doExecuteScript(script);
    }

    public static String getSuggestions(WebLocator view) {

        Long count = countXPathMatches(view);

        if (count > 0) {
            return "Found " + count + " matches for the given " + view.getClass().getSimpleName();
        }

        XPathBuilder builder = view.getPathBuilder();

        List<String> nonNullFields = getNonNullFields(builder);

        String[] originalFields = new String[nonNullFields.size()];
        nonNullFields.toArray(originalFields);

        int k = nonNullFields.size() - 1;
        //use array instead of object to work like a 'pass-by-reference'
        StringBuilder suggestions = new StringBuilder();

        while (suggestions.length() == 0 && k > 0) {
            proposeSolutions(originalFields, k, 0, new String[k], builder, suggestions);
            k--;
        }

        if (suggestions.length() == 0) {
            suggestions.append("Could not find any suggestions.");
        }

        return suggestions.toString();
    }

    private static List<String> getNonNullFields(XPathBuilder builder) {

        //this is the list of fields that are taken into consideration when generating suggestions
        List<String> availableFields = new ArrayList<>();
        availableFields.add("id");
        availableFields.add("elPath");
        availableFields.add("baseCls");
        availableFields.add("cls");
        availableFields.add("name");
        availableFields.add("text");
        availableFields.add("style");
        availableFields.add("title");
        availableFields.add("label");
        availableFields.add("type");
        availableFields.add("infoMessage");
        availableFields.add("container");
        //from this point on the fields have the initial value set. We must take this into consideration when validating
        //suggestions and revert to the initial value rather than null.
        availableFields.add("visibility");
        availableFields.add("root");
        availableFields.add("tag");
        availableFields.add("labelTag");
        availableFields.add("labelPosition");
        availableFields.add("position");
        availableFields.add("resultIdx");

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

            suggestions.append(String.format("\nFound %s matches by removing %s\n",
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

        while (firstIndex < sortedFirst.length && secondIndex < sortedSecond.length) { // O(n + m)
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

    public static void main(String[] args) {
        WebLocator l = new WebLocator().setText("Matei");
        getXPathScript(l);
    }
}
