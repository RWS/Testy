package com.sdl.selenium.web;

import com.opera.core.systems.OperaDesktopDriver;
import com.sdl.selenium.web.utils.PropertiesReader;
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

    private static boolean isIE;
    private static boolean isOpera;
    private static boolean isSafari;
    private static boolean isChrome;
    private static boolean isFireFox;

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

    public static void init(WebDriver driver){
        if (driver != null) {
            WebDriverConfig.driver = driver;
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

    public static WebDriver getWebDriver(Browser browser, String propertiesName) {
        PropertiesReader properties = new PropertiesReader(propertiesName);
        String driverPath = properties.getProperty("browser.driver.path");
        if (browser == Browser.FIREFOX) {
            createFirefoxDriver(properties);
        } else if (browser == Browser.IEXPLORE) {
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            if (driverPath != null && !driverPath.equals("")) {
                System.setProperty("webdriver.ie.driver", driverPath);
            }
            driver = new InternetExplorerDriver(ieCapabilities);
        } else if (browser == Browser.CHROME) {
            if (driverPath != null && !driverPath.equals("")) {
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
        if(version != null){
            firefoxCapabilities.setCapability("version", version);
        }
        String homeDir = properties.getProperty("browser.binary.path");
        if(homeDir != null){
            firefoxCapabilities.setCapability("firefox_binary", homeDir + "firefox.exe");
        }

        ProfilesIni allProfiles = new ProfilesIni();
        String profileName = properties.getProperty("browser.profile.name");
        FirefoxProfile myProfile = allProfiles.getProfile(profileName);
        if (myProfile != null) {
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
}
