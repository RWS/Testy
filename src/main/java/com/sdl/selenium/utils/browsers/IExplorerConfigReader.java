package com.sdl.selenium.utils.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class IExplorerConfigReader extends AbstractBrowserConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(IExplorerConfigReader.class);

    private static final String DEFAULT_CONFIG = "" +
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
        DesiredCapabilities capabilities = getDesiredCapabilities();
        return new InternetExplorerDriver(capabilities);
    }

    /***
     * If you're using Selenium Grid, make sure the selenium server is in the same folder with the IEDriverServer
     * or include the path to the ChromeDriver in command line when registering the node:
     * -Dwebdriver.chrome.driver=%{path to chrome driver}
     * @return Internet Explorer capabilities
     */
    private DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        setCapabilities(capabilities);
        String driverPath = getProperty("browser.driver.path");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.ie.driver", driverPath);
        }
        return capabilities;
    }

    @Override
    public WebDriver createDriver(URL remoteUrl) throws IOException {
        DesiredCapabilities capabilities = getDesiredCapabilities();
        if (isRemoteDriver()) {
            return new RemoteWebDriver(remoteUrl, capabilities);
        } else {
            return new InternetExplorerDriver(capabilities);
        }
    }

    @Override
    public boolean isSilentDownload() {
        return !"".equals(getProperty("browser.download.dir"));
    }

    @Override
    public String getDownloadPath() {
        //http://stackoverflow.com/questions/18510965/how-to-set-internetexplorerdriver-download-directory
        return "";
    }

    private void setCapabilities(DesiredCapabilities capabilities) {
        for (Map.Entry<Object, Object> entry : entrySet()) {
            String key = (String) entry.getKey();
            if (key.startsWith("desired.capabilities.")) {
                String preferenceKey = key.substring(21);
                String value = (String) entry.getValue();

                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    boolean aBoolean = Boolean.valueOf(value);
                    capabilities.setCapability(preferenceKey, aBoolean);
                } else {
                    try {
                        int intValue = Integer.parseInt(value);
                        capabilities.setCapability(preferenceKey, intValue);
                    } catch (NumberFormatException e) {
                        capabilities.setCapability(preferenceKey, value);
                    }
                }
            }
        }
        LOGGER.info("The properties was load with success: {}", toString());
    }
}
