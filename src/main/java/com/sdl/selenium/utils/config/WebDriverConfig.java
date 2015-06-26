package com.sdl.selenium.utils.config;

import com.opera.core.systems.OperaDesktopDriver;
import com.sdl.selenium.utils.browsers.*;
import com.sdl.selenium.web.Browser;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class WebDriverConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverConfig.class);

    private static WebDriver driver;

    private static boolean isIE;
    private static boolean isOpera;
    private static boolean isSafari;
    private static boolean isChrome;
    private static boolean isFireFox;
    private static boolean isSilentDownload;
    private static String downloadPath;

    /**
     * @return last created driver (current one)
     */
    public static WebDriver getDriver() {
        return driver;
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
            LOGGER.info("===============================================================");
            LOGGER.info("|          Open Selenium Web Driver ");
            LOGGER.info("===============================================================\n");
            WebDriverConfig.driver = driver;
            WebLocator.setDriverExecutor(driver);
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

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(WebLocatorConfig.getInt("driver.implicitlyWait"), TimeUnit.MILLISECONDS);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    if (WebLocatorConfig.getBoolean("driver.autoClose")) {
                        initSeleniumEnd();
                    }
                }
            });
        }
    }

    private static void initSeleniumEnd() {
        LOGGER.info("===============================================================");
        LOGGER.info("|          Stopping driver (closing browser)                   |");
        LOGGER.info("===============================================================");
        driver.quit();
        LOGGER.debug("===============================================================");
        LOGGER.debug("|         Driver stopped (browser closed)                     |");
        LOGGER.debug("===============================================================\n");
    }

    public static boolean isSilentDownload() {
        return isSilentDownload;
    }

    private static void setSilentDownload(boolean isSalientDownload) {
        WebDriverConfig.isSilentDownload = isSalientDownload;
    }

    public static String getDownloadPath() {
        return downloadPath;
    }

    public static void setDownloadPath(String downloadPath) {
        WebDriverConfig.downloadPath = downloadPath;
    }

    /**
     * Create and return new WebDriver
     *
     * @param browserProperties path to browser.properties
     * @return WebDriver
     * @throws IOException exception
     */
    public static WebDriver getWebDriver(String browserProperties) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(browserProperties);

        LOGGER.debug("File: {} " + (resource != null ? "exists" : "does not exist"), browserProperties);

        InputStream inputStream = resource.openStream();


        Browser browser = findBrowser(inputStream);
        return getDriver(browser, inputStream);
    }

    /**
     * Create and return new WebDriver
     *
     * @param browser see details {@link com.sdl.selenium.web.Browser}
     * @return WebDriver
     * @throws IOException exception
     */
    public static WebDriver getWebDriver(Browser browser) throws IOException {
        return getDriver(browser, null);
    }

    private static WebDriver getDriver(Browser browser, InputStream inputStream) throws IOException {
        AbstractBrowserConfigReader properties = null;
        if (browser == Browser.FIREFOX) {
            properties = new FirefoxConfigReader();
        } else if (browser == Browser.IEXPLORE) {
            properties = new IExplorerConfigReader();
        } else if (browser == Browser.CHROME) {
            properties = new ChromeConfigReader();
        } else if (browser == Browser.HTMLUNIT) {
            properties = new HtmlUnitConfigReader();
        } else {
            LOGGER.error("Browser not supported {}", browser);
            driver = null;
        }
        if (properties != null) {
            properties.load(inputStream);

            LOGGER.info(properties.toString());

            driver = properties.createDriver();
            WebDriverConfig.setDownloadPath(properties.getDownloadPath());
            WebDriverConfig.setSilentDownload(properties.isSilentDownload());
        }
        init(driver);
        return driver;
    }

    public static Browser getBrowser(String browserKey) {
        browserKey = browserKey.toUpperCase();
        Browser browser = null;
        try {
            browser = Browser.valueOf(browserKey);
        } catch (IllegalArgumentException e) {
            LOGGER.error("BROWSER not supported : {}. Supported browsers: {}", browserKey, Arrays.asList(Browser.values()));
        }
        return browser;
    }

    private static Browser findBrowser(InputStream inputStream) {
        PropertiesReader properties = new PropertiesReader(null, inputStream);
        String browserKey = properties.getProperty("browser");
        LOGGER.info("Browser is: {}", browserKey);
        return getBrowser(browserKey);
    }
}
