package com.sdl.selenium.web.form;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleTextFieldTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SimpleTextField(), "//input"},
                {new SimpleTextField("ID"), "//input[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SimpleTextField SimpleTextField, String expectedXpath) {
        Assert.assertEquals(SimpleTextField.getPath(), expectedXpath);
    }
}