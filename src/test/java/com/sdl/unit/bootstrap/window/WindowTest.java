package com.sdl.unit.bootstrap.window;

import com.sdl.selenium.bootstrap.window.Window;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WindowTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Window(),             "//*[@role='dialog' and @aria-hidden='false']"},
                {new Window().setId("myModal"), "//*[@id='myModal' and @role='dialog' and @aria-hidden='false']"},
                {new Window("Modal title"),"//*[count(.//*[contains(concat(' ', @class, ' '), ' modal-header ')]//*[text()='Modal title']) > 0 and @role='dialog' and @aria-hidden='false']"},
                {new Window().setTitle("Modal title", SearchType.STARTS_WITH),"//*[count(.//*[contains(concat(' ', @class, ' '), ' modal-header ')]//*[starts-with(text(),'Modal title')]) > 0 and @role='dialog' and @aria-hidden='false']"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Window window, String expectedXpath) {
        assertThat(window.getXPath(), equalTo(expectedXpath));
    }
}
