package com.sdl.selenium.web.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class will load properties from System properties first, if not found then will load them from loaded file
 *
 * @author nmatei
 * @since 4/7/2014
 */
public class PropertiesReader extends Properties {
    public static String RESOURCES_PATH = "src/test/resources/";
    public static String RESOURCES_DIRECTORY_PATH = new File(RESOURCES_PATH).getAbsolutePath();

    private static final Logger logger = Logger.getLogger(PropertiesReader.class);

    public PropertiesReader() {
    }

    public PropertiesReader(String resourcePath) {
        try {
            RESOURCES_PATH = new File(resourcePath).getParent() + File.separator;
            RESOURCES_DIRECTORY_PATH = new File(RESOURCES_PATH).getAbsolutePath();
            FileInputStream fileInputStream = new FileInputStream(resourcePath);
            load(fileInputStream);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public String getProperty(String key) {
        String property = System.getProperty(key, super.getProperty(key));
        logger.debug("getProperty: " + key + " = " + property);
        return property;
    }

}
