package com.sdl.weblocator;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private static final Logger logger = Logger.getLogger(TestBase.class);
    public static WebDriver driver;

    public static int TEST_RUNS = 0;
    public static int TEST_FAILED = 0;

    public static final String SERVER = InputData.SERVER_URL;

    // start suite & open browser
    @BeforeSuite(alwaysRun = true)
    public void startSuite() throws Exception {
        logger.info("===============================================================");
        logger.info("|          BeforeSuite START-SUITE >> enter                    |");
        logger.info("=============================================================\n");
        initSeleniumStart();
    }

    @AfterSuite(alwaysRun = true)
    public void stopSuite() {
        logger.info("===============================================================");
        logger.info("|          AfterSuite STOP-SUITE >> enter                      |");
        logger.info("===============================================================\n");
        initSeleniumEnd();
    }

    @BeforeClass(alwaysRun = true)
    public void startTest() throws Exception {
        logger.debug("===============================================================");
        logger.debug("|         BeforeClass START-TEST >> enter                     |");
        logger.debug("===============================================================\n");
    }

    @AfterClass(alwaysRun = true)
    public void stopTest() {
        logger.debug("==============================================================");
        logger.debug("|         AfterClass STOP-TEST >> enter                       |");
        logger.debug("==============================================================\n");
    }


    @BeforeMethod
    public void bm(Method method) throws Exception {
        logger.info("===============================================================");
        logger.info("|    Start Test (" + (++TEST_RUNS) + ") => " + method.getName());
        logger.info("|    " + method.getDeclaringClass());
        logger.info("===============================================================");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.debug("tearDown >> enter");
        String testName = result.getName(); // TODO class name
        boolean isFailed = !result.isSuccess();
        logger.info(result.getName() + " is " + (isFailed ? "FAILED" : "SUCCESS"));
        if (isFailed) {
            logger.warn("tests message: " + result.getThrowable().getMessage());
            String args = "";
            if (result.getParameters() != null) {
                for (Object o : result.getParameters()) {
                    args += o.toString() + "; ";
                }
            }
            logger.warn("tests arguments: " + args);
            String fileName = testName + "-failed";
            logger.error("tests. screen: " + Utils.getScreenShot(fileName, getScreensPath()));

            logger.info("TOTAL FAILED TESTS: " + (++TEST_FAILED) + " of " + TEST_RUNS);
            logger.info("Refresh page to clean up page for next tests.");
            driver.navigate().refresh();
        }
        logger.debug("tearDown << exit");
        logger.info("\n");
    }

    /**
     * This method returns the correct driver, depending on what parameter is given
     *
     * @param browser
     * @return
     */

    public WebDriver getWebDriver(String browser) {
        if (browser.equalsIgnoreCase("*firefox")) {
            ProfilesIni allProfiles = new ProfilesIni();
            FirefoxProfile myProfile = allProfiles.getProfile(InputData.FIREFOX_PROFILE);
            if (myProfile != null) {
                return new FirefoxDriver(myProfile);
            } else {
                return new FirefoxDriver();
            }
        } else if (browser.equalsIgnoreCase("*iexplore")) {
            String driverPathIe = InputData.DRIVER_PATH_IE;
            if (driverPathIe != null && !driverPathIe.equals("")) {
                System.setProperty("webdriver.ie.driver", driverPathIe);
            }
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            return new InternetExplorerDriver(ieCapabilities);
//            return new InternetExplorerDriver();
        } else if (browser.equalsIgnoreCase("*chrome")) {
            if (InputData.DRIVER_PATH_CHROME != null && !InputData.DRIVER_PATH_CHROME.equals("")) {
                System.setProperty("webdriver.chrome.driver", InputData.DRIVER_PATH_CHROME);
            }
            return new ChromeDriver();
        } else if (browser.equalsIgnoreCase("*htmlunit")) {
            return new HtmlUnitDriver(true);
        } else {
            logger.error("Browser not supported");
            return null;
        }
    }

    private void initSeleniumStart() throws Exception {
        logger.info("===============================================================");
        logger.info("|          Browser: " + "*firefox");
        logger.info("|          AUT URL: " + SERVER);
        logger.info("===============================================================\n");

        driver = getWebDriver(InputData.BROWSER);
        driver.manage().window().maximize();
        driver.get(SERVER);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        WebLocator.setDriver(driver);
    }

    private void initSeleniumEnd() {
        logger.info("===============================================================");
        logger.info("|          Stopping driver (closing browser)                   |");
        logger.info("===============================================================");
        driver.close();
        logger.debug("===============================================================");
        logger.debug("|         Driver stopped (browser closed)                     |");
        logger.debug("===============================================================\n");
    }

    public static String getScreensPath() {
//        TestProperties properties = TestProperties.getInstance();
//        return properties.getProjectDir()+ "\\reports\\screens\\";
        return "\\reports\\screens\\";
    }
}
