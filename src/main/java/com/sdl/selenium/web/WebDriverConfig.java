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

public class WebDriverConfig {
    private static final Logger logger = Logger.getLogger(WebDriverConfig.class);

    private static WebDriver driver;
    private static Selenium selenium;

    private static boolean isIE;
    private static boolean isOpera;
    private static boolean isSafari;
    private static boolean isChrome;
    private static boolean isFireFox;

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

    public static WebDriver getWebDriver(String browserProperties) {
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
            options.addArguments("--lang=en");
            options.addArguments("--allow-running-insecure-content");
            options.addArguments("--enable-logging --v=1");
            driver = new ChromeDriver(options);
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
        DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
        String version = properties.getProperty("browser.version");
        if (version != null) {
            firefoxCapabilities.setCapability("version", version);
        }
        String homeDir = properties.getProperty("browser.binary.path");
        if (homeDir != null) {
            firefoxCapabilities.setCapability("firefox_binary", homeDir + "firefox.exe");
        }
        String profileName = properties.getProperty("browser.profile.name");
        FirefoxProfile myProfile;
        if (!"".equals(profileName) && profileName != null) {
            ProfilesIni allProfiles = new ProfilesIni();
            myProfile = allProfiles.getProfile(profileName);
        } else {
            myProfile = new FirefoxProfile();
        }
        if (myProfile != null) {
            String maxRunTimeScript = properties.getProperty("dom.max_script_run_time");
            if (maxRunTimeScript != null && !"".equals(maxRunTimeScript)) {
                myProfile.setPreference("dom.max_script_run_time", Integer.valueOf(maxRunTimeScript));
            }
            String folderList = properties.getProperty("browser.download.folderList");
            if (folderList != null && !"".equals(folderList)) {
                myProfile.setPreference("browser.download.folderList", Integer.valueOf(folderList));
            }
            setBooleanProperties(properties, myProfile,
                    "browser.download.manager.showWhenStarting",
                    "browser.download.manager.closeWhenDone",
                    "browser.download.manager.showAlertOnComplete",
                    "browser.download.manager.showWhenStartinge",
                    "browser.download.panel.shown"
            );

            String downloadDir = new File(PropertiesReader.RESOURCES_PATH + properties.getProperty("browser.download.dir")).getAbsolutePath();
            if (downloadDir != null && !"".equals(downloadDir)) {
                myProfile.setPreference("browser.download.dir", downloadDir);
            }
            String typeFile = properties.getProperty("browser.helperApps.neverAsk.saveToDisk");
            if (typeFile != null && !"".equals(typeFile)) {
                myProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", typeFile);
            }


            driver = new FirefoxDriver(myProfile);
        } else {
            String profilePath = properties.getProperty("browser.profile.path");
            if (profilePath != null && !profilePath.equals("")) {
                FirefoxProfile firefoxProfile = new FirefoxProfile(new File(profilePath));
                driver = new FirefoxDriver(firefoxProfile);
            } else {
                driver = new FirefoxDriver(firefoxCapabilities);
            }
        }
    }

    private static void setBooleanProperties(PropertiesReader properties, FirefoxProfile myProfile, String... keys) {
        for (String key : keys) {
            String property = properties.getProperty(key);
            if (property != null && !"".equals(property)) {
                myProfile.setPreference(key, Boolean.valueOf(property));
            }
        }
    }
}
