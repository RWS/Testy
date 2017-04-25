package com.sdl.selenium;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public final class WebLocatorUtils extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorUtils.class);

    private WebLocatorUtils(){

    }

    /**
     * @deprecated use WebDriverConfig.getDriver().getPageSource();
     * @return resource of page
     */
    @Deprecated
    public static String getPageSource() {
        return WebDriverConfig.getDriver().getPageSource();
    }

    public static Object doExecuteScript(String script, Object... objects) {
        return executor.executeScript(script, objects);
    }

    /**
     * Scroll to element
     * @param element type WebLocator
     */
    public static void scrollToWebLocator(WebLocator element) {
        doExecuteScript("arguments[0].scrollIntoView(true);", element.getWebElement());
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
            appendLocatorXPath(builder, "current_view", view, true);

            for (String locatorName : map.keySet()) {
                WebLocator locator = map.get(locatorName);
                appendLocatorXPath(builder, locatorName, locator, true);
            }
        }

        builder.append("\n");
        String log = builder.toString();
        LOGGER.info(log);
        return log;
    }

    // TODO add in anonymous function and simplify var names
    private static void appendLocatorXPath(StringBuilder builder, String locatorName, WebLocator locator, boolean compact) {
        String path = locator.getXPath();
        String xpathLocatorVar = "xpath_" + locatorName;
        String newLine = compact ? "" : "\n";
        builder.append("var ").append(xpathLocatorVar).append("Path = \"").append(path).append("\";").append(newLine);
        builder.append("var ").append(xpathLocatorVar).append(" = $x(").append(xpathLocatorVar).append("Path);").append(newLine);
        builder.append("if (").append(xpathLocatorVar).append("[0]) {").append(newLine);
        builder.append("  console.info('").append(locatorName).append("', ").append(xpathLocatorVar).append("[0]);").append(newLine);
        builder.append("  if (").append(xpathLocatorVar).append(".length > 1) console.warn('  found more elements', ").append(xpathLocatorVar).append(");").append(newLine);
        builder.append("} else {").append(newLine);
        builder.append("  console.error('").append(locatorName).append("', ' - not found!');").append(newLine);
        builder.append("  console.warn(\"  \"+").append(xpathLocatorVar).append("Path);").append(newLine);
        builder.append("}").append(newLine).append(newLine);
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

            elements.sort(Comparator.reverseOrder());
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
