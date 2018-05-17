package com.sdl.selenium.utils.config;

import com.opera.core.systems.OperaDesktopDriver;
import com.sdl.selenium.utils.browsers.*;
import com.sdl.selenium.web.Browser;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.PropertiesReader;
import com.sdl.selenium.web.utils.Utils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

            if(!SystemUtils.IS_OS_LINUX){
                driver.manage().window().maximize();
            }
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
        String user = System.getProperty("user.home");
        try {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(user + "\\AppData\\Local\\Temp"));
        } catch (IOException e) {
            LOGGER.debug("{}", e.getMessage());
        }
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
        return getWebDriver(browserProperties, null);
    }

    /**
     * Create and return new WebDriver or RemoteWebDriver based on properties file
     *
     * @param browserProperties path to browser.properties
     * @param remoteUrl         url
     * @return WebDriver
     * @throws IOException exception
     */
    public static WebDriver getWebDriver(String browserProperties, URL remoteUrl) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(browserProperties);

        LOGGER.debug("File: {} " + (resource != null ? "exists" : "does not exist"), browserProperties);

        if (resource != null) {
            Browser browser = findBrowser(resource.openStream());
            return getDriver(browser, resource.openStream(), remoteUrl);
        }
        return null;
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

    public static WebDriver getWebDriver(URL remoteUrl, DesiredCapabilities capabilities) throws IOException {
        driver = new RemoteWebDriver(remoteUrl, capabilities);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        init(driver);
        return driver;
    }

    private static WebDriver getDriver(Browser browser, InputStream inputStream, URL remoteUrl) throws IOException {
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
            if (inputStream != null) {
                properties.load(inputStream);
            }
            LOGGER.debug(properties.toString());

            driver = properties.createDriver(remoteUrl);
            WebDriverConfig.setDownloadPath(properties.getDownloadPath());
            WebDriverConfig.setSilentDownload(properties.isSilentDownload());
        }
        init(driver);
        return driver;
    }

    private static WebDriver getDriver(Browser browser, InputStream inputStream) throws IOException {
        return getDriver(browser, inputStream, null);
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

        WebLocatorConfig.setBrowserProperties(properties);

        LOGGER.info("Browser is: {}", browserKey);
        return getBrowser(browserKey);
    }

    /**
     * Switch driver to last browser tab
     *
     * @return oldTabName
     */
    public static String switchToLastTab() {
        int totalTabs;
        int time = 0;
        do {
            Utils.sleep(100L);
            totalTabs = getCountTabs();
            time++;
        } while (totalTabs <= 1 && time < 10);
        return switchToTab(totalTabs - 1);
    }

    public static int getCountTabs() {
        int totalTabs = driver.getWindowHandles().size();
        LOGGER.debug("totalTabs is {}", totalTabs);
        return totalTabs;
    }

    /**
     * Switch driver to first browser tab
     * Tab is not visible but we can interact with it, TODO see how to make it active
     *
     * @return oldTabName
     */
    public static String switchToFirstTab() {
        return switchToTab(0);
    }

    public static String switchToTab(int index) {
        String oldTabName = null;
        try {
            Utils.sleep(100); // to make sure tab has been created
            try {
                oldTabName = driver.getWindowHandle();
                LOGGER.debug("Preview tab id: {}", oldTabName);
                LOGGER.info("Preview tab title : {}", driver.getTitle());
            } catch (NoSuchWindowException e) {
                LOGGER.info("Preview tab already closed");
            }

            List<String> winList = new ArrayList<>(driver.getWindowHandles());
            String tabID = winList.get(index);
            LOGGER.debug("Switch to tab id: {}", tabID);

            driver.switchTo().window(tabID);
            String title = driver.getTitle();
            if (title == null || "".equals(title)) {
                Utils.sleep(200);
                driver.switchTo().window(tabID);
                title = driver.getTitle();
            }
            LOGGER.info("Current tab title : {}", title);
        } catch (NoSuchWindowException e) {
            LOGGER.error("NoSuchWindowException", e);
        }
        return oldTabName;
    }

    /**
     * @param tabCount : this is webdriver
     * @param millis   : time you define to wait the tab open
     * @return true if tab open in the time, false if tab not open in the time.
     */
    public static boolean waitForNewTab(int tabCount, long millis) {
        boolean hasExpectedTabs = false;
        while (!hasExpectedTabs && millis > 0) {
            if (getCountTabs() >= tabCount) {
                hasExpectedTabs = true;
            } else {
                LOGGER.info("Waiting {} ms for new tab...", millis);
                Utils.sleep(100);
            }
            millis -= 100;
        }
        return hasExpectedTabs;
    }
}
