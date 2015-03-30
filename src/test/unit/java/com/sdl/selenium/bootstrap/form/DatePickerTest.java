package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        Assert.assertEquals(datePicker.getPath(), expectedXpath);
    }
}
