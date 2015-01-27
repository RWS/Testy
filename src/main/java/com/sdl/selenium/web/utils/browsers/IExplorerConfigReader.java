package com.sdl.selenium.web.utils.browsers;

import com.sdl.selenium.web.utils.PropertiesReader;

public class IExplorerConfigReader extends PropertiesReader {

    private static final String DEFAULT_CONFIG = ""+
            "\n browser=iexplore" +
            "\n browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\IEDriverServer.exe";

    public IExplorerConfigReader() {
        super(null, DEFAULT_CONFIG);
    }

    public IExplorerConfigReader(String resourcePath) {
        super(resourcePath, DEFAULT_CONFIG);
    }
}
