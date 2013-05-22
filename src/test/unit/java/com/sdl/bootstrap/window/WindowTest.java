package com.sdl.bootstrap.window;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WindowTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Window(),             "//*[@role='dialog' and @aria-hidden='false']"},
                {new Window().setId("ID"), "//*[@role='dialog' and @aria-hidden='false' and @id='ID']"},
                {new Window("WindowTitle"),"//*[@role='dialog' and @aria-hidden='false' and count(*[contains(@class,'-header')]//*[text()='WindowTitle']) > 0]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Window window, String expectedXpath) {
        Assert.assertEquals(window.getPath(), expectedXpath);
    }

}
