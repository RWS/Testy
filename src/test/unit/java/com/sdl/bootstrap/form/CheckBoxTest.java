package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckBoxTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new CheckBox(),                                       "//input[@type='checkbox']"},
                {new CheckBox().setId("ID"),                           "//*[@id='ID']//input[@type='checkbox']"},
                {new CheckBox(container),                              "//*[contains(@class, 'container')]//input[@type='checkbox']"},
                {new CheckBox(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(@class, 'container')]//*[contains(text(), 'Register')]"},
                {new CheckBox(container, "TextFieldText"),             "//*[contains(@class, 'container')]//label[contains(text(),'TextFieldText')]//input[@type='checkbox']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox checkBox, String expectedXpath) {
        Assert.assertEquals(checkBox.getPath(), expectedXpath);
    }
}
