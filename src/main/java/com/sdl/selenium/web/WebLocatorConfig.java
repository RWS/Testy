package com.sdl.selenium.web;

import com.sdl.selenium.web.utils.PropertiesReader;
import com.sdl.selenium.web.utils.WebLocatorConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;

public class WebLocatorConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(WebLocatorConfig.class);

    private static final String CONFIG_FILE_NAME = "webLocator.properties";

    private static ClassLoader loader = WebLocatorConfig.class.getClassLoader();

    private static long defaultRenderMillis = 3000;
    private static boolean logUseClassName = false;
    private static boolean logXPathEnabled = true;
    private static boolean logContainers = true;
    private static boolean highlight = false;
    private static List<SearchType> searchTextType = new ArrayList<SearchType>() {{
        add(SearchType.CONTAINS);
    }};

    private static WebLocatorConfigReader properties = null;

    static {
        URL resource = loader.getResource(CONFIG_FILE_NAME);
        String filePath = resource != null ? resource.getFile() : null;
        properties = new WebLocatorConfigReader(filePath);
        init();
    }

    public static Boolean getBoolean(String key) {
        Boolean v = null;
        String vString = properties.getProperty(key);
        if (vString != null) {
            v = Boolean.valueOf(vString);
        } else {
            LOGGER.debug("key not found:" + key);
        }
        return v;
    }

    public static Integer getInt(String key) {
        Integer v = null;
        String vString = properties.getProperty(key);
        if (vString != null) {
            v = Integer.valueOf(vString);
        } else {
            LOGGER.debug("key not found:" + key);
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

        Boolean isLogXPathEnabled = getBoolean("weblocator.log.logXPathEnabled");
        if (isLogXPathEnabled != null) {
            setLogXPathEnabled(isLogXPathEnabled);
        }

        Boolean logContainers = getBoolean("weblocator.log.containers");
        if (logContainers != null) {
            setLogContainers(logContainers);
        }
        Boolean highlight = getBoolean("weblocator.highlight");
        if (highlight != null) {
            setHighlight(highlight);
        }

        String searchTextType = properties.getProperty("weblocator.defaults.searchType");
        if (searchTextType != null && !"".equals(searchTextType)) {
            searchTextType = searchTextType.toUpperCase();
            String[] searchTypes = searchTextType.split("\\s*,\\s*");
            List<SearchType> list = new ArrayList<SearchType>();
            for (String searchType : searchTypes) {
                try {
                    list.add(SearchType.valueOf(searchType));
                } catch (IllegalArgumentException e) {
                    LOGGER.error("SearchType not supported : " + searchType + ". Supported SearchTypes: " + Arrays.asList(SearchType.values()));
                }
            }
            setSearchTextType(list);
        }
    }

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

    public static boolean isLogXPathEnabled() {
        return logXPathEnabled;
    }

    public static void setLogXPathEnabled(boolean logXPathEnabled) {
        WebLocatorConfig.logXPathEnabled = logXPathEnabled;
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

    @SuppressWarnings("unchecked")
    public static List<SearchType> getSearchTextType() {
        return (List<SearchType>) ((ArrayList) searchTextType).clone();
//        ArrayList<SearchType> copyOfArrayList = new ArrayList<SearchType>();
//        copyOfArrayList.addAll(searchTextType);
//        return copyOfArrayList;
    }

    public static void setSearchTextType(List<SearchType> searchTextType) {
        WebLocatorConfig.searchTextType = searchTextType;
    }
}
