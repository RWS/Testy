package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RadioGroupTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new RadioGroup(), "//*[contains(concat(' ', @class, ' '), ' x-form-radio-group ')]"},
                {new RadioGroup(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-radio-group ')]"},
                {new RadioGroup(container, "Name"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-radio-group ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(RadioGroup radioGroup, String expectedXpath) {
        Assert.assertEquals(radioGroup.getPath(), expectedXpath);
    }
}