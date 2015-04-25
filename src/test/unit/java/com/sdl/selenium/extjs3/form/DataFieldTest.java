package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataFieldTest {
    public static ExtJsComponent container = new ExtJsComponent(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DateField(), "//input[not(@type='hidden')]"},
                {new DateField(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[not(@type='hidden')]"},
                {new DateField(container, "cls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' cls ') and not(@type='hidden')]"},
                {new DateField("name", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and not(@type='hidden')]"},

                {new DateField(By.container(container)), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[not(@type='hidden')]"},
                {new DateField(By.container(container), By.classes("cls")), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' cls ') and not(@type='hidden')]"},
                {new DateField(By.container(container), By.name("name")), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and not(@type='hidden')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DateField dateField, String expectedXpath) {
        Assert.assertEquals(dateField.getPath(), expectedXpath);
    }
}
