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
    //    public static final String ENV_PROPERTY_DEFAULT = "ci03";
    public static final String ENV_PROPERTY_DEFAULT = "localhost";
    public static final String RESOURCES_PATH = "src/test/resources/";

    private static InputData singleton = new InputData();

    public InputData() {
        try {
            String testEnvironment = System.getProperty(ENV_PROPERTY, ENV_PROPERTY_DEFAULT);
            logger.info("test.environment : " + testEnvironment);

            FileInputStream fileInputStream = new FileInputStream(RESOURCES_PATH + testEnvironment + ".properties");
            load(fileInputStream);
            logger.info("selenium.server  : " + getProperty("bigbird.url"));
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
    public static final String UPLOAD_DIRECTORY = new File(RESOURCES_PATH).getAbsolutePath();
    public static final String SERVER_URL = singleton.getProperty("server.url");
    public static final String BOOTSTRAP_URL = singleton.getProperty("bootstrap.url");
    public static final String FIREFOX_PROFILE_DIR = singleton.getProperty("firefox.profile.dir");
    public static final String CHROME_DRIVER_DIR = UPLOAD_DIRECTORY + singleton.getProperty("chrome.driver.dir");
    public static final String DB_SERVER = singleton.getProperty("db.server");
    public static final String DB_TABLE = singleton.getProperty("db.table");
    public static final String DB_USER = singleton.getProperty("db.user");
    public static final String DB_PASSWORD = singleton.getProperty("db.password");
    public static final String BROWSER = singleton.getProperty("browser");

}
