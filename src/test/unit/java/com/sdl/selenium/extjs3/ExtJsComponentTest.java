package com.sdl.selenium.extjs3;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ExtJsComponentTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new ExtJsComponent(),                             "//*"},
                {new ExtJsComponent().withId("ID"),                 "//*[@id='ID']"},
                {new ExtJsComponent("cls"),                        "//*[contains(concat(' ', @class, ' '), ' cls ')]"},
                {new ExtJsComponent(container),                    "//*[contains(concat(' ', @class, ' '), ' container ')]//*"},
                {new ExtJsComponent(container).withElxPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new ExtJsComponent(container, "path"),            "//*[contains(concat(' ', @class, ' '), ' container ')]path"},
                {new ExtJsComponent("cls", container),             "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' cls ')]"},
                {new ExtJsComponent("Text","cls", container),      "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' cls ') and contains(text(),'Text')]"},
                {new ExtJsComponent(container).withVisibility(true),"//*[contains(concat(' ', @class, ' '), ' container ')]//*[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(ExtJsComponent extJsComponent, String expectedXpath) {
        assertEquals(extJsComponent.getXPath(), expectedXpath);
    }

    @Test
    public void getDisabledPathShoutContainDisabledClass(){
        ExtJsComponent locator = new ExtJsComponent(container);
        assertEquals(locator.getXPath(true), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[count(ancestor-or-self::*[contains(@class, 'x-masked') or contains(@class, 'x-item-disabled')]) > 0]");
    }
}
