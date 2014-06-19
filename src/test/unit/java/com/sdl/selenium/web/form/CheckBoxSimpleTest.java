package com.sdl.selenium.web.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckBoxSimpleTest {
    private static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SimpleCheckBox(),           "//input[@type='checkbox']"},
                {new SimpleCheckBox(container),  "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new SimpleCheckBox("SelectId"), "//input[@id='SelectId' and @type='checkbox']"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SimpleCheckBox combo, String expectedXpath) {
        Assert.assertEquals(combo.getPath(), expectedXpath);
    }
}
