package com.sdl.selenium.utils.browsers;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChromeConfigReader extends AbstractBrowserConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChromeConfigReader.class);

    private static final String DEFAULT_CONFIG =
            "\n browser=chrome" +
                    "\n browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\chromedriver.exe" +
                    "\n browser.download.dir=src\\\\test\\\\resources\\\\download\\\\" +
                    "\n options.arguments=--disable-infobars --lang=en --allow-running-insecure-content --enable-logging --v=1 --test-type" +
                    "\n options.experimental.profile.default_content_setting_values.automatic_downloads=1" +
                    "\n options.experimental.profile.content_settings.pattern_pairs.*.multiple-automatic-downloads=1" +
                    "\n options.experimental.profile.default_content_settings.popups=0" +
                    "\n options.experimental.download.prompt_for_download=1" +
                    "\n options.experimental.credentials_enable_service=false" +
                    "\n options.experimental.profile.password_manager_enabled=false" +
                    "\n options.experimental.safebrowsing.enabled=true";


    public ChromeConfigReader() {
        this(null);
    }

    public ChromeConfigReader(String resourcePath) {
        super(DEFAULT_CONFIG, resourcePath);
    }

    /**
     * If you're using Selenium Grid, make sure the selenium server is in the same folder with the ChromeDriver
     * or include the path to the ChromeDriver in command line when registering the node:
     * -Dwebdriver.chrome.driver=%{path to chrome driver}
     *
     * @return ChromeOptions
     */
    private ChromeOptions getChromeOptions() {
        String driverPath = getProperty("browser.driver.path");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
        ChromeOptions options = new ChromeOptions();
        setProfilePreferences(options);
        return options;
    }

    @Override
    public WebDriver createDriver() throws IOException {
        return new ChromeDriver(getChromeOptions());
    }

    @Override
    public WebDriver createDriver(URL remoteUrl) throws IOException {
        ChromeOptions options = getChromeOptions();
        if (isRemoteDriver()) {
            RemoteWebDriver driver = new RemoteWebDriver(remoteUrl, options);
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        } else {
            return new ChromeDriver(options);
        }
    }

    @Override
    public boolean isSilentDownload() {
        return !"".equals(getProperty("browser.download.dir"));
    }

    private void setProfilePreferences(ChromeOptions options) {
        Map<String, Object> prefs = new HashMap<>();
        for (Map.Entry<Object, Object> entry : entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith("options.experimental.")) {
                String preferenceKey = key.substring(21);

                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    prefs.put(preferenceKey, Boolean.valueOf(value));
                } else {
                    try {
                        int intValue = Integer.parseInt(value);
                        prefs.put(preferenceKey, intValue);
                    } catch (NumberFormatException e) {
                        prefs.put(preferenceKey, value);
                    }
                }
            }
        }
        String property = getDownloadPath();
        File file = new File(property);
        String downloadDir = null;
        try {
            downloadDir = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (downloadDir != null && !"".equals(downloadDir)) {
            prefs.put("download.default_directory", downloadDir);
        }
        String arguments = getProperty("options.arguments");
        options.addArguments(arguments.split(" "));
        options.setExperimentalOption("prefs", prefs);
        LOGGER.debug("The properties was load with success: {}", toString());
    }
}
