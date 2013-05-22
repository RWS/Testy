package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextAreaTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextArea(),                                       "//textarea"},
                {new TextArea().setId("ID"),                           "//textarea[@id='ID']"},
                {new TextArea(container),                              "//*[contains(@class, 'container')]//textarea"},
                {new TextArea(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(@class, 'container')]//*[contains(text(), 'Register')]"},
                {new TextArea(container, "TextAriaLabel"),             "//*[contains(@class, 'container')]//label[text()='TextAriaLabel']//following-sibling::*//textarea"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextArea textArea, String expectedXpath) {
        Assert.assertEquals(textArea.getPath(), expectedXpath);
    }
}
