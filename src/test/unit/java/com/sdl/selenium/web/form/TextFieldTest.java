package com.sdl.selenium.web.form;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextFieldTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextField(), "//input"},
                {new TextField("ID"), "//input[@id='ID']"},
                {new TextField("ID").setType("text"), "//input[@id='ID' and @type='text']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextField TextField, String expectedXpath) {
        Assert.assertEquals(TextField.getXPath(), expectedXpath);
    }
}