package com.sdl.selenium.jqueryui.window;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WindowTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Window(), "//*[contains(concat(' ', @class, ' '), ' ui-dialog ui-widget ui-widget-content ') and contains(@style, 'display: block;')]"},
                {new Window("text"), "//*[contains(concat(' ', @class, ' '), ' ui-dialog ui-widget ui-widget-content ') and count(.//*[text()='text']) > 0 and contains(@style, 'display: block;')]"},
                {new Window("text").setId("ID"), "//*[@id='ID' and contains(concat(' ', @class, ' '), ' ui-dialog ui-widget ui-widget-content ') and count(.//*[text()='text']) > 0 and contains(@style, 'display: block;')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Window window, String expectedXpath) {
        assertThat(window.getXPath(), equalTo(expectedXpath));
    }
}