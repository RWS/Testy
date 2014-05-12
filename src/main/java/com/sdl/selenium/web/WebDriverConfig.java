package com.sdl.selenium.web;

import com.opera.core.systems.OperaDesktopDriver;
import com.sdl.selenium.web.utils.PropertiesReader;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebDriverConfig {
    private static final Logger logger = Logger.getLogger(WebDriverConfig.class);

    private static WebDriver driver;
    private static Selenium selenium;

    private static boolean isIE;
    private static boolean isOpera;
    private static boolean isSafari;
    private static boolean isChrome;
    private static boolean isFireFox;
    private static boolean isSilentDownload;

    /**
     * 
     * @return last created driver (current one)
     */
    public static WebDriver getDriver() {
        return driver;
    }

    public static Selenium getSelenium() {
        return selenium;
    }

    public static boolean isIE() {
        return isIE;
    }

    public static boolean isOpera() {
        return isOpera;
    }

    public static boolean isSafari() {
        return isSafari;
    }

    public static boolean isChrome() {
        return isChrome;
    }

    public static boolean isFireFox() {
        return isFireFox;
    }

    public static void init(WebDriver driver) {
        if (driver != null) {
            logger.info("========= init WebDriver =========");
            WebDriverConfig.driver = driver;
            WebLocator.setDriverExecutor(getDriver());
            if (driver instanceof InternetExplorerDriver) {
                isIE = true;
            } else if (driver instanceof ChromeDriver) {
                isChrome = true;
            } else if (driver instanceof FirefoxDriver) {
                isFireFox = true;
            } else if (driver instanceof SafariDriver) {
                isSafari = true;
            } else if (driver instanceof OperaDesktopDriver) {
                isOpera = true;
            }
        }
    }

    public static boolean hasWebDriver() {
        return driver != null;
    }

    public static boolean isSilentDownload() {
        return isSilentDownload;
    }

    private static void setSilentDownload(boolean isSalientDownload) {
        WebDriverConfig.isSilentDownload = isSalientDownload;
    }

    /**
     * Create and return new WebDriver
     * @param browserProperties
     * @return
     */
    public static WebDriver getWebDriver(String browserProperties) throws IOException {
        //logger.debug("PropertiesReader.RESOURCES_PATH(1.7.2-SNAPSHOT)=" + PropertiesReader.RESOURCES_PATH);
        PropertiesReader properties = new PropertiesReader(browserProperties);
        String browserKey = properties.getProperty("browser");
        browserKey = browserKey.toUpperCase();
        Browser browser = Browser.valueOf(browserKey);
        String driverPath = PropertiesReader.RESOURCES_PATH + properties.getProperty("browser.driver.path");
        if (browser == Browser.FIREFOX) {
            createFirefoxDriver(properties);
        } else if (browser == Browser.IEXPLORE) {
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            if (!"".equals(driverPath)) {
                System.setProperty("webdriver.ie.driver", driverPath);
            }
            driver = new InternetExplorerDriver(ieCapabilities);
        } else if (browser == Browser.CHROME) {
            if (!"".equals(driverPath)) {
                System.setProperty("webdriver.chrome.driver", driverPath);
            }
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--start-maximized");
            options.addArguments("--lang=en");
            options.addArguments("--allow-running-insecure-content");
            options.addArguments("--enable-logging --v=1");
            Map<String, Object> prefs = new HashMap<String, Object>();
            String property = properties.getProperty("browser.download.dir");
            String downloadDir = new File(PropertiesReader.RESOURCES_PATH + property).getCanonicalPath();
            if (downloadDir != null && !"".equals(downloadDir)) {
                prefs.put("download.default_directory", downloadDir);
            }
            options.setExperimentalOption("prefs", prefs);
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(capabilities);
            WebDriverConfig.setSilentDownload(!"".equals(property));
        } else if (browser == Browser.HTMLUNIT) {
            driver = new HtmlUnitDriver(true);
        } else {
            logger.error("Browser not supported" + browser);
            driver = null;
        }
        init(driver);
        return driver;
    }

    private static void createFirefoxDriver(PropertiesReader properties) {
        String profileName = properties.getProperty("browser.profile.name");
        FirefoxProfile myProfile;
        if (!"".equals(profileName) && profileName != null) {
            ProfilesIni allProfiles = new ProfilesIni();
            myProfile = allProfiles.getProfile(profileName);
        } else {
            myProfile = new FirefoxProfile();
        }
        if (myProfile != null) {
            logger.info("profile not null");
            setProperties(properties, myProfile, Integer.class,
                    "dom.max_script_run_time",
                    "browser.download.folderList"
            );
            setProperties(properties, myProfile, Boolean.class,
                    "browser.download.manager.showWhenStarting",
                    "browser.download.manager.closeWhenDone",
                    "browser.download.manager.showAlertOnComplete",
                    "browser.download.panel.shown",
                    "browser.helperApps.alwaysAsk.force"
            );
            setProperties(properties, myProfile, String.class,
                    "browser.helperApps.neverAsk.saveToDisk",
                    "browser.helperApps.neverAsk.openFile"
            );

            String downloadDir = new File(PropertiesReader.RESOURCES_PATH + properties.getProperty("browser.download.dir")).getAbsolutePath();
            if (downloadDir != null && !"".equals(downloadDir)) {
                myProfile.setPreference("browser.download.dir", downloadDir);
            }

            driver = new FirefoxDriver(myProfile);
            WebDriverConfig.setSilentDownload(
                    !"".equals(properties.getProperty("browser.download.dir")) &&
                            !"".equals(properties.getProperty("browser.helperApps.neverAsk.openFile")) &&
                            !"".equals(properties.getProperty("browser.helperApps.neverAsk.saveToDisk")) &&
                            !(Boolean.valueOf(properties.getProperty("browser.helperApps.alwaysAsk.force"))) &&
                            !(Boolean.valueOf(properties.getProperty("browser.download.panel.shown"))) &&
                            !(Boolean.valueOf(properties.getProperty("browser.download.manager.showAlertOnComplete"))) &&
                            (Boolean.valueOf(properties.getProperty("browser.download.manager.closeWhenDone"))) &&
                            !(Boolean.valueOf(properties.getProperty("browser.download.manager.showWhenStarting"))) &&
                            (Integer.valueOf(properties.getProperty("browser.download.folderList")) == 2)
            );
        } else {
            String profilePath = properties.getProperty("browser.profile.path");
            if (profilePath != null && !profilePath.equals("")) {
                FirefoxProfile firefoxProfile = new FirefoxProfile(new File(profilePath));
                driver = new FirefoxDriver(firefoxProfile);
            } else {
                DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
                String version = properties.getProperty("browser.version");
                if (version != null) {
                    firefoxCapabilities.setCapability("version", version);
                }
                String homeDir = properties.getProperty("browser.binary.path");
                if (homeDir != null) {
                    firefoxCapabilities.setCapability("firefox_binary", homeDir + "firefox.exe");
                }
                driver = new FirefoxDriver(firefoxCapabilities);
            }
        }
    }

    private static <T> void setProperties(PropertiesReader properties, FirefoxProfile myProfile, java.lang.Class<T> objectType, String... keys) {
        for (String key : keys) {
            String property = properties.getProperty(key);
            if (property != null && !"".equals(property)) {
                if (objectType == Boolean.class) {
                    myProfile.setPreference(key, Boolean.valueOf(property));
                } else if (objectType == Integer.class) {
                    myProfile.setPreference(key, Integer.valueOf(property));
                } else {
                    myProfile.setPreference(key, property);
                }
            }
        }
    }
}
