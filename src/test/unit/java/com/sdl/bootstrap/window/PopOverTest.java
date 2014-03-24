package com.sdl.bootstrap.window;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PopOverTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new PopOver("Title"),             "//*[@class='popover-title' and text()='Title']//following-sibling::*[@class='popover-content']"},
                {new PopOver("Title", "Message"),  "//*[@class='popover-title' and text()='Title']//following-sibling::*[@class='popover-content' and text()='Message']"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(PopOver popOver, String expectedXpath) {
        Assert.assertEquals(popOver.getPath(), expectedXpath);
    }

}
