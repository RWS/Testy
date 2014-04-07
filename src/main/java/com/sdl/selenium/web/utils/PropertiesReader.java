package com.sdl.selenium.web.utils;

import org.apache.log4j.Logger;

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

    private static final Logger logger = Logger.getLogger(PropertiesReader.class);

    public PropertiesReader() {
    }

    public PropertiesReader(String resourcePath) {
        try {
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
