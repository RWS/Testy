package com.sdl.selenium.web;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;

public class WebLocatorConfig {

    private static Logger logger = Logger.getLogger(WebLocatorConfig.class);

    private static final String CONFIG_FILE_NAME = "webLocator.properties";
    private static ClassLoader loader = WebLocatorConfig.class.getClassLoader();

    private static long defaultRenderMillis = 3000;
    private static boolean logUseClassName = false;
    private static boolean logContainers = true;
    private static boolean highlight = false;

    private static Properties properties = null;

    static {
        properties = new Properties();
        try {
            String filePath = loader.getResource(CONFIG_FILE_NAME).getFile();
            logger.debug("reading: " + filePath);

            InputStream inputStream = loader.getSystemResourceAsStream(CONFIG_FILE_NAME);
            if (inputStream != null) {
                Reader reader = new InputStreamReader(inputStream, "UTF-8");
                properties.load(reader);
                inputStream.close();

                init();
            }
        } catch (Exception e) {
            logger.error("Error loading config file. " + CONFIG_FILE_NAME);
        }
    }

    public static String getString(String key) {
        String property = properties.getProperty(key);
        logger.debug("get key: " + key + " = " + property);
        return property;
    }

    public static Boolean getBoolean(String key) {
        Boolean v = null;
        String vString = getString(key);
        if (vString != null) {
            v = Boolean.valueOf(vString);
        } else {
            logger.debug("key not found:" + key);
        }
        return v;
    }

    public static Integer getInt(String key) {
        Integer v = null;
        String vString = getString(key);
        if (vString != null) {
            v = Integer.valueOf(vString);
        } else {
            logger.debug("key not found:" + key);
        }
        return v;
    }

    private static void init() {
        Integer renderMillis = getInt("weblocator.defaults.renderMillis");
        if (renderMillis != null) {
            setDefaultRenderMillis(renderMillis);
        }

        Boolean logUseClassName = getBoolean("weblocator.log.useClassName");
        if (logUseClassName != null) {
            setLogUseClassName(logUseClassName);
        }
        Boolean logContainers = getBoolean("weblocator.log.containers");
        if (logContainers != null) {
            setLogContainers(logContainers);
        }
        Boolean highlight = getBoolean("weblocator.highlight");
        if (highlight != null) {
            setHighlight(highlight);
        }
    }

    //

    public static long getDefaultRenderMillis() {
        return defaultRenderMillis;
    }

    public static void setDefaultRenderMillis(long defaultRenderMillis) {
        WebLocatorConfig.defaultRenderMillis = defaultRenderMillis;
    }

    public static boolean isLogUseClassName() {
        return logUseClassName;
    }

    public static void setLogUseClassName(boolean logUseClassName) {
        WebLocatorConfig.logUseClassName = logUseClassName;
    }

    public static boolean isLogContainers() {
        return logContainers;
    }

    public static void setLogContainers(boolean logContainers) {
        WebLocatorConfig.logContainers = logContainers;
    }

    public static boolean isHighlight() {
        return highlight;
    }

    public static void setHighlight(boolean highlight) {
        WebLocatorConfig.highlight = highlight;
    }

    public static void main(String args[]) {
        logger.info("start");

//        Window w = new Window("MyWin");
//        Panel p = new Panel(w, "MyPanel");
//        WebLocator el = new WebLocator("cls", p);
//        logger.info(el.getRenderMillis());
//        logger.info(el);
        logger.info("isHighlight(): " + isHighlight());

//        WebLocator el2 = new WebLocator(p, "//div/div[4]/div");
//        logger.debug(el2);
//
//
//        GridPanel grid = new GridPanel(p);
//        logger.debug(grid);
//
//        p.setElPath("//div/div/div");
//        p.setTitle(null);
//        GridPanel grid1 = new GridPanel(p);
//        logger.debug(grid1);

        URL baseUrl = WebLocatorConfig.class.getResource("webLocator.properties");
        logger.debug(baseUrl);
//
//        TextField s = new TextField("name", p);
//        logger.debug(s);


    }
}
