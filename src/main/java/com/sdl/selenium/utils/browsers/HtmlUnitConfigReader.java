package com.sdl.selenium.utils.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;
import java.net.URL;

public class HtmlUnitConfigReader extends AbstractBrowserConfigReader {

    private static final String DEFAULT_CONFIG =
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
    public WebDriver createDriver(URL remoteUrl) throws IOException {
        if (isRemoteDriver()) {
            RemoteWebDriver driver = new RemoteWebDriver(remoteUrl, DesiredCapabilities.htmlUnit());
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        } else {
            return createDriver();
        }
    }

    @Override
    public boolean isSilentDownload() {
        return "silent".equals(getProperty("browser.download.dir")) || !"".equals(getProperty("browser.download.dir"));
    }

    @Override
    public DriverService getDriveService() {
        return null;
    }
}
