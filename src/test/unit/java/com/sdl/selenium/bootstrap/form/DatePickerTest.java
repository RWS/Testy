package com.sdl.selenium.bootstrap.form;

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
                {new DatePicker(),                                       "//*[contains(concat(' ', @class, ' '), ' date ')]"},
                {new DatePicker().setId("ID"),                           "//*[@id='ID' and contains(concat(' ', @class, ' '), ' date ')]"},
                {new DatePicker(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' date ')]"},
                {new DatePicker(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new DatePicker(container, "ID"),             "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(concat(' ', @class, ' '), ' date ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DatePicker datePicker, String expectedXpath) {
        assertThat(datePicker.getXPath(), equalTo(expectedXpath));
    }
}
