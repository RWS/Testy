package com.sdl.weblocator;

import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);
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

    private void initSeleniumStart() throws Exception {
        logger.info("===============================================================");
        logger.info("|          Browser: " + InputData.BROWSER_CONFIG);
        logger.info("|          AUT URL: " + SERVER);
        logger.info("===============================================================\n");

        driver = WebDriverConfig.getWebDriver(InputData.BROWSER_CONFIG);
        driver.manage().window().maximize();
        driver.get(SERVER);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    }

    private void initSeleniumEnd() {
        logger.info("===============================================================");
        logger.info("|          Stopping driver (closing browser)                   |");
        logger.info("===============================================================");
        driver.quit();
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
