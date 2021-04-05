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

    private static String getXType(WebElement el) {
        return executeExtJS(el, "c.xtype");
    }

    private static String executeExtJS(WebElement el, String scriptPartial) {
        String id = el.getAttribute("id");
        String value = null;
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(c){return " + scriptPartial + "})(window.Ext.getCmp('" + id + "'))";
            value = (String) WebLocatorUtils.doExecuteScript(script);
        }
        return value;
    }

    public static String discoverExtJs6Elements(WebLocator webLocator) {
        String result = "";
        if (webLocator.getWebElement() != null) {
            WebElement parent = webLocator.getWebElement();
            List<WebElement> branch = new LinkedList<>();
            List<String> elements = new LinkedList<>();
            int index = 0;
            boolean found = false;
            while (parent != null) {
                if (index > 0) {
                    String xType = getXType(parent);
                    if (!Strings.isNullOrEmpty(xType)) {
                        StringBuilder element = new StringBuilder();
                        if ("panel".equals(xType) || "form-fieldtypes".equals(xType)) {
                            String title = executeExtJS(parent, "c.title");
                            String name = Strings.isNullOrEmpty(title) ? "panel" : getVariable(title);
                            element.append("Panel ").append(name).append(" = new Panel(this");
                            found = false;
                            addText(title, element);
                        } else if ("grid".equals(xType) || "row-expander-grid".equals(xType) || "row-numberer".equals(xType) || "array-grid".equals(xType)) {
                            String title = executeExtJS(parent, "c.title");
                            String name = Strings.isNullOrEmpty(title) ? "grid" : getVariable(title);
                            element.append("Grid ").append(name).append(" = new Grid(this");
                            found = true;
                            addText(title, element);
                        } else if ("tab".equals(xType)) {
                            String title = executeExtJS(parent, "c.title");
                            String name = Strings.isNullOrEmpty(title) ? "tab" : getVariable(title);
                            element.append("Tab ").append(name).append(" = new Tab(this");
                            addText(title, element);
                            found = true;
                        } else if ("textfield".equals(xType) || "datetime".equals(xType) || "numberfield".equals(xType)) {
                            String label = executeExtJS(parent, "c.fieldLabel");
                            String name = Strings.isNullOrEmpty(label) ? "textField" : getVariable(label);
                            element.append("TextField ").append(name).append(" = new TextField(this");
                            addText(label, element);
                            found = true;
                        } else if ("checkboxfield".equals(xType)) {
                            String label = executeExtJS(parent, "c.fieldLabel");
                            String name = Strings.isNullOrEmpty(label) ? "checkBox" : getVariable(label);
                            element.append("CheckBox ").append(name).append(" = new CheckBox(this");
                            addText(label, element);
                            found = true;
                        } else if ("timefield".equals(xType)) {
                            String label = executeExtJS(parent, "c.fieldLabel");
                            String name = Strings.isNullOrEmpty(label) ? "timeField" : getVariable(label);
                            element.append("ComboBox ").append(name).append(" = new ComboBox(this");
                            addText(label, element);
                            found = true;
                        } else if (xType.contains("combobox")) {
                            String label = executeExtJS(parent, "c.fieldLabel");
                            String name = Strings.isNullOrEmpty(label) ? "comboBox" : getVariable(label);
                            element.append("ComboBox ").append(name).append(" = new ComboBox(this");
                            addText(label, element);
                            found = true;
                        } else if ("datefield".equals(xType)) {
                            String label = executeExtJS(parent, "c.fieldLabel");
                            String name = Strings.isNullOrEmpty(label) ? "dateField" : getVariable(label);
                            element.append("DateField ").append(name).append(" = new DateField(this");
                            addText(label, element);
                            found = true;
                        } else if (xType.contains("textarea")) {
                            String label = executeExtJS(parent, "c.fieldLabel");
                            String name = Strings.isNullOrEmpty(label) ? "textArea" : getVariable(label);
                            element.append("TextArea ").append(name).append(" = new TextArea(this");
                            addText(label, element);
                            found = true;
                        } else if ("displayfield".equals(xType)) {
                            String label = executeExtJS(parent, "c.fieldLabel");
                            String name = Strings.isNullOrEmpty(label) ? "displayField" : getVariable(label);
                            element.append("DisplayField ").append(name).append(" = new DisplayField(this");
                            addText(label, element);
                            found = true;
                        } else if ("button".equals(xType)) {
                            String text = executeExtJS(parent, "c.text");
                            String name = Strings.isNullOrEmpty(text) ? "button" : getVariable(text);;
                            element.append("Button ").append(name).append(" = new Button(this");
                            addText(text, element);
                            found = true;
                        } else if ("fieldset".equals(xType)) {
                            String title = executeExtJS(parent, "c.title");
                            String name = getVariable(title);
                            element.append("FieldSet ").append(name).append(" = new FieldSet(this");
                            addText(title, element);
                            found = true;
                        } else {
                            LOGGER.info("Not associated these xtype: " + xType);
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
                    int j = 0;
                    for (; i < size; i++) {
                        WebElement element = webElements.get(i);
                        String aClass = element.getAttribute("class");
                        if (!Strings.isNullOrEmpty(aClass) && !aClass.contains("x-hidden-offsets") && !aClass.contains("x-hidden-clip")
                                && !aClass.contains("x-grid-") && !aClass.contains("x-mask")) {
                            branch.add(j, element);
                            j++;
                        }
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

    private static String getVariable(String label) {
        String[] strings = label.split(" ");
        List<String> list = new LinkedList<>();
        for (int i = 0; i < strings.length; i++) {
            if (i > 0) {
                String labelVar = strings[i].substring(0, 1).toUpperCase() + strings[i].substring(1);
                list.add(labelVar);
            } else {
                list.add(strings[i].toLowerCase());
            }
        }
        return String.join("", list);
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
