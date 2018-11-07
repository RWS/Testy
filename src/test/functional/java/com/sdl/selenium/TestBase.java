package com.sdl.selenium;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;

@Slf4j
public class TestBase {

    public static WebDriver driver;

    public static int TEST_RUNS = 0;
    public static int TEST_FAILED = 0;

    static {
        startSuite();
    }

    @BeforeClass(alwaysRun = true)
    public void startTest() throws Exception {
        log.debug("===============================================================");
        log.debug("|         BeforeClass START-TEST >> enter                     |");
        log.debug("===============================================================\n");
    }

    @AfterClass(alwaysRun = true)
    public void stopTest() {
        log.debug("==============================================================");
        log.debug("|         AfterClass STOP-TEST >> enter                       |");
        log.debug("==============================================================\n");
    }

    @BeforeMethod
    public void bm(Method method) throws Exception {
        log.info("===============================================================");
        log.info("|    Start Test (" + (++TEST_RUNS) + ") => " + method.getName());
        log.info("|    " + method.getDeclaringClass());
        log.info("===============================================================");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        log.debug("tearDown >> enter");
        String testName = result.getName(); // TODO class name
        boolean isFailed = !result.isSuccess();
        log.info(result.getName() + " is " + (isFailed ? "FAILED" : "SUCCESS"));
        if (isFailed) {
            log.warn("tests message: " + result.getThrowable().getMessage());
            String args = "";
            if (result.getParameters() != null) {
                for (Object o : result.getParameters()) {
                    args += o.toString() + "; ";
                }
            }
            log.warn("tests arguments: " + args);
            String fileName = testName + "-failed";
            log.error("tests. screen: " + Utils.getScreenShot(fileName, getScreensPath()));

            log.info("TOTAL FAILED TESTS: " + (++TEST_FAILED) + " of " + TEST_RUNS);
            log.info("Refresh page to clean up page for next tests.");
            driver.navigate().refresh();
        }
        log.debug("tearDown << exit");
        log.info("\n");
    }

    private static void startSuite() {
        log.info("===============================================================");
        log.info("|          Browser: " + InputData.BROWSER_CONFIG);
        log.info("|          AUT URL: " + InputData.LOGIN_URL);
        log.info("===============================================================\n");
        
        try {
            driver = WebDriverConfig.getWebDriver(InputData.BROWSER_CONFIG);
            if (driver != null) {
                driver.get(InputData.LOGIN_URL);
            }
            FileUtils.forceMkdir(new File(WebDriverConfig.getDownloadPath()));
        } catch (Exception e) {
            log.error("Exception when start suite", e);
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
