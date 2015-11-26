package com.sdl.selenium;

import com.google.common.collect.Lists;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public static String getFirebugXPath(WebLocator locator) {
        return "$x(\"" + locator.getXPath() + "\")";
    }

    public static Map<String, WebLocator> webLocatorAsMap(WebLocator webLocator) {
        Map<String, WebLocator> result = new HashMap<>();

        for(Field field : webLocator.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldInstance = null;
            try {
                fieldInstance = field.get(webLocator);
                if(fieldInstance instanceof WebLocator) {
                    result.put(field.getName(), (WebLocator) fieldInstance);
                }
            } catch (IllegalAccessException e) {
                //should not get here because of field.setAccessible(true);
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

    public static String getHtmlTree(WebLocator webLocator) {
        String result = "";

        if(webLocator.currentElement != null || webLocator.isElementPresent()) {

            WebElement parent = webLocator.currentElement;

            List<String> elements = new LinkedList<>();

            while (!parent.getTagName().equals("html")){

                String outerHtml = parent.getAttribute("outerHTML");
                String innerHtml = parent.getAttribute("innerHTML");

                String html = outerHtml.substring(0, outerHtml.indexOf(innerHtml));

                elements.add(html);

                parent = parent.findElement(By.xpath(".."));
            }

            elements = Lists.reverse(elements);
            String indent = "\n";
            for(String elem : elements) {
                result = result.concat(indent).concat(elem);
                indent = indent.concat("\t");
            }
        }

        return result;
    }

    public static void main(String[] args) {
        WebLocator l = new WebLocator().setText("Save");
        getXPathScript(l);
    }
}
