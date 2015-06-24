package com.sdl.selenium.utils.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.File;
import java.io.IOException;

public class HtmlUnitConfigReader extends AbstractBrowserConfigReader {

    private static final String DEFAULT_CONFIG = ""+
            "\n browser=htmlunit";

    public HtmlUnitConfigReader() {
        this(null);
    }

    public HtmlUnitConfigReader(String resourcePath) {
        super(resourcePath, DEFAULT_CONFIG);
    }

    @Override
    public WebDriver createDriver() throws IOException {
        return new HtmlUnitDriver(true);
    }

    @Override
    public boolean isSilentDownload() {
        return !"".equals(getProperty("browser.download.dir"));
    }

    @Override
    public String getDownloadPath() {
        File file = new File(getProperty("browser.download.dir"));
        return file.getAbsolutePath();
    }
}
