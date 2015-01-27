package com.sdl.selenium.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

    public PropertiesReader() {
    }

    public PropertiesReader(String resourcePath) {
        loadFile(resourcePath);
    }
    
    public PropertiesReader(String resourcePath, String defaults) {
        if(defaults != null){
            loadDefaults(defaults);
        }
        if(resourcePath != null){
            loadFile(resourcePath);
        }
    }

    protected void loadFile(String resourcePath) {
        try {
            LOGGER.info("load properties file: {}", resourcePath);
            FileInputStream fileInputStream = new FileInputStream(resourcePath);
            load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("IOException: {}", e);
        }
    }
    
    protected void loadDefaults(String defaults) {
        // load string as config if config file not found
        InputStream stream = new ByteArrayInputStream(defaults.getBytes(StandardCharsets.UTF_8));
        try {
            LOGGER.info("load properties defaults: {}", defaults);
            load(stream);
        } catch (IOException e) {
            LOGGER.error("IOException: {}", e);
        }
    }

    @Override
    public String getProperty(String key) {
        String property = System.getProperty(key, super.getProperty(key));
        LOGGER.debug("getProperty: " + key + " = " + property);
        return property;
    }

}
