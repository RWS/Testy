package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SpanTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Span(),                                       "//span[contains(@class, 'uneditable-input')]"},
                {new Span().setId("ID"),                           "//span[@id='ID' and contains(@class, 'uneditable-input')]"},
                {new Span(container),                              "//*[contains(@class, 'container')]//span[contains(@class, 'uneditable-input')]"},
                {new Span(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(@class, 'container')]//*[contains(text(), 'Register')]"},
                {new Span(container, "TextFieldText"),             "//*[contains(@class, 'container')]//label[text()='TextFieldText']//following-sibling::*//span[contains(@class, 'uneditable-input')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Span span, String expectedXpath) {
        Assert.assertEquals(span.getPath(), expectedXpath);
    }
}
