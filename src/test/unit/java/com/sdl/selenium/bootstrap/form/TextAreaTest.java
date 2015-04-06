package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.sdl.selenium.web.By.*;
import static org.testng.Assert.assertEquals;

public class TextAreaTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextArea(),                                       "//textarea"},
                {new TextArea().setId("ID"),                           "//textarea[@id='ID']"},
                {new TextArea(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea"},
                {new TextArea(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new TextArea(container, "TextAriaLabel"),             "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextAriaLabel']//following-sibling::*//textarea"},
                {new TextArea(id("ID")),                           "//textarea[@id='ID']"},
                {new TextArea(container(container)),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea"},
                {new TextArea(container(container), xpath("//*[contains(text(), 'Register')]")), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new TextArea(container(container), label("TextAriaLabel")),             "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextAriaLabel']//following-sibling::*//textarea"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextArea textArea, String expectedXpath) {
        assertEquals(textArea.getPathBuilder().getPath(), expectedXpath);
    }
}
