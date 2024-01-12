package com.sdl.selenium;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.io.File;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestBase {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TestBase.class);
    public static WebDriver driver;

    public static int TEST_RUNS = 0;
    public static int TEST_FAILED = 0;
    public static String version;

    static {
        startSuite();
        version = getVersion();
    }

    public static String getVersion() {
        String version = null;
        Pattern pattern = Pattern.compile("(\\d\\.\\d\\.\\d)");
        Matcher matcher = pattern.matcher(InputData.EXTJS_EXAMPLE_URL);
        if (matcher.find()) {
            version = matcher.group(1);
        }
        return version;
    }

//    @BeforeClass(alwaysRun = true)
//    public void startTest() throws Exception {
//        log.debug("===============================================================");
//        log.debug("|         BeforeClass START-TEST >> enter                     |");
//        log.debug("===============================================================\n");
//    }
//
//    @AfterClass(alwaysRun = true)
//    public void stopTest() {
//        log.debug("==============================================================");
//        log.debug("|         AfterClass STOP-TEST >> enter                       |");
//        log.debug("==============================================================\n");
//    }
//
//    @BeforeMethod
//    public void bm(Method method) {
//        log.info("===============================================================");
//        log.info("|    Start Test (" + (++TEST_RUNS) + ") => " + method.getName());
//        log.info("|    " + method.getDeclaringClass());
//        log.info("===============================================================");
//    }
//
//    @AfterMethod
//    public void tearDown(ITestResult result) {
//        log.debug("tearDown >> enter");
//        String testName = result.getName(); // TODO class name
//        boolean isFailed = !result.isSuccess();
//        log.info(result.getName() + " is " + (isFailed ? "FAILED" : "SUCCESS"));
//        if (isFailed) {
//            log.warn("tests message: " + result.getThrowable().getMessage());
//            StringBuilder args = new StringBuilder();
//            if (result.getParameters() != null) {
//                for (Object o : result.getParameters()) {
//                    args.append(o.toString()).append("; ");
//                }
//            }
//            log.warn("tests arguments: " + args);
//            String fileName = testName + "-failed";
//            log.error("tests. screen: " + Utils.getScreenShot(fileName, getScreensPath()));
//
//            log.info("TOTAL FAILED TESTS: " + (++TEST_FAILED) + " of " + TEST_RUNS);
//            log.info("Refresh page to clean up page for next tests.");
//            driver.navigate().refresh();
//        }
//        log.debug("tearDown << exit");
//        log.info("\n");
//    }

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

    public void openEXTJSUrl(String part, WebLocator el) {
        driver.get(InputData.EXTJS_EXAMPLE_URL + part);
        driver.navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        el.ready(Duration.ofSeconds(20));
        Utils.sleep(1000);
    }
}
