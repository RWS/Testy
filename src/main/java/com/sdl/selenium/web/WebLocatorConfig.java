package com.sdl.selenium.web;

import com.sdl.selenium.web.utils.WebLocatorConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WebLocatorConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(WebLocatorConfig.class);

    private static final String CONFIG_FILE_NAME = "webLocator.properties";

    private static ClassLoader loader = WebLocatorConfig.class.getClassLoader();

    private static long defaultRenderMillis;
    private static boolean logUseClassName;
    private static boolean logXPathEnabled;
    private static boolean logContainers;
    private static boolean highlight;
    private static Set<SearchType> searchTextType = new HashSet<SearchType>() {{
        add(SearchType.CONTAINS);
    }};
    public static String defaultLabelPosition;
    public static int minCharsToType;

    private static WebLocatorConfigReader properties = null;

    static {
        URL resource = loader.getResource(CONFIG_FILE_NAME);
        String filePath = resource != null ? resource.getFile() : null;
        if (filePath == null) {
            LOGGER.info("override defaults by adding them in src/main/resources/{}", CONFIG_FILE_NAME);
        }
        properties = new WebLocatorConfigReader(filePath);
        LOGGER.info(properties.toString());
        init();
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Boolean getBoolean(String key) {
        Boolean v = null;
        String vString = getProperty(key);
        if (vString != null) {
            v = Boolean.valueOf(vString);
        } else {
            LOGGER.debug("key not found:" + key);
        }
        return v;
    }

    public static Integer getInt(String key) {
        Integer v = null;
        String vString = getProperty(key);
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
        String labelPosition = getProperty("weblocator.defaults.labelPosition");
        setDefaultLabelPosition(labelPosition);

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

        String searchTextType = getProperty("weblocator.defaults.searchType");
        if (searchTextType != null && !"".equals(searchTextType)) {
            searchTextType = searchTextType.toUpperCase();
            String[] searchTypes = searchTextType.split("\\s*,\\s*");
            Set<SearchType> list = new HashSet<SearchType>();
            for (String searchType : searchTypes) {
                try {
                    list.add(SearchType.valueOf(searchType));
                } catch (IllegalArgumentException e) {
                    LOGGER.error("SearchType not supported : {}. Supported SearchTypes: {}", searchType, Arrays.asList(SearchType.values()));
                }
            }
            setSearchTextType(list);
        }

        setMinCharsToType(getInt("weblocator.min.chars.toType"));
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
    public static Set<SearchType> getSearchTextType() {
        return (Set<SearchType>) ((HashSet) searchTextType).clone();
//        ArrayList<SearchType> copyOfArrayList = new ArrayList<SearchType>();
//        copyOfArrayList.addAll(searchTextType);
//        return copyOfArrayList;
    }

    public static void setSearchTextType(Set<SearchType> searchTextType) {
        WebLocatorConfig.searchTextType = searchTextType;
    }

    public static String getDefaultLabelPosition() {
        return defaultLabelPosition;
    }

    public static void setDefaultLabelPosition(String defaultLabelPosition) {
        WebLocatorConfig.defaultLabelPosition = defaultLabelPosition;
    }

    public static int getMinCharsToType() {
        return minCharsToType;
    }

    private static void setMinCharsToType(int minCharsToType) {
        WebLocatorConfig.minCharsToType = minCharsToType;
    }
}
