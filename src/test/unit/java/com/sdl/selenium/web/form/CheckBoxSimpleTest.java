package com.sdl.selenium.web.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckBoxSimpleTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SimpleCheckBox(),           "//input[@type='checkbox']"},
                {new SimpleCheckBox("SelectId"), "//input[@type='checkbox' and @id='SelectId']"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SimpleCheckBox combo, String expectedXpath) {
        Assert.assertEquals(combo.getPath(), expectedXpath);
    }
}
