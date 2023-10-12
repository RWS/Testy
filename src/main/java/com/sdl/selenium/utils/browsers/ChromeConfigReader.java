package com.sdl.selenium.utils.browsers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ChromeConfigReader extends AbstractBrowserConfigReader {

    private static final String DEFAULT_CONFIG = String.join("\n", "##Chrome defaults \n",
            "browser=chrome",
//            "browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\chromedriver.exe",
            "browser.download.dir=src\\\\test\\\\resources\\\\download\\\\",
            "options.arguments=--remote-allow-origins=* --disable-infobars --lang=en --use-simple-cache-backend --allow-running-insecure-content --enable-logging --v=1 --test-type",
            "options.experimental.profile.default_content_setting_values.automatic_downloads=1",
            "options.experimental.profile.content_settings.pattern_pairs.*.multiple-automatic-downloads=1",
            "options.experimental.profile.default_content_settings.popups=0",
            "options.experimental.download.prompt_for_download=1",
            "options.experimental.credentials_enable_service=false",
            "options.experimental.profile.password_manager_enabled=false",
            "options.experimental.safebrowsing.enabled=true");
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ChromeConfigReader.class);


    private DriverService driverService;

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
//        String driverPath = getProperty("browser.driver.path");
//        if (!"".equals(driverPath)) {
//            System.setProperty("webdriver.chrome.driver", driverPath);
//        }
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        setProfilePreferences(options);
        return options;
    }

    @Override
    public WebDriver createDriver() {
        driverService = ChromeDriverService.createDefaultService();
        return new ChromeDriver((ChromeDriverService) driverService, getChromeOptions());
    }

    @Override
    public WebDriver createDriver(URL remoteUrl, DesiredCapabilities capabilities) {
        ChromeOptions options = getChromeOptions();
        options = options.merge(capabilities);
        options.setAcceptInsecureCerts(true);
        if (isRemoteDriver()) {
            ClientConfig config = ClientConfig.defaultConfig().readTimeout(Duration.ofMinutes(6));
            RemoteWebDriver driver = (RemoteWebDriver) RemoteWebDriver.builder().oneOf(options).address(remoteUrl).config(config).build();
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        } else {
            driverService = ChromeDriverService.createDefaultService();
            return new ChromeDriver((ChromeDriverService) getDriveService(), options);
        }
    }

    @Override
    public boolean isSilentDownload() {
        return "silent".equals(getProperty("browser.download.dir")) || !"".equals(getProperty("browser.download.dir"));
    }

    public DriverService getDriveService() {
        return driverService;
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
        if (downloadDir != null && !"".equals(downloadDir) && !"silent".equals(downloadDir)) {
            prefs.put("download.default_directory", downloadDir);
        }
        String arguments = getProperty("options.arguments");
        options.addArguments(arguments.split(" "));
        options.setExperimentalOption("prefs", prefs);
        log.debug("The properties was load with success: {}", toString());
    }
}
