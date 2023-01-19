package com.sdl.unit.bootstrap.form;

import com.sdl.selenium.bootstrap.form.TextArea;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextArea textArea, String expectedXpath) {
        assertThat(textArea.getXPath(), equalTo(expectedXpath));
    }
}
