package com.sdl.weblocator;

import com.sdl.selenium.web.utils.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InputData extends PropertiesReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputData.class);

    public static final String ENV_PROPERTY = "env";
    public static final String ENV_PROPERTY_DEFAULT = "localhost";
    public static final String FUNCTIONAL_PATH = "src/test/functional/";

    private static InputData properties = new InputData();

    public InputData() {
        try {
            String testEnvironment = System.getProperty(ENV_PROPERTY, ENV_PROPERTY_DEFAULT);
            LOGGER.info("test.environment : " + testEnvironment);

            FileInputStream fileInputStream = new FileInputStream(RESOURCES_PATH + testEnvironment + ".properties");
            load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("IOException: {}", e);
        }
    }

    // ==============================
    public static final String DOWNLOAD_DIRECTORY = RESOURCES_DIRECTORY_PATH + "\\temp\\";
    public static final String FUNCTIONAL_PATH_ABSOLUTE = "file:///" + new File(FUNCTIONAL_PATH).getAbsolutePath();

    public static final String SERVER_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("server.url");
    public static final String BOOTSTRAP_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("bootstrap.url");
    public static final String WEB_LOCATOR_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("web.locator.url");

    public static final String BROWSER_CONFIG = RESOURCES_PATH + properties.getProperty("browser.config");
}
