package com.extjs.selenium;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ExtJsComponentTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new ExtJsComponent(),                             "//*"},
                {new ExtJsComponent().setId("ID"),                 "//*[@id='ID']"},
                {new ExtJsComponent("cls"),                        "//*[contains(concat(' ', @class, ' '), ' cls ')]"},
                {new ExtJsComponent(container),                    "//*[contains(concat(' ', @class, ' '), ' container ')]//*"},
                {new ExtJsComponent(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new ExtJsComponent(container, "path"),            "//*[contains(concat(' ', @class, ' '), ' container ')]path"},
                {new ExtJsComponent("cls", container),             "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' cls ')]"},
                {new ExtJsComponent("Text","cls", container),      "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' cls ') and contains(text(),'Text')]"},
                {new ExtJsComponent(container).setVisibility(true),"//*[contains(concat(' ', @class, ' '), ' container ')]//*[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(ExtJsComponent extJsComponent, String expectedXpath) {
        assertEquals(extJsComponent.getPath(), expectedXpath);
    }

    @Test
    public void path(){
        ExtJsComponent locator = new ExtJsComponent(container);
        assertEquals(locator.getPath(true), "//*[contains(concat(' ', @class, ' '), ' container ')]//*/ancestor-or-self::*[contains(@class, 'x-masked') or contains(@class, 'x-item-disabled')]");
    }
}
