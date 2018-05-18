package com.sdl.selenium.utils.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class IExplorerConfigReader extends AbstractBrowserConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(IExplorerConfigReader.class);

    private static final String DEFAULT_CONFIG =
            "\n browser=iexplore" +
            "\n browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\IEDriverServer.exe" +
            "\n desired.capabilities.ignoreProtectedModeSettings=true" +
            "\n desired.capabilities.javascriptEnabled=true";

    public IExplorerConfigReader() {
        this(null);
    }

    public IExplorerConfigReader(String resourcePath) {
        super(DEFAULT_CONFIG, resourcePath);
    }

    @Override
    public WebDriver createDriver() throws IOException {
        return new InternetExplorerDriver(getOptions());
    }

    /***
     * If you're using Selenium Grid, make sure the selenium server is in the same folder with the IEDriverServer
     * or include the path to the ChromeDriver in command line when registering the node:
     * -Dwebdriver.chrome.driver=%{path to chrome driver}
     * @return Internet Explorer capabilities
     */
    private InternetExplorerOptions getOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        setOptions(options);
        String driverPath = getProperty("browser.driver.path");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.ie.driver", driverPath);
        }
        return options;
    }

    @Override
    public WebDriver createDriver(URL remoteUrl) throws IOException {
        InternetExplorerOptions capabilities = getOptions();
        if (isRemoteDriver()) {
            RemoteWebDriver driver = new RemoteWebDriver(remoteUrl, capabilities);
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        } else {
            return new InternetExplorerDriver(capabilities);
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

    @Override
    public String getDownloadPath() {
        //http://stackoverflow.com/questions/18510965/how-to-set-internetexplorerdriver-download-directory
        return "";
    }

    private void setOptions(InternetExplorerOptions options) {
        for (Map.Entry<Object, Object> entry : entrySet()) {
            String key = (String) entry.getKey();
            if (key.startsWith("desired.capabilities.")) {
                String preferenceKey = key.substring(21);
                String value = (String) entry.getValue();

                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    boolean aBoolean = Boolean.valueOf(value);
                    options.setCapability(preferenceKey, aBoolean);
                } else {
                    try {
                        int intValue = Integer.parseInt(value);
                        options.setCapability(preferenceKey, intValue);
                    } catch (NumberFormatException e) {
                        options.setCapability(preferenceKey, value);
                    }
                }
            }
        }
        LOGGER.info("The properties was load with success: {}", toString());
    }
}
