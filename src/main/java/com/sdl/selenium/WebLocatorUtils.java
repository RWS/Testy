package com.sdl.selenium;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, WebLocator> webLocatorAsMap(WebLocator webLocator) {
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

    public static void main(String[] args) {
        WebLocator l = new WebLocator().setText("Matei");
        getXPathScript(l);
    }
}
