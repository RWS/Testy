package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SelectPickerTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SelectPicker(),                                       "//*"},
                {new SelectPicker().setId("ID"),                           "//*[@id='ID']"},
                {new SelectPicker(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//*"},
                {new SelectPicker(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new SelectPicker(container, "SelectPickerLabel"),         "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='SelectPickerLabel']//following-sibling::*//*"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SelectPicker selectPicker, String expectedXpath) {
        Assert.assertEquals(selectPicker.getPath(), expectedXpath);
    }
}
