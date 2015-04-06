package com.sdl.selenium.web.form;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.sdl.selenium.web.form.by.By.id;
import static com.sdl.selenium.web.form.by.By.type;
import static org.testng.Assert.assertEquals;

public class TextFieldTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextField(), "//input"},
                {new TextField("ID"), "//input[@id='ID']"},
                {new TextField("ID").setType("text"), "//input[@id='ID' and @type='text']"},

                {new TextField(id("ID")), "//input[@id='ID']"},
                {new TextField(id("ID"), type("text")), "//input[@id='ID' and @type='text']"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextField TextField, String expectedXpath) {
        assertEquals(TextField.getPathBuilder().getPath(), expectedXpath);
    }
}