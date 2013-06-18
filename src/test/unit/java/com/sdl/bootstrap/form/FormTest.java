package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FormTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Form(),                      "//form"},
                {new Form().setId("ID"),          "//form[@id='ID']"},
                {new Form(container),             "//*[contains(@class, 'container')]//form"},
                {new Form(container, "TitleForm"),"//*[contains(@class, 'container')]//form[count(.//legend[text()='TitleForm']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Form form, String expectedXpath) {
        Assert.assertEquals(form.getPath(), expectedXpath);
    }
}
