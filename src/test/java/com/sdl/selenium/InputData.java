package com.sdl.selenium;

import com.sdl.selenium.web.utils.PropertiesReader;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class InputData extends PropertiesReader {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(InputData.class);

    public static final String ENV_PROPERTY = "env";
    public static final String ENV_PROPERTY_DEFAULT = "localhost";
    public static final String FUNCTIONAL_PATH = "src/test/functional/";

    private static final InputData properties = new InputData();

    public InputData() {
        String testEnvironment = System.getProperty(ENV_PROPERTY, ENV_PROPERTY_DEFAULT);
        log.info("test.environment: " + testEnvironment);

//        FileInputStream inputStream = getFileAsStream(RESOURCES_PATH + testEnvironment + ".properties");
        URL resource = Thread.currentThread().getContextClassLoader().getResource(testEnvironment + ".properties");
        //URL resource = loader.getResource(testEnvironment + ".properties");

        InputStream inputStream = null;
        try {
            inputStream = resource != null ? resource.openStream() : null;
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }

        init(null, inputStream);
        log.info(toString());
    }

    // ==============================
    public static final String DOWNLOAD_DIRECTORY = RESOURCES_DIRECTORY_PATH + "\\download\\";
    public static final String UPLOAD_EXE_PATH = RESOURCES_DIRECTORY_PATH + "\\upload\\upload.exe";
    public static final String FUNCTIONAL_PATH_ABSOLUTE = "file:///" + new File(FUNCTIONAL_PATH).getAbsolutePath();

    public static final String BOOTSTRAP_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("bootstrap.url");
    public static final String EXTJS_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("extjs.url");
    public static final String EXTJS_EXAMPLE_URL = properties.getProperty("extjs.example.url");
    public static final String EXTREACT_EXAMPLE_URL = properties.getProperty("extreact.example.url");
    public static final String WEB_LOCATOR_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("web.locator.url");
    public static final String SUGGESTIONS_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("suggestions.url");
    public static final String LOGIN_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("login.url");

    public static final String BROWSER_CONFIG = properties.getProperty("browser.config");
}
