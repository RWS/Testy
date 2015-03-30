package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RadioTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Radio(), "//input[contains(concat(' ', @class, ' '), ' x-form-radio ')]"},
                {new Radio("LabelText"), "//input[contains(concat(' ', @class, ' '), ' x-form-radio ') and @value='LabelText']"},
                {new Radio(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' x-form-radio ')]"},
                {new Radio(container, "Name"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='Name' and contains(concat(' ', @class, ' '), ' x-form-radio ')]"},
                {new Radio(container, "Name").setLabel("label"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='label']/../input[@name='Name' and contains(concat(' ', @class, ' '), ' x-form-radio ')]"},
                {new Radio("Label", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Label']/../input[contains(concat(' ', @class, ' '), ' x-form-radio ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Radio radio, String expectedXpath) {
        Assert.assertEquals(radio.getPath(), expectedXpath);
    }
}