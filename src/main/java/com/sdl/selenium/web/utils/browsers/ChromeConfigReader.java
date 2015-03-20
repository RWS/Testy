package com.sdl.selenium.web.utils.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChromeConfigReader extends AbstractBrowserConfigReader {

    private static final String DEFAULT_CONFIG = "" +
            "\n browser=chrome" +
            "\n browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\chromedriver.exe" +
            "\n browser.download.dir=src\\\\test\\\\resources\\\\download\\\\";

    public ChromeConfigReader() {
        this(null);
    }

    public ChromeConfigReader(String resourcePath) {
        super(resourcePath, DEFAULT_CONFIG);
    }

    @Override
    public WebDriver createDriver() throws IOException {
        String driverPath = getProperty("browser.driver.path");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--start-maximized");
        options.addArguments("--lang=en");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--enable-logging --v=1");
        options.addArguments("--test-type");
        Map<String, Object> prefs = new HashMap<String, Object>();
        String property = getProperty("browser.download.dir");
        File file = new File(property);
        String downloadDir = file.getCanonicalPath();
        if (!"".equals(downloadDir)) {
            prefs.put("download.default_directory", downloadDir);
        }
        setProfilePreferences(prefs);
        options.setExperimentalOption("prefs", prefs);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return new ChromeDriver(capabilities);
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

    private void setProfilePreferences(Map<String, Object> prefs) {
        for (Map.Entry<Object, Object> entry : entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith("profile.preference.")) {
                String preferenceKey = key.substring(19);

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
    }
}
