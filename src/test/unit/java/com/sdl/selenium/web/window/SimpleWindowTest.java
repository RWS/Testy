package com.sdl.selenium.web.window;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleWindowTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SimpleWindow(), "//*[contains(concat(' ', @class, ' '), ' ui-dialog ui-widget ui-widget-content ') and contains(@style ,'display: block;')]"},
                {new SimpleWindow("text"), "//*[contains(concat(' ', @class, ' '), ' ui-dialog ui-widget ui-widget-content ') and contains(@style ,'display: block;') and count(.//*[text()='text']) > 0]"},
                {new SimpleWindow("text").setId("ID"), "//*[@id='ID' and contains(concat(' ', @class, ' '), ' ui-dialog ui-widget ui-widget-content ') and contains(@style ,'display: block;') and count(.//*[text()='text']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SimpleWindow simpleWindow, String expectedXpath) {
        Assert.assertEquals(simpleWindow.getPath(), expectedXpath);
    }
}