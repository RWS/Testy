package com.sdl.weblocator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdl.selenium.web.utils.PropertiesReader;

public class InputData extends PropertiesReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputData.class);

    public static final String ENV_PROPERTY = "env";
    public static final String ENV_PROPERTY_DEFAULT = "localhost";
    public static final String FUNCTIONAL_PATH = "src/test/functional/";

    private static InputData properties = new InputData();

    public InputData() {
        String testEnvironment = System.getProperty(ENV_PROPERTY, ENV_PROPERTY_DEFAULT);
        LOGGER.info("test.environment : " + testEnvironment);

//        FileInputStream inputStream = getFileAsStream(RESOURCES_PATH + testEnvironment + ".properties");
        URL resource = Thread.currentThread().getContextClassLoader().getResource(testEnvironment + ".properties");
        //URL resource = loader.getResource(testEnvironment + ".properties");

        InputStream inputStream = null;
        try {
            inputStream = resource != null ? resource.openStream() : null;
        } catch (IOException e) {
            LOGGER.error("IOException: {}", e);
        }

        init(null, inputStream);

        LOGGER.info(toString());
    }

    // ==============================
    public static final String DOWNLOAD_DIRECTORY = RESOURCES_DIRECTORY_PATH + "\\download\\";
    public static final String FUNCTIONAL_PATH_ABSOLUTE = "file:///" + new File(FUNCTIONAL_PATH).getAbsolutePath();

    public static final String SERVER_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("server.url");
    public static final String BOOTSTRAP_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("bootstrap.url");
    public static final String WEB_LOCATOR_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("web.locator.url");
    public static final String SUGGESTIONS_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("suggestions.url");

    public static final String BROWSER_CONFIG = properties.getProperty("browser.config");
}
