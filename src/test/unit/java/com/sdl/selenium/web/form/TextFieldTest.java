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
                {new TextField("ID").setPlaceholder("Search"), "//input[@id='ID' and @placeholder='Search']"},
                {new TextField("ID").setPlaceholder(null), "//input[@id='ID']"},
                {new TextField("ID").setPlaceholder("Search").setPlaceholder(null), "//input[@id='ID']"},
                {new TextField("ID").setPlaceholder("Search").setClasses("cls"), "//input[@id='ID' and contains(concat(' ', @class, ' '), ' cls ') and @placeholder='Search']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextField TextField, String expectedXpath) {
        Assert.assertEquals(TextField.getXPath(), expectedXpath);
    }
}