package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WindowTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Window(),             "//*[@role='dialog' and @aria-hidden='false']"},
                {new Window().setId("ID"), "//*[@id='ID' and @role='dialog' and @aria-hidden='false']"},
                {new Window("WindowTitle"),"//*[count(*[contains(@class,'-header')]//*[text()='WindowTitle']) > 0 and @role='dialog' and @aria-hidden='false']"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Window window, String expectedXpath) {
        Assert.assertEquals(window.getPath(), expectedXpath);
    }

}
