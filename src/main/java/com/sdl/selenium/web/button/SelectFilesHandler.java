package com.sdl.selenium.web.button;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class SelectFilesHandler {

    private static final Logger logger = Logger.getLogger(SelectFilesHandler.class);
    private WebLocator buttonElement;

    public SelectFilesHandler() {

    }

    public SelectFilesHandler(WebLocator buttonElement) {
        setButtonElement(buttonElement);
    }

    public WebLocator getButtonElement() {
        return buttonElement;
    }

    public void setButtonElement(WebLocator buttonElement) {
        this.buttonElement = buttonElement;
    }

    public void browse(String filePath) {
        logger.info("browse filePath : " + filePath);
        openBrowseWindow();
        selectFiles(filePath);
    }

    /**
     * Uploud file with AutoIT exe
     * Use only this: button.browseWithAutoIT(new String[] {"C:\\upload.exe", "C:\\text.txt"});
     */
    public void browseWithAutoIT(String[] filePath) {
        logger.info("browse filePath : " + filePath[1]);
        openBrowseWindow();
        try {
            Process process = Runtime.getRuntime().exec(filePath[0] + " " + filePath[1] + " " + uploadName());
            if(0 != process.waitFor()){
                Assert.fail();
            }
//            Utils.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String uploadName(){
        return WebLocator.driver instanceof FirefoxDriver ? "File Upload" : "Open";
    }

    public boolean isElementPresent() {
        return getButtonElement().isElementPresent();
    }

    public void openBrowseWindow() {
        WebDriver driver = WebLocator.getDriver();
        driver.switchTo().window(driver.getWindowHandle()); // TODO is not ready 100% (need to focus on browser)
        buttonElement.focus();
//        buttonElement.sendKeys(Keys.TAB);
        Actions builder = new Actions(driver);
        builder.moveToElement(buttonElement.currentElement).build().perform();
        builder.click().build().perform();
        driver.switchTo().defaultContent();
//        String parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
//        WebDriver popup = null;
//        Iterator windowIterator = driver.getWindowHandles().iterator();
//        while(windowIterator.hasNext()) {
//            String windowHandle = windowIterator.next().toString();
//            popup = driver.switchTo().window(windowHandle);
//            logger.debug("title: " + popup.getTitle());
//            if (popup.getTitle().equals("Google")) {
//                break;
//            }
//        }
//        logger.debug("title: " + driver.getTitle());
    }

    public void selectFiles(String path) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            logger.error(e);
        }

        writeFileName(path, robot);
        // press enter to close Open dialog
        pressEnter(robot);
    }

    public void downloadFiles(String path) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            logger.error(e);
        }

        pressEnter(robot); // open window

        writeFileName(path, robot);
        // press enter to close Open dialog
        pressEnter(robot);
    }

    private void pressEnter(Robot robot) {
        robot.keyPress(KeyEvent.VK_ENTER);
        logger.debug("keyPress VK_ENTER");
        robot.keyRelease(KeyEvent.VK_ENTER);
        logger.debug("keyRelease VK_ENTER");
        Utils.sleep(1000);
    }

    private void writeFileName(String filePath, Robot robot) {
        logger.info("path written " + filePath);
        char[] pathChars = filePath.toUpperCase().toCharArray();
        for (char c : pathChars) {
            if (c == '\\') {
                robot.keyPress(KeyEvent.VK_BACK_SLASH);
                robot.keyRelease(KeyEvent.VK_BACK_SLASH);
            } else if (c == ':') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (c == '_') {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else {
                robot.keyPress(c);
                robot.keyRelease(c);
            }
        }
        Utils.sleep(500);
    }

    /// TODO extract browseUploadTMXDatabase from this class
//    public void browseUploadTMXDatabase(String path) {
//        waitToRender();
//        int xPosition = 20;
//        // TODO if singlewindow
//        logger.warn("This must be identified dinamically");
//        logger.info("browse path : " + path);
//
//
//        String buttonPath = getPath();
////        String buttonPath = "//*[contains(@class, 'x-window') and contains(@style ,'visibility: visible;') and count((* | (*[contains(@class, '-tl')]//*))[contains(@class,'x-window-header')]//*[text()='TERM AND BRAND MANAGEMENT']) > 0]//*[contains(@class, 'x-tab-panel') and count(*[contains(@class,'x-tab-panel-header')]//*[text()='Term Management']) > 0]//*[contains(@class, 'x-tab-panel-body')]/*[not(contains(@class, 'x-hide-display'))]//input[contains(@class, 'x-form-file') and contains(@name,'fileData')]";
//        // to scroll to this element (if element is not visible)
//        driver.findElement(By.xpath(buttonPath)).sendKeys(Keys.TAB);
////        r.keyPress(KeyEvent.VK_ENTER);
//        //need to keem mouse movement and action because for some reason
//        //the browser sometimes looses focus and ENTER key down cannot be performed
//        String movePosition = buttonPath ;
//
////        driver.manage().window().maximize();  //TODO
//        driver.manage().window().setSize(new Dimension(100,100));
//        driver.manage().window().setPosition(new Point(0,0));
//         Actions builder = new Actions(driver);
//        builder.moveToElement(driver.findElement(By.xpath(movePosition))).build().perform();
//        builder.click().build().perform();
//        driver.findElement(By.xpath(buttonPath)).sendKeys(Keys.TAB);
//        Utils.sleep(2000);
//         Robot r = null;
//        try {
//            r = new Robot();
//        } catch (AWTException e) {
//            logger.error(e);
//        }
//
//        // position of Browse button
////        logger.debug("getScreenY() 1: " + getScreenY());
////        int left = selenium.getElementPositionLeft(buttonPath).intValue() + xPosition;
////        int top = getScreenY();
////        //  add 8 px to be sure we'll click inside the button
////        top += 10;
////        r.mouseMove(left, top);
//        Utils.sleep(200);
////        logger.debug("getScreenY() 2: " + getScreenY());
////        logger.info("mouseMove: " + left + " x " + top);
//
//        // TODO temporary for debugging
//       /* String screensPath = properties.getProjectDir()+ "\\reports\\browse";
//        logger.info("Screenshot: " + screensPath + getAttributeId() +".jpg");
//
////        r.mousePress(InputEvent.BUTTON1_MASK);
//        Utils.sleep(50);
////        logger.info("mousePress");
////        logger.debug("getScreenY() 3: " + getScreenY());
////        r.mouseRelease(InputEvent.BUTTON1_MASK);
//        Utils.sleep(1500);
////        logger.debug("mouseRelease");
//*/
//        // input file path, field is focused by default
//        char[] s = path.toUpperCase().toCharArray();
//
//        for (char c : s) {
//            if (c == '\\') {
//                r.keyPress(KeyEvent.VK_BACK_SLASH);
//                r.keyRelease(KeyEvent.VK_BACK_SLASH);
//            } else if (c == ':') {
//                r.keyPress(KeyEvent.VK_SHIFT);
//                r.keyPress(KeyEvent.VK_SEMICOLON);
//                r.keyRelease(KeyEvent.VK_SEMICOLON);
//                r.keyRelease(KeyEvent.VK_SHIFT);
//            } else if (c == '_') {
//                r.keyPress(KeyEvent.VK_SHIFT);
//                r.keyPress(KeyEvent.VK_MINUS);
//                r.keyRelease(KeyEvent.VK_MINUS);
//                r.keyRelease(KeyEvent.VK_SHIFT);
//
//            } else {
//                r.keyPress(c);
//                r.keyRelease(c);
//            }
//        }
//        Utils.sleep(500);
//        // press enter to close Open dialog
//        r.keyPress(KeyEvent.VK_ENTER);
//        logger.debug("keyPress VK_ENTER");
//        r.keyRelease(KeyEvent.VK_ENTER);
//        logger.debug("keyRelease VK_ENTER");
//        Utils.sleep(500);
//
////        driver.manage().window().maximize();
//    }
}
