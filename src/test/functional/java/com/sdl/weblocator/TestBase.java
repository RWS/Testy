package com.sdl.weblocator;

import com.extjs.selenium.button.Button;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestBase.class);
    
    public static WebDriver driver;

    public static int TEST_RUNS = 0;
    public static int TEST_FAILED = 0;

    public static final String SERVER = InputData.SERVER_URL;

    // start suite & open browser
    @BeforeSuite(alwaysRun = true)
    public void startSuite() throws Exception {
        LOGGER.info("===============================================================");
        LOGGER.info("|          BeforeSuite START-SUITE >> enter                    |");
        LOGGER.info("=============================================================\n");
        initSeleniumStart();
    }

    @AfterSuite(alwaysRun = true)
    public void stopSuite() {
        LOGGER.info("===============================================================");
        LOGGER.info("|          AfterSuite STOP-SUITE >> enter                      |");
        LOGGER.info("===============================================================\n");
        initSeleniumEnd();
    }

    @BeforeClass(alwaysRun = true)
    public void startTest() throws Exception {
        LOGGER.debug("===============================================================");
        LOGGER.debug("|         BeforeClass START-TEST >> enter                     |");
        LOGGER.debug("===============================================================\n");
    }

    @AfterClass(alwaysRun = true)
    public void stopTest() {
        LOGGER.debug("==============================================================");
        LOGGER.debug("|         AfterClass STOP-TEST >> enter                       |");
        LOGGER.debug("==============================================================\n");
    }


    @BeforeMethod
    public void bm(Method method) throws Exception {
        LOGGER.info("===============================================================");
        LOGGER.info("|    Start Test (" + (++TEST_RUNS) + ") => " + method.getName());
        LOGGER.info("|    " + method.getDeclaringClass());
        LOGGER.info("===============================================================");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        LOGGER.debug("tearDown >> enter");
        String testName = result.getName(); // TODO class name
        boolean isFailed = !result.isSuccess();
        LOGGER.info(result.getName() + " is " + (isFailed ? "FAILED" : "SUCCESS"));
        if (isFailed) {
            LOGGER.warn("tests message: " + result.getThrowable().getMessage());
            String args = "";
            if (result.getParameters() != null) {
                for (Object o : result.getParameters()) {
                    args += o.toString() + "; ";
                }
            }
            LOGGER.warn("tests arguments: " + args);
            String fileName = testName + "-failed";
            LOGGER.error("tests. screen: " + Utils.getScreenShot(fileName, getScreensPath()));

            LOGGER.info("TOTAL FAILED TESTS: " + (++TEST_FAILED) + " of " + TEST_RUNS);
            LOGGER.info("Refresh page to clean up page for next tests.");
            driver.navigate().refresh();
        }
        LOGGER.debug("tearDown << exit");
        LOGGER.info("\n");
    }

    private void initSeleniumStart() throws Exception {
        LOGGER.info("===============================================================");
        LOGGER.info("|          Browser: " + InputData.BROWSER_CONFIG);
        LOGGER.info("|          AUT URL: " + SERVER);
        LOGGER.info("===============================================================\n");

        driver = WebDriverConfig.getWebDriver(InputData.BROWSER_CONFIG);
        driver.manage().window().maximize();
        driver.get(SERVER);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    }

    private void initSeleniumEnd() {
        LOGGER.info("===============================================================");
        LOGGER.info("|          Stopping driver (closing browser)                   |");
        LOGGER.info("===============================================================");
        driver.quit();
        LOGGER.debug("===============================================================");
        LOGGER.debug("|         Driver stopped (browser closed)                     |");
        LOGGER.debug("===============================================================\n");
    }

    public static String getScreensPath() {
//        TestProperties properties = TestProperties.getInstance();
//        return properties.getProjectDir()+ "\\reports\\screens\\";
        return "\\reports\\screens\\";
    }

    public void showComponent(String buttonText) {
        WebLocator tbar = new WebLocator().setId("top-toolbar");
        Button showComponentButton = new Button(tbar, buttonText);
        showComponentButton.assertClick();
    }
}
