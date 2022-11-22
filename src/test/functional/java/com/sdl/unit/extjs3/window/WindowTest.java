package com.sdl.unit.extjs3.window;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.extjs3.window.Window;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WindowTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Window(),                     "//*[contains(concat(' ', @class, ' '), ' x-window ') and contains(@style, 'visibility: visible;')]"},
                {new Window().setClasses("Cls"),   "//*[contains(concat(' ', @class, ' '), ' x-window ') and contains(concat(' ', @class, ' '), ' Cls ') and contains(@style, 'visibility: visible;')]"},
                {new Window(true),                 "//*[contains(concat(' ', @class, ' '), ' x-window ') and preceding-sibling::*[contains(@class, 'ext-el-mask') and contains(@style, 'display: block')] and contains(@style, 'visibility: visible;')]"},
                {new Window(false),                "//*[contains(concat(' ', @class, ' '), ' x-window ') and contains(@style, 'visibility: visible;')]"},
                {new Window("WindowTitle"),        "//*[contains(concat(' ', @class, ' '), ' x-window ') and count(*[contains(@class,'x-window-header') or contains(@class, '-tl')]//*[text()='WindowTitle']) > 0 and contains(@style, 'visibility: visible;')]"},
                {new Window("WindowTitle", true),  "//*[contains(concat(' ', @class, ' '), ' x-window ') and count(*[contains(@class,'x-window-header') or contains(@class, '-tl')]//*[text()='WindowTitle']) > 0 and preceding-sibling::*[contains(@class, 'ext-el-mask') and contains(@style, 'display: block')] and contains(@style, 'visibility: visible;')]"},
                {new Window("WindowTitle", false), "//*[contains(concat(' ', @class, ' '), ' x-window ') and count(*[contains(@class,'x-window-header') or contains(@class, '-tl')]//*[text()='WindowTitle']) > 0 and contains(@style, 'visibility: visible;')]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Window window, String expectedXpath) {
        assertThat(window.getXPath(), equalTo(expectedXpath));
    }

}
