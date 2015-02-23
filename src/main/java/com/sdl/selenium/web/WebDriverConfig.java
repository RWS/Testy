package com.sdl.selenium.web;

import com.opera.core.systems.OperaDesktopDriver;
import com.sdl.selenium.web.utils.PropertiesReader;
import com.sdl.selenium.web.utils.browsers.ChromeConfigReader;
import com.sdl.selenium.web.utils.browsers.FirefoxConfigReader;
import com.sdl.selenium.web.utils.browsers.IExplorerConfigReader;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    /**
     * @deprecated The RC interface will be removed in Selenium 3.0. Please migrate to using WebDriver.
     *             When Selenium will be removed. Change your code as this method will return true
     */
    public static boolean hasWebDriver() {
        return driver != null;
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
     * @param browserProperties
     * @return webDriver
     */
    public static WebDriver getWebDriver(String browserProperties) throws IOException {
        Browser browser = findBrowser(browserProperties);
        return getDriver(browser, browserProperties);
    }

    /**
     * Create and return new WebDriver
     *
     * @param browser see details {@link com.sdl.selenium.web.Browser}
     * @return webDriver
     * @throws IOException
     */
    public static WebDriver getWebDriver(Browser browser) throws IOException {
        return getDriver(browser, null);
    }

    private static WebDriver getDriver(Browser browser, String browserProperties) throws IOException {
        PropertiesReader properties;
        if (browser == Browser.FIREFOX) {
            properties = new FirefoxConfigReader(browserProperties);
            createFirefoxDriver(properties);
        } else if (browser == Browser.IEXPLORE) {
            properties = new IExplorerConfigReader(browserProperties);
            createIEDriver(properties);
        } else if (browser == Browser.CHROME) {
            properties = new ChromeConfigReader(browserProperties);
            createChromeDriver(properties);
        } else if (browser == Browser.HTMLUNIT) {
            driver = new HtmlUnitDriver(true);
        } else {
            LOGGER.error("Browser not supported" + browser);
            driver = null;
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
            LOGGER.error("BROWSER not supported : " + browserKey + ". Supported browsers: " + Arrays.asList(Browser.values()));
        }
        return browser;
    }

    private static Browser findBrowser(String browserProperties) {
        PropertiesReader properties = new PropertiesReader(browserProperties);
        String browserKey = properties.getProperty("browser");
        return getBrowser(browserKey);
    }

    private static void createIEDriver(PropertiesReader properties) {
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        String driverPath = properties.getProperty("browser.driver.path");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.ie.driver", driverPath);
        }
        driver = new InternetExplorerDriver(ieCapabilities);
    }

    private static void createChromeDriver(PropertiesReader properties) throws IOException {
        String driverPath = properties.getProperty("browser.driver.path");
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
        String property = properties.getProperty("profile.preference.browser.download.dir");
        File file = new File(property);
        setDownloadPath(file.getAbsolutePath());
        String downloadDir = file.getCanonicalPath();
        if (downloadDir != null && !"".equals(downloadDir)) {
            prefs.put("download.default_directory", downloadDir);
        }
        options.setExperimentalOption("prefs", prefs);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        WebDriverConfig.setSilentDownload(!"".equals(property));
    }

    private static void createFirefoxDriver(PropertiesReader properties) throws IOException {
        String profileName = properties.getProperty("browser.profile.name");
        FirefoxProfile myProfile;
        if (!"".equals(profileName) && profileName != null) {
            ProfilesIni allProfiles = new ProfilesIni();
            myProfile = allProfiles.getProfile(profileName);
        } else {
            myProfile = new FirefoxProfile();
        }
        if (myProfile != null) {
            LOGGER.info("profile not null");
            setProfilePreferences(properties, myProfile);

            File file = new File(properties.getProperty("profile.preference.browser.download.dir"));
            setDownloadPath(file.getAbsolutePath());
            String downloadDir = file.getCanonicalPath();
            if (downloadDir != null && !"".equals(downloadDir)) {
                myProfile.setPreference("profile.preference.browser.download.dir", downloadDir);
            }

            driver = new FirefoxDriver(myProfile);
            initSilentDownload(properties);
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

    private static void initSilentDownload(PropertiesReader properties) {
        WebDriverConfig.setSilentDownload(
                !"".equals(properties.getProperty("profile.preference.browser.download.dir")) &&
                        !"".equals(properties.getProperty("profile.preference.browser.helperApps.neverAsk.openFile")) &&
                        !"".equals(properties.getProperty("profile.preference.browser.helperApps.neverAsk.saveToDisk")) &&
                        !(Boolean.valueOf(properties.getProperty("profile.preference.browser.helperApps.alwaysAsk.force"))) &&
                        !(Boolean.valueOf(properties.getProperty("profile.preference.browser.download.panel.shown"))) &&
                        !(Boolean.valueOf(properties.getProperty("profile.preference.browser.download.manager.showAlertOnComplete"))) &&
                        (Boolean.valueOf(properties.getProperty("profile.preference.browser.download.manager.closeWhenDone"))) &&
                        !(Boolean.valueOf(properties.getProperty("profile.preference.browser.download.manager.showWhenStarting"))) &&
                        (Integer.valueOf(properties.getProperty("profile.preference.browser.download.folderList")) == 2)
        );
    }

    private static void setProfilePreferences(PropertiesReader properties, FirefoxProfile myProfile) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            if (key.startsWith("profile.preference.")) {
                String preferenceKey = key.substring(19);
                String value = (String) entry.getValue();
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    myProfile.setPreference(preferenceKey, Boolean.valueOf(value));
                } else {
                    try {
                        int intValue = Integer.parseInt(value);
                        myProfile.setPreference(preferenceKey, intValue);
                    } catch (NumberFormatException e) {
                        myProfile.setPreference(preferenceKey, value);
                    }
                }
            }
        }
    }
}
