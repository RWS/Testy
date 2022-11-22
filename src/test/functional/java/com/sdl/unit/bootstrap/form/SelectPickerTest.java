package com.sdl.unit.bootstrap.form;

import com.sdl.selenium.bootstrap.form.SelectPicker;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SelectPickerTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SelectPicker(),                                       "//button[contains(concat(' ', @class, ' '), ' dropdown-toggle ')]"},
                {new SelectPicker().setId("ID"),                           "//button[@id='ID' and contains(concat(' ', @class, ' '), ' dropdown-toggle ')]"},
                {new SelectPicker(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//button[contains(concat(' ', @class, ' '), ' dropdown-toggle ')]"},
                {new SelectPicker(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new SelectPicker(container, "SelectPickerLabel"),         "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='SelectPickerLabel']//following-sibling::*//button[contains(concat(' ', @class, ' '), ' dropdown-toggle ')]"},
                {new SelectPicker(container).setId("ID"),                         "//*[contains(concat(' ', @class, ' '), ' container ')]//button[@id='ID' and contains(concat(' ', @class, ' '), ' dropdown-toggle ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SelectPicker selectPicker, String expectedXpath) {
        assertThat(selectPicker.getXPath(), equalTo(expectedXpath));
    }
}
