package com.sdl.selenium;

import com.google.common.base.Strings;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public final class WebLocatorUtils extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorUtils.class);

    private WebLocatorUtils() {
    }

    public static Object doExecuteScript(String script, Object... objects) {
        return executor.executeScript(script, objects);
    }

    /**
     * Scroll to element
     *
     * @param element type WebLocator
     */
    public static void scrollToWebLocator(WebLocator element) {
        if (element.isPresent()) {
            doExecuteScript("arguments[0].scrollIntoView(true);", element.getWebElement());
//            doExecuteScript("var e = arguments[0]; e.scrollIntoView(); var rect = e.getBoundingClientRect(); return {'x': rect.left, 'y': rect.top};", element.getWebElement());
        }
    }

    /**
     * <p>Generate JS script to validate and test xpath of all WebLocator's.
     * Just copy next lines and paste them in firebug, then press Run.</p>
     * <p>Usage example:</p>
     * <pre>{@code
     * WebLocatorUtils.getXPathScript(new LoginView());
     * }</pre>
     *
     * @param view any instance of WebLocator
     *             <p>Views must respect the JavaBeans conventions</p>
     * @return script
     */
    public static String getXPathScript(WebLocator view) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n");
        Map<String, WebLocator> map = webLocatorAsMap(view);

        if (map.size() == 0) {
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

        for (Field field : webLocator.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object fieldInstance = null;
            try {
                fieldInstance = field.get(webLocator);
                if (fieldInstance instanceof WebLocator) {
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

        if (webLocator.getWebElement() != null) {

            WebElement parent = webLocator.currentElement;

            List<String> elements = new LinkedList<>();

            while (!parent.getTagName().equals("html")) {

                String outerHtml = parent.getAttribute("outerHTML");
                String innerHtml = parent.getAttribute("innerHTML");

                String html = outerHtml.substring(0, outerHtml.indexOf(innerHtml));

                elements.add(html);

                parent = parent.findElement(By.xpath(".."));
            }

            elements.sort(Comparator.reverseOrder());
            String indent = "\n";
            for (String elem : elements) {
                result = result.concat(indent).concat(elem);
                indent = indent.concat("\t");
            }
        }

        return result;
    }

    public static String discoverElements(WebLocator webLocator) {
        String result = "";
        if (webLocator.getWebElement() != null) {
            WebElement parent = webLocator.getWebElement();
            List<WebElement> branch = new LinkedList<>();
            List<String> elements = new LinkedList<>();
            int index = 0;
            boolean found = false;
            boolean foundField = false;
            String label = "";
            while (parent != null) {
                if (index > 0) {
                    String aClass = parent.getAttribute("class");
                    if (!aClass.contains("x-hidden-offsets") && !aClass.contains("x-hidden-clip") && !aClass.contains("Wrap")
                            && !aClass.contains("wrap") && !aClass.contains("-inner") && !aClass.contains("-outerCt")) {
                        String tag = parent.getTagName();
                        String text = parent.getText().split("\\n")[0];
                        StringBuilder element = new StringBuilder();
                        if (aClass.contains("x-panel ")) {
                            element.append("Panel panel = new Panel(null");
                            addText(text, element);
                            found = true;
                        } else if (aClass.contains("x-field ") || foundField) {
                            foundField = true;
                            if (tag.equals("label")) {
                                label = text.replace(":", "");
                            } else if (tag.equals("input") || tag.equals("textarea")) {
                                String attribute = parent.getAttribute("data-componentid");
                                String componentId = "";
                                if (!Strings.isNullOrEmpty(attribute)) {
                                    componentId = attribute.split("-")[0];
                                }
                                switch (componentId) {
                                    case "numberfield":
                                    case "textfield":
                                        element.append("TextField field = new TextField(this");
                                        addText(label, element);
                                        found = true;
                                        label = "";
                                        foundField = false;
                                        break;
                                    case "timefield":
                                    case "checkbox":
                                        element.append("CheckBox checkBox = new CheckBox(this");
                                        addText(label, element);
                                        found = true;
                                        label = "";
                                        foundField = false;
                                        break;
                                    case "combo":
                                        element.append("ComboBox comboBox = new ComboBox(this");
                                        addText(label, element);
                                        found = true;
                                        label = "";
                                        foundField = false;
                                        break;
                                    case "datefield":
                                        element.append("DateField dateField = new DateField(this");
                                        addText(label, element);
                                        found = true;
                                        label = "";
                                        foundField = false;
                                        break;
                                    case "textarea":
                                        element.append("TextArea textarea = new TextArea(this");
                                        addText(label, element);
                                        found = true;
                                        label = "";
                                        foundField = false;
                                        break;
                                    default:
                                        LOGGER.info("Not associated these classes::" + text + "|" + aClass);
                                        break;
                                }
                            } else {
                                if (!foundField) {
                                    LOGGER.info("Not associated these classes:::" + text + "|" + aClass);
                                }
                            }
                        } else if (aClass.contains("x-btn ")) {
                            element.append("Button button = new Button(this");
                            addText(text, element);
                            found = true;
                        } else {
                            LOGGER.info("Not associated these classes: " + aClass);
                            found = false;
                        }
                        if (found) {
                            elements.add(element.toString());
                        }
                    }
                }
                index++;
                List<WebElement> webElements = parent.findElements(By.xpath("./*"));
                int size = webElements.size();
                if (size == 0 && !branch.isEmpty()) {
                    found = false;
                    parent = branch.get(0);
                    branch.remove(0);
                    continue;
                }
                if (size > 1) {
                    int i = found ? 2 : 1;
                    for (; i < size; i++) {
                        branch.add(0, webElements.get(i));
                    }
                    parent = webElements.isEmpty() ? null : webElements.get(found ? 1 : 0);
                } else {
                    parent = webElements.isEmpty() ? null : webElements.get(0);
                }
            }
            result = "\n" + String.join("\n", elements);
        }
        return result;
    }

    private static void addText(String label, StringBuilder element) {
        if (!Strings.isNullOrEmpty(label)) {
            element.append(", \"").append(label).append("\"");
        }
        element.append(");");
    }

    private static void setupJs() {
        // Check for jQuery on the page, add it if need be
        doExecuteScript("if (!window.jQuery) {" +
                "var jquery = document.createElement('script'); jquery.type = 'text/javascript';" +
                "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';" +
                "document.getElementsByTagName('head')[0].appendChild(jquery);" +
                "}");

        // Use jQuery to add jquery-growl to the page
        doExecuteScript("$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

        // Use jQuery to add jquery-growl styles to the page
        doExecuteScript("$('head').append('<link rel=\"stylesheet\" href=\"http://the-internet.herokuapp.com/css/jquery.growl.css\" type=\"text/css\" />');");

        // jquery-growl w/ no frills
        doExecuteScript("$.growl({ title: 'GET', message: '/' });");
    }

    public static void showPopup(String msg) {
        setupJs();
        doExecuteScript("$.growl.notice({ title: 'Notice', message: '" + msg + "' });");
    }

    public static void showNotification(String title, String msg) {
        doExecuteScript("SDL.Notification.success('" + title + "', '" + msg + "');");
    }

    public static void main(String[] args) {
        WebLocator l = new WebLocator().setText("Save");
        getXPathScript(l);
    }
}
