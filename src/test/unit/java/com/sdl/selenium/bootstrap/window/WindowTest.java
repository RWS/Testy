package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WindowTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Window(),             "//*[@role='dialog' and @aria-hidden='false']"},
                {new Window().setId("ID"), "//*[@id='ID' and @role='dialog' and @aria-hidden='false']"},
                {new Window("WindowTitle"),"//*[count(./*[contains(concat(' ', @class, ' '), ' -header ')]//*[text()='WindowTitle']) > 0 and @role='dialog' and @aria-hidden='false']"},
                {new Window().setTitle("WindowTitle", SearchType.STARTS_WITH),"//*[count(./*[contains(concat(' ', @class, ' '), ' -header ')]//*[starts-with(text(),'WindowTitle')]) > 0 and @role='dialog' and @aria-hidden='false']"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Window window, String expectedXpath) {
        Assert.assertEquals(window.getXPath(), expectedXpath);
    }

}
