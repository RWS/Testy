package com.sdl.selenium.web.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleComboBoxTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SimpleComboBox(),             "//select"},
                {new SimpleComboBox(container),    "//*[contains(@class, 'container')]//select"},
                {new SimpleComboBox().setId("ID"), "//select[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SimpleComboBox combo, String expectedXpath) {
        Assert.assertEquals(combo.getPath(), expectedXpath);
    }
}
