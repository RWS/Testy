package com.sdl.selenium.web.utils.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

public class IExplorerConfigReader extends AbstractBrowserConfigReader {

    private static final String DEFAULT_CONFIG = ""+
            "\n browser=iexplore" +
            "\n browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\IEDriverServer.exe";

    public IExplorerConfigReader() {
        this(null);
    }

    public IExplorerConfigReader(String resourcePath) {
        super(resourcePath, DEFAULT_CONFIG);
    }

    @Override
    public WebDriver createDriver() throws IOException {
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        String driverPath = getProperty("browser.driver.path");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.ie.driver", driverPath);
        }
        return new InternetExplorerDriver(ieCapabilities);
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
