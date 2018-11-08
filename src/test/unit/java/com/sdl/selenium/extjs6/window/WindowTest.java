package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.web.SearchType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class WindowTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Window(),                             "//*[contains(concat(' ', @class, ' '), ' x-window ')]"},
                {new Window("Title"),                      "//*[contains(concat(' ', @class, ' '), ' x-window ') and count(.//*[contains(@class,'x-window-header') or contains(@class, '-tl')]//*[text()='Title']) > 0]"},
                {new Window("Title", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' x-window ') and count(.//*[contains(@class,'x-window-header') or contains(@class, '-tl')]//*[contains(.,'Title')]) > 0]"},
                {new Window("Title").setTitle("Contains", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' x-window ') and count(.//*[contains(@class,'x-window-header') or contains(@class, '-tl')]//*[contains(.,'Contains')]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Window window, String expectedXpath) {
        assertThat(window.getXPath(), equalTo(expectedXpath));
    }
}
