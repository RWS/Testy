package com.sdl.weblocator.pageObject;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class PageObjectTest {
    private static final Logger logger = Logger.getLogger(PageObjectTest.class);
    static WebDriver driver = null;
    static WritePage page2 = null;

    public static void main(String[] args) {
        init();

//        StartPage page = PageFactory.initElements(driver, StartPage.class);
        SimplePage page2 = new SimplePage(driver);

//        pageObject(page);
        pageSimple(page2);
        /*for (int i = 0; i < 20; i++) {
            pageObject2(page);
//            pageSimple(page2);
            logger.debug("------------------------------------------");
        }*/
        stop();
    }

    public static void init() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        driver.get("file:///D:/Testy/src/test/functional/bootstrap/index.html");
    }

    public static void pageObject(StartPage page) {
        long startMs = System.currentTimeMillis();
        page.open();

        page2 = PageFactory.initElements(driver, WritePage.class);
        page2.complete();
        page2.save();

        long endMs = System.currentTimeMillis();
        logger.info(String.format("pageObject took %s ms", endMs - startMs));
    }

    public static void pageObject2(StartPage page) {
        long startMs = System.currentTimeMillis();
        page.open();

        page2.complete();
        page2.save();

        long endMs = System.currentTimeMillis();
        logger.info(String.format("pageObject took %s ms", endMs - startMs));
    }

    public static void pageSimple(SimplePage page) {
        long startMs = System.currentTimeMillis();

        page.complete();
        page.save();

        long endMs = System.currentTimeMillis();
        logger.info(String.format("pageSimple took %s ms", endMs - startMs));
    }

    public static void stop() {
        driver.quit();
    }
}
