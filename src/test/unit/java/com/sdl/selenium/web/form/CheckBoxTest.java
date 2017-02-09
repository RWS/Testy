package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckBoxTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new CheckBox(),           "//input[@type='checkbox']"},
                {new CheckBox(container),  "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new CheckBox("Id"), "//input[@id='Id' and @type='checkbox']"},
                {new CheckBox("Id").setType("check"), "//input[@id='Id' and @type='check']"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox combo, String expectedXpath) {
        Assert.assertEquals(combo.getXPath(), expectedXpath);
    }
}
