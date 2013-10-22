package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataFieldTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DateField(), "//input[not(@type='hidden')]"},
                {new DateField(container, "cls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' cls ') and not(@type='hidden')]"},
                {new DateField("name", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and not(@type='hidden')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DateField dateField, String expectedXpath) {
        Assert.assertEquals(dateField.getPath(), expectedXpath);
    }
}
