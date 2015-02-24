package com.sdl.selenium.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * This class will load properties from System properties first, if not found then will load them from loaded file
 *
 * @author nmatei
 * @since 4/7/2014
 */
public class PropertiesReader extends OrderedProperties {
    public static String RESOURCES_PATH = "src/test/resources/";
    public static String RESOURCES_DIRECTORY_PATH = new File(RESOURCES_PATH).getAbsolutePath();

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

    public PropertiesReader() {
    }

    public PropertiesReader(String resourcePath) {
        if (resourcePath != null) {
            loadFile(resourcePath);
        }
    }

    public PropertiesReader(String resourcePath, String defaults) {
        if (defaults != null) {
            loadDefaults(defaults);
        }
        if (resourcePath != null) {
            loadFile(resourcePath);
        }
        LOGGER.info(this.toString());
    }

    protected void loadFile(String resourcePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(resourcePath);
            load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            LOGGER.error("IOException: {}", e);
        }
    }

    protected void loadDefaults(String defaults) {
        // load string as config if config file not found
        InputStream stream = new ByteArrayInputStream(defaults.getBytes(StandardCharsets.UTF_8));
        try {
            load(stream);
        } catch (IOException e) {
            LOGGER.error("IOException: {}", e);
        }
    }

    @Override
    public String getProperty(String key) {
        String property = System.getProperty(key, super.getProperty(key));
        return property;
    }

    public synchronized String toString() {
        int max = size() - 1;
        if (max == -1) {
            return "\n";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<Object, Object>> it = entrySet().iterator();

        sb.append("\n");
        for (int i = 0; ; i++) {
            Map.Entry<Object, Object> e = it.next();
            Object key = e.getKey();
            Object value = e.getValue();
            sb.append('\t');
            sb.append(key.toString());
            sb.append('=');
            sb.append(value.toString());

            sb.append("\n");
            if (i == max) {
                return sb.toString();
            }
        }
    }
}
