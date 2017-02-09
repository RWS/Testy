package com.sdl.selenium;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;

public class TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestBase.class);
    
    public static WebDriver driver;

    public static int TEST_RUNS = 0;
    public static int TEST_FAILED = 0;

    static {
        startSuite();
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

    private static void startSuite() {
        LOGGER.info("===============================================================");
        LOGGER.info("|          Browser: " + InputData.BROWSER_CONFIG);
        LOGGER.info("|          AUT URL: " + InputData.LOGIN_URL);
        LOGGER.info("===============================================================\n");
        
        try {
            driver = WebDriverConfig.getWebDriver(InputData.BROWSER_CONFIG);
            if (driver != null) {
                driver.get(InputData.LOGIN_URL);
            }
            FileUtils.forceMkdir(new File(WebDriverConfig.getDownloadPath()));
        } catch (Exception e) {
            LOGGER.error("Exception when start suite", e);
        }
    }

    public static String getScreensPath() {
//        TestProperties properties = TestProperties.getInstance();
//        return properties.getProjectDir()+ "\\reports\\screens\\";
        return "\\reports\\screens\\";
    }

    public void showComponent(String buttonText) {
        WebLocator tbar = new WebLocator().setId("top-toolbar");
        Button showComponentButton = new Button(tbar, buttonText);
        showComponentButton.click();
    }
}
