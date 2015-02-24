package com.sdl.selenium.web.utils.browsers;

import com.sdl.selenium.web.utils.PropertiesReader;

public class ChromeConfigReader extends PropertiesReader {

    private static final String DEFAULT_CONFIG = "" +
            "\n browser=chrome" +
            "\n browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\chromedriver.exe" +
            "\n browser.download.dir=src\\\\test\\\\resources\\\\download\\\\";

    public ChromeConfigReader() {
        this(null);
    }

    public ChromeConfigReader(String resourcePath) {
        super(resourcePath, DEFAULT_CONFIG);
    }
}
