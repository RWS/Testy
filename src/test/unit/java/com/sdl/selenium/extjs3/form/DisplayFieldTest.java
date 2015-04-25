package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DisplayFieldTest {
    public static ExtJsComponent container = new ExtJsComponent(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DisplayField(),                    "//*[contains(concat(' ', @class, ' '), ' x-form-display-field ') and not(@type='hidden')]"},
                {new DisplayField(container, "Label"),  "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Label']//following-sibling::*//*[contains(concat(' ', @class, ' '), ' x-form-display-field ') and not(@type='hidden')]"},
                {new DisplayField("name", container),   "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@name='name' and contains(concat(' ', @class, ' '), ' x-form-display-field ') and not(@type='hidden')]"},

                {new DisplayField(By.container(container), By.label("Label")),  "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Label']//following-sibling::*//*[contains(concat(' ', @class, ' '), ' x-form-display-field ') and not(@type='hidden')]"},
                {new DisplayField(By.container(container), By.name("name")),   "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@name='name' and contains(concat(' ', @class, ' '), ' x-form-display-field ') and not(@type='hidden')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DisplayField displayField, String expectedXpath) {
        Assert.assertEquals(displayField.getPath(), expectedXpath);
    }
}
