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
            "\n options.arguments=--lang=en --allow-running-insecure-content --enable-logging --v=1 --test-type --disable-popup-blocking, --disable-extensions-file-access-check" +
//            "\n options.arguments=--lang=en --allow-running-insecure-content --enable-logging --v=1 --test-type --disable-popup-blocking, --disable-extensions-file-access-check --safebrowsing-disable-download-protection --disable-web-security" +
            "\n options.experimental.profile.default_content_settings.multiple-automatic-downloads=1" +
//            "\n options.experimental.profile.managed_default_content_settings.images=2" +
//            "\n options.experimental.safebrowsing.enabled=0" +
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

    @Override
    public String getDownloadPath() {
        File file = new File(getProperty("browser.download.dir"));
        return file.getAbsolutePath();
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
        String property = getProperty("browser.download.dir");
        File file = new File(property);
        String downloadDir = file.getCanonicalPath();
        if (!"".equals(downloadDir)) {
            prefs.put("download.default_directory", downloadDir);
        }
//        prefs.put("download.directory_upgrade", true);
//        prefs.put("download.extensions_to_open", "exe");
        prefs.put("download.prompt_for_download", false);
        prefs.put("safebrowsing.enabled", true);
        String arguments = getProperty("options.arguments");
        options.addArguments(arguments);
        options.setExperimentalOption("prefs", prefs);
//        options.push('--ignore-certificate-errors')
//        chromeSwitches.push('--disable-popup-blocking')
//        chromeSwitches.push('--disable-extensions-file-access-check')
//        chromeSwitches.push('--safebrowsing-disable-download-protection')
        options.addArguments("--disable-improved-download-protection");
        options.addArguments("--safebrowsing-disable-download-protection");
        options.addArguments("--disable-backing-store-limit");
        options.addArguments("--disable-prompt-on-repost");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions-file-access-check");
    }
}
