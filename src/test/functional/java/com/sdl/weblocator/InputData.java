package com.sdl.weblocator;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class InputData extends Properties {
    private static final Logger logger = Logger.getLogger(InputData.class);

    public static final String ENV_PROPERTY = "env";
    public static final String ENV_PROPERTY_DEFAULT = "localhost";
    public static final String RESOURCES_PATH = "src/test/resources/";
    public static final String FUNCTIONAL_PATH = "src/test/functional/";

    private static InputData properties = new InputData();

    public InputData() {
        try {
            String testEnvironment = System.getProperty(ENV_PROPERTY, ENV_PROPERTY_DEFAULT);
            logger.info("test.environment : " + testEnvironment);

            FileInputStream fileInputStream = new FileInputStream(RESOURCES_PATH + testEnvironment + ".properties");
            load(fileInputStream);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public String getProperty(String key) {
        String property = System.getProperty(key, super.getProperty(key));
        logger.debug("getProperty: " + key + " = " + property);
        return property;
    }

    // ==============================
    public static final String RESOURCES_DIRECTORY_PATH = new File(RESOURCES_PATH).getAbsolutePath();
    public static final String DOWNLOAD_DIRECTORY = RESOURCES_DIRECTORY_PATH + "\\temp\\";
    public static final String FUNCTIONAL_PATH_ABSOLUTE = "file:///" + new File(FUNCTIONAL_PATH).getAbsolutePath();

    public static final String SERVER_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("server.url");
    public static final String BOOTSTRAP_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("bootstrap.url");
    public static final String WEB_LOCATOR_URL = FUNCTIONAL_PATH_ABSOLUTE + properties.getProperty("web.locator.url");

    public static final String BROWSER = properties.getProperty("browser");

    public static final String FIREFOX_PROFILE = properties.getProperty("firefox.profile");

    public static final String DRIVER_PATH_CHROME = RESOURCES_DIRECTORY_PATH + properties.getProperty("driver.path.chrome");
    public static final String DRIVER_PATH_IE = RESOURCES_DIRECTORY_PATH + properties.getProperty("driver.path.ie");
}
