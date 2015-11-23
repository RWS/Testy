package com.sdl.selenium.utils.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChromeConfigReader extends AbstractBrowserConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChromeConfigReader.class);

    private static final String DEFAULT_CONFIG = "" +
            "\n browser=chrome" +
            "\n browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\chromedriver.exe" +
            "\n browser.download.dir=src\\\\test\\\\resources\\\\download\\\\" +
            "\n options.arguments=--lang=en --allow-running-insecure-content --enable-logging --v=1 --test-type" +
            "\n options.experimental.profile.default_content_setting_values.automatic_downloads=1" +
            "\n options.experimental.download.prompt_for_download=1";

    public ChromeConfigReader() {
        this(null);
    }

    public ChromeConfigReader(String resourcePath) {
        super(DEFAULT_CONFIG, resourcePath);
    }

    @Override
    public WebDriver createDriver() throws IOException {
        String driverPath = getProperty("browser.driver.path");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
        ChromeOptions options = new ChromeOptions();
        setProfilePreferences(options);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return new ChromeDriver(capabilities);
    }

    @Override
    public boolean isSilentDownload() {
        return !"".equals(getProperty("browser.download.dir"));
    }

    private void setProfilePreferences(ChromeOptions options) throws IOException {
        Map<String, Object> prefs = new HashMap<String, Object>();

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
        String downloadDir = file.getCanonicalPath();
        if (!"".equals(downloadDir)) {
            prefs.put("download.default_directory", downloadDir);
        }
        String arguments = getProperty("options.arguments");
        options.addArguments(arguments);
        options.setExperimentalOption("prefs", prefs);
        LOGGER.info("The properties was load with success: {}", toString());
    }
}
