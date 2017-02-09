package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckBoxTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new CheckBox(),                  "//input[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox(container),         "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox(container, "name"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox combo, String expectedXpath) {
        Assert.assertEquals(combo.getXPath(), expectedXpath);
    }
}
