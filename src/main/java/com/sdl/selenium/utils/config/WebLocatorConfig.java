package com.sdl.selenium.utils.config;

import com.google.common.base.Strings;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.utils.PropertiesReader;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WebLocatorConfig {

    private static final String CONFIG_FILE_NAME = "webLocator.properties";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WebLocatorConfig.class);

    private static long defaultRenderMillis;
    private static boolean logUseClassName;
    private static boolean logXPath;
    private static boolean logRetryException;
    private static boolean logSuggestions;
    private static boolean logContainers;
    private static boolean highlight;
    private static boolean generateCssSelector;
    private static ArrayList<SearchType> searchTextType = new ArrayList<>() {{
        add(SearchType.CONTAINS);
    }};
    private static String defaultLabelPosition;
    private static int minCharsToType;
    private static String uploadExePath;
    private static String extJsVersion;
    private static Set<String> logParamsExclude = new HashSet<>(Arrays.asList("a", "b")) {
    };

    private static WebLocatorConfigReader properties;

    static {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(CONFIG_FILE_NAME);
        String filePath = resource != null ? resource.getFile() : null;
        if (filePath == null) {
            log.info("override defaults by adding them in src/main/resources/{}", CONFIG_FILE_NAME);
        }
        properties = new WebLocatorConfigReader();
        if (resource != null) {
            try {
                //properties = new WebLocatorConfigReader(resource.openStream());
                properties.load(resource.openStream());
            } catch (IOException e) {
                log.error("IOException: {}", e.getMessage());
            }
        } //else {
        //properties = new WebLocatorConfigReader();
        //}

        init(properties);
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
            log.debug("key not found:" + key);
        }
        return v;
    }

    public static Integer getInt(String key) {
        Integer v = null;
        String vString = getProperty(key);
        if (vString != null) {
            v = Integer.valueOf(vString);
        } else {
            log.debug("key not found:" + key);
        }
        return v;
    }

    public static void init(WebLocatorConfigReader properties) {
        WebLocatorConfig.properties = properties;
        log.info(properties.toString());

        init();
    }

    private static void init() {
        Integer renderMillis = getInt("weblocator.defaults.renderMillis");
        if (renderMillis != null) {
            setDefaultRenderMillis(renderMillis);
        }
        setDefaultLabelPosition(getProperty("weblocator.defaults.labelPosition"));

        Boolean logUseClassName = getBoolean("weblocator.log.useClassName");
        if (logUseClassName != null) {
            setLogUseClassName(logUseClassName);
        }

        Boolean isLogXPathEnabled = getBoolean("weblocator.log.xPath");
        if (isLogXPathEnabled != null) {
            setLogXPath(isLogXPathEnabled);
        }

        Boolean retryException = getBoolean("weblocator.log.retryException");
        if (retryException != null) {
            setLogRetryException(retryException);
        }
        Boolean suggestionEnabled = getBoolean("weblocator.log.suggestions");
        if (suggestionEnabled != null) {
            setLogSuggestions(suggestionEnabled);
        }

        Boolean logContainers = getBoolean("weblocator.log.containers");
        if (logContainers != null) {
            setLogContainers(logContainers);
        }
        Boolean highlight = getBoolean("weblocator.highlight");
        if (highlight != null) {
            setHighlight(highlight);
        }

        Boolean generateCssSelector = getBoolean("weblocator.generateCssSelector");
        if (generateCssSelector != null) {
            setGenerateCssSelector(generateCssSelector);
        }

        convertAndSetSearchTextType(getProperty("weblocator.defaults.searchType"));

        setMinCharsToType(getInt("weblocator.min.chars.toType"));

        setExtJsVersion(getProperty("extjs.version"));
        setUploadExePath(getProperty("upload.exe.path"));

        setLogParamsExclude(getProperty("weblocator.log.params.exclude"));
    }

    public static void convertAndSetSearchTextType(String searchTextType) {
        if (Strings.isNullOrEmpty(searchTextType)) {
            searchTextType = searchTextType.toUpperCase();
            String[] searchTypes = searchTextType.split("\\s*,\\s*");
            ArrayList<SearchType> list = new ArrayList<>();
            for (String searchType : searchTypes) {
                try {
                    list.add(SearchType.valueOf(searchType));
                } catch (IllegalArgumentException e) {
                    log.error("SearchType not supported : {}. Supported SearchTypes: {}", searchType, Arrays.asList(SearchType.values()));
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

    public static boolean isLogXPath() {
        return logXPath;
    }

    public static void setLogXPath(boolean logXPath) {
        WebLocatorConfig.logXPath = logXPath;
    }

    public static boolean isLogRetryException() {
        return logRetryException;
    }

    public static void setLogRetryException(boolean logRetryException) {
        WebLocatorConfig.logRetryException = logRetryException;
    }

    public static boolean isLogSuggestions() {
        return logSuggestions;
    }

    public static void setLogSuggestions(boolean logSuggestions) {
        WebLocatorConfig.logSuggestions = logSuggestions;
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

    public static void setGenerateCssSelector(boolean generateCssSelector) {
        WebLocatorConfig.generateCssSelector = generateCssSelector;
    }

    public static boolean isGenerateCssSelector() {
        return generateCssSelector;
    }

    public static void setHighlight(boolean highlight) {
        WebLocatorConfig.highlight = highlight;
    }

    @SuppressWarnings("unchecked")
    public static List<SearchType> getSearchTextType() {
        return (List<SearchType>) searchTextType.clone();
    }

    public static void setSearchTextType(ArrayList<SearchType> searchTextType) {
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

    public static void setMinCharsToType(int minCharsToType) {
        WebLocatorConfig.minCharsToType = minCharsToType;
    }

    public static void setExtJsVersion(String extJsVersion) {
        WebLocatorConfig.extJsVersion = extJsVersion;
    }

    public static String getExtJsVersion() {
        return WebLocatorConfig.extJsVersion;
    }

    public static String getUploadExePath() {
        return uploadExePath;
    }

    public static void setUploadExePath(String uploadExePath) {
        WebLocatorConfig.uploadExePath = uploadExePath;
    }

    public static void setLogParamsExclude(String logParamsExclude) {
        Set<String> list = new HashSet<>();
        if (!logParamsExclude.isEmpty()) {
            logParamsExclude = logParamsExclude.toLowerCase();
            list.addAll(Arrays.asList(logParamsExclude.split(",")));
        }
        WebLocatorConfig.logParamsExclude.addAll(list);
    }

    public static Set<String> getLogParamsExclude() {
        return logParamsExclude;
    }

    public static void setBrowserProperties(PropertiesReader properties) {
        properties.keySet().retainAll(WebLocatorConfig.properties.keySet());
        if (!properties.isEmpty()) {
            WebLocatorConfig.properties.putAll(properties);
            log.info("The webLocator.properties were overwritten with value from browser properties: {}", properties.toString());
            init();
        }
    }
}
