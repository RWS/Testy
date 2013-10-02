package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UneditableInputTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new UneditableInput(),                                       "//span[contains(@class, 'uneditable-input')]"},
                {new UneditableInput().setId("ID"),                           "//span[@id='ID' and contains(@class, 'uneditable-input')]"},
                {new UneditableInput(container),                              "//*[contains(@class, 'container')]//span[contains(@class, 'uneditable-input')]"},
                {new UneditableInput(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(@class, 'container')]//*[contains(text(), 'Register')]"},
                {new UneditableInput(container, "TextFieldText"),             "//*[contains(@class, 'container')]//label[text()='TextFieldText']//following-sibling::*//span[contains(@class, 'uneditable-input')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(UneditableInput uneditableInput, String expectedXpath) {
        Assert.assertEquals(uneditableInput.getPath(), expectedXpath);
    }
}
