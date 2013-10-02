package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckBoxTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Checkbox(),                  "//input[contains(@class, 'x-form-checkbox')]"},
                {new Checkbox(container),         "//*[contains(@class, 'container')]//input[contains(@class, 'x-form-checkbox')]"},
                {new Checkbox(container, "name"), "//*[contains(@class, 'container')]//input[@name='name' and contains(@class, 'x-form-checkbox')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Checkbox combo, String expectedXpath) {
        Assert.assertEquals(combo.getPath(), expectedXpath);
    }
}
