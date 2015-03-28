package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MultiSelectTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new MultiSelect(),                                       "//button[contains(concat(' ', @class, ' '), ' multiselect dropdown-toggle btn ')]"},
                {new MultiSelect().setId("ID"),                           "//button[@id='ID' and contains(concat(' ', @class, ' '), ' multiselect dropdown-toggle btn ')]"},
                {new MultiSelect(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//button[contains(concat(' ', @class, ' '), ' multiselect dropdown-toggle btn ')]"},
                {new MultiSelect(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new MultiSelect(container, "SelectPickerLabel"),         "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='SelectPickerLabel']//following-sibling::*//button[contains(concat(' ', @class, ' '), ' multiselect dropdown-toggle btn ')]"},
                {new MultiSelect(container).setId("ID"),                         "//*[contains(concat(' ', @class, ' '), ' container ')]//button[@id='ID' and contains(concat(' ', @class, ' '), ' multiselect dropdown-toggle btn ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(MultiSelect multiSelect, String expectedXpath) {
        Assert.assertEquals(multiSelect.getPath(), expectedXpath);
    }
}
