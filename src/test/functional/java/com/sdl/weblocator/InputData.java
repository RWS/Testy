package com.sdl.weblocator;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class InputData extends Properties {
    private static final Logger logger = Logger.getLogger(InputData.class);

    public static final String ENV_PROPERTY = "env";
    public static final String ENV_PROPERTY_DEFAULT = "localhost";
    public static final String RESOURCES_PATH = "src/test/resources/";
    public static final String FUNCTIONAL_PATH = "src/test/functional/";

    private static InputData singleton = new InputData();

    public InputData() {
        try {
            String testEnvironment = System.getProperty(ENV_PROPERTY, ENV_PROPERTY_DEFAULT);
            logger.info("test.environment : " + testEnvironment);

            FileInputStream fileInputStream = new FileInputStream(RESOURCES_PATH + testEnvironment + ".properties");
            load(fileInputStream);
        } catch (FileNotFoundException e) {
            logger.error(e);
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
    public static final String FUNCTIONAL_PATH_ABSOLUTE = "file:///" + new File(FUNCTIONAL_PATH).getAbsolutePath();

    public static final String SERVER_URL = FUNCTIONAL_PATH_ABSOLUTE + singleton.getProperty("server.url");
    public static final String BOOTSTRAP_URL = FUNCTIONAL_PATH_ABSOLUTE + singleton.getProperty("bootstrap.url");

    public static final String FIREFOX_PROFILE_DIR = singleton.getProperty("firefox.profile.dir");
    public static final String CHROME_DRIVER_DIR = RESOURCES_DIRECTORY_PATH + singleton.getProperty("chrome.driver.dir");
    public static final String BROWSER = singleton.getProperty("browser");

}
