package com.sdl.selenium.web.utils.browsers;

import com.sdl.selenium.web.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public abstract class AbstractBrowserConfigReader extends PropertiesReader {

    public AbstractBrowserConfigReader(String resourcePath, String defaults) {
        super(resourcePath, defaults);
    }

    public abstract WebDriver createDriver() throws IOException;

    public abstract boolean isSilentDownload();

    public abstract String getDownloadPath();
}
