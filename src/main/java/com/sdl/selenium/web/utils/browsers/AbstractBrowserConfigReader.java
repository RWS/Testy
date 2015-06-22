package com.sdl.selenium.web.utils.browsers;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.sdl.selenium.web.utils.PropertiesReader;

public abstract class AbstractBrowserConfigReader extends PropertiesReader {

    public AbstractBrowserConfigReader(String defaults, String resourcePath) {
        super(defaults, resourcePath);
    }

    public abstract WebDriver createDriver() throws IOException;

    public abstract boolean isSilentDownload();

    public abstract String getDownloadPath();
}
