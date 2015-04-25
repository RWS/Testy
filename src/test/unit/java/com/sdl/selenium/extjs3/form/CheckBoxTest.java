package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckBoxTest {
    public static ExtJsComponent container = new ExtJsComponent(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Checkbox(),                  "//input[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new Checkbox(container),         "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new Checkbox(container, "name"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},

                {new Checkbox(By.container(container)),         "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new Checkbox(By.container(container), By.name("name")), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Checkbox combo, String expectedXpath) {
        Assert.assertEquals(combo.getPath(), expectedXpath);
    }
}
