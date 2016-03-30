package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SelectPickerTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SelectPicker(),                                       "//button[contains(concat(' ', @class, ' '), ' btn dropdown-toggle ')]"},
                {new SelectPicker().withId("ID"),                           "//button[@id='ID' and contains(concat(' ', @class, ' '), ' btn dropdown-toggle ')]"},
                {new SelectPicker(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//button[contains(concat(' ', @class, ' '), ' btn dropdown-toggle ')]"},
                {new SelectPicker(container).withElxPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new SelectPicker(container, "SelectPickerLabel"),         "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='SelectPickerLabel']//following-sibling::*//button[contains(concat(' ', @class, ' '), ' btn dropdown-toggle ')]"},
                {new SelectPicker(container).withId("ID"),                         "//*[contains(concat(' ', @class, ' '), ' container ')]//button[@id='ID' and contains(concat(' ', @class, ' '), ' btn dropdown-toggle ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SelectPicker selectPicker, String expectedXpath) {
        Assert.assertEquals(selectPicker.getXPath(), expectedXpath);
    }
}
