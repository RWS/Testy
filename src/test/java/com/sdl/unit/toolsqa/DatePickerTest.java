package com.sdl.unit.toolsqa;

import com.sdl.selenium.toolsqa.DatePicker;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DatePickerTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DatePicker(),                         "//input[@type='text']"},
                {new DatePicker().setId("ID"),             "//input[@id='ID' and @type='text']"},
                {new DatePicker(container),                "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='text']"},
                {new DatePicker(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new DatePicker(container, "Date"),  "//*[contains(concat(' ', @class, ' '), ' container ')]//p[contains(text(),'Date')]//input[@type='text']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DatePicker datePicker, String expectedXpath) {
        assertThat(datePicker.getXPath(), equalTo(expectedXpath));
    }
}
