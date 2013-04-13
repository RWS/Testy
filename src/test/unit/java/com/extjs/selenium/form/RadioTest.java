package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RadioTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Radio("LabelText"), "//input[contains(@class, 'x-form-radio') and @value='LabelText']"},
                {new Radio(container), "//*[contains(@class, 'container')]//input[contains(@class, 'x-form-radio')]"},
                {new Radio(container, "Name"), "//*[contains(@class, 'container')]//input[contains(@class, 'x-form-radio') and contains(@name,'Name')]"},
                {new Radio("Label", container), "//*[contains(@class, 'container')]//label[text()='Label']/..//input[contains(@class, 'x-form-radio')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Radio radio, String expectedXpath) {
        Assert.assertEquals(radio.getPath(), expectedXpath);
    }
}