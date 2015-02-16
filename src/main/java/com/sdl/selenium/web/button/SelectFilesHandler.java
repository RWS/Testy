package com.sdl.selenium.web.button;

import com.sdl.bootstrap.button.RunExe;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SelectFilesHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectFilesHandler.class);
    
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
        LOGGER.info("browse filePath : " + filePath);
        openBrowseWindow();
        selectFiles(filePath);
    }

    /**
     * Upload file with AutoIT exe
     * Use only this: button.browseWithAutoIT(new String[] {"C:\\upload.exe", "C:\\text.txt"});
     *
     * @param filePath   path to upload.exe
     * @return true or false
     */
    public boolean browseWithAutoIT(String[] filePath) {
        openBrowseWindow();
        return RunExe.getInstance().upload(filePath);
    }

    public boolean isElementPresent() {
        return getButtonElement().isElementPresent();
    }

    public void openBrowseWindow() {
        WebDriver driver = WebDriverConfig.getDriver();
        driver.switchTo().window(driver.getWindowHandle()); // TODO is not ready 100% (need to focus on browser)
        buttonElement.focus();
//        buttonElement.sendKeys(Keys.TAB);
        Actions builder = new Actions(driver);
        builder.moveToElement(buttonElement.currentElement).build().perform();
        builder.click().build().perform();
        driver.switchTo().defaultContent();
    }

    public void selectFiles(String path) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            LOGGER.error("AWTException: {}", e);
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
            LOGGER.error("AWTException: {}", e);
        }

        pressEnter(robot); // open window

        writeFileName(path, robot);
        // press enter to close Open dialog
        pressEnter(robot);
    }

    private void pressEnter(Robot robot) {
        robot.keyPress(KeyEvent.VK_ENTER);
        LOGGER.debug("keyPress VK_ENTER");
        robot.keyRelease(KeyEvent.VK_ENTER);
        LOGGER.debug("keyRelease VK_ENTER");
        Utils.sleep(1000);
    }

    private void writeFileName(String filePath, Robot robot) {
        LOGGER.info("path written " + filePath);
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
}
