package com.sdl.selenium.web.utils;

import com.extjs.selenium.grid.GridPanel;
import com.extjs.selenium.panel.Panel;
import com.extjs.selenium.window.Window;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

/**
 * TODO Sa fie o metoda de Utils care sa poti seta din WebLocator path-ul unde sa salveze imaginile in dependenta de WebDriver ori Selenium
 */
public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class);

//    public static void getScreenShot(String fileName, String screensPath) {
//        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        fileName = (dfm.format(new Date())) + " - " + fileName + ".jpg";
//        String filePath = screensPath + fileName;
//        if (WebLocator.hasWebDriver()) {
//            WebDriver driver = WebLocator.getDriver();
//            try {
//                File screensDir = new File(screensPath);
//                screensDir.mkdirs();
//                logger.info("Screenshot: " + filePath);
//
//                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
//                File screenShot = takesScreenshot.getScreenshotAs(OutputType.FILE);
//                screenShot.setWritable(true);
//                File file = new File(filePath);
//                screenShot.renameTo(file);
//            } catch (Exception e) {
//                logger.error("Failed to capture screenshot: " + e);
//            }
//        } else {
//            String resultPath, screenShotsResultsPath;
//            resultPath = new File("tests-output").getAbsolutePath();
//            logger.debug("resultPath: " + resultPath);
//            screenShotsResultsPath = new File(filePath).getAbsolutePath();
//            WebLocator.selenium.captureScreenshot(screenShotsResultsPath);
//            if (!"".equals(resultPath)) {
//                resultPath = "http://clujci01:8085/hudson/job/Bigbird_Selenium_Tests/ws/tests-output";
////                screenShotsResultsPath = resultPath + "/" + this.getClass().getName() + "/" + screenShotName;
//            }
//            logger.info("<a href=\"" + screenShotsResultsPath + "\">Image</a>");
//        }
//    }

    // Test components

    public static void main(String args[]){
        logger.info("start");

        Window w = new Window("MyWin");
        Panel p = new Panel(w, "MyPanel");
        WebLocator el = new WebLocator("cls", p);
        logger.info(el.getRenderMillis());
        logger.info(el);

        WebLocator el2 = new WebLocator(p, "//div/div[4]/div");
        logger.debug(el2);


        GridPanel grid = new GridPanel(p);
        logger.debug(grid);

        p.setElPath("//div/div/div");
        p.setTitle(null);
        GridPanel grid1 = new GridPanel(p);
        logger.debug(grid1);

    }
}
