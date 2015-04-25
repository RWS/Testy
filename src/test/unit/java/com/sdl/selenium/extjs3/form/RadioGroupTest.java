package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RadioGroupTest {
    public static ExtJsComponent container = new ExtJsComponent(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new RadioGroup(), "//*[contains(concat(' ', @class, ' '), ' x-form-radio-group ')]"},
                {new RadioGroup(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-radio-group ')]"},
                {new RadioGroup(container, "Name"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-radio-group ')]"},

                {new RadioGroup(By.container(container)), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-radio-group ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(RadioGroup radioGroup, String expectedXpath) {
        Assert.assertEquals(radioGroup.getPath(), expectedXpath);
    }
}