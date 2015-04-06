package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextFieldTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextField(),                                       "//input[@type='text']"},
                {new TextField().setId("ID"),                           "//input[@id='ID' and @type='text']"},
                {new TextField(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='text']"},
                {new TextField(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new TextField(container, "TextFieldText"),             "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='text']"},

                {new TextField(By.id("ID")),                           "//input[@id='ID' and @type='text']"},
                {new TextField(By.container(container)),               "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='text']"},
                {new TextField(By.container(container), By.xpath("//*[contains(text(), 'Register')]")), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new TextField(By.container(container), By.label("TextFieldText")),  "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='text']"},

        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextField textField, String expectedXpath) {
        Assert.assertEquals(textField.getPathBuilder().getPath(), expectedXpath);
    }
}
