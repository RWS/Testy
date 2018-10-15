package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FieldContainerTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new FieldContainer(),                      "//div[contains(concat(' ', @class, ' '), ' x-form-fieldcontainer ')]"},
                {new FieldContainer(container),             "//*[contains(concat(' ', @class, ' '), ' container ')]//div[contains(concat(' ', @class, ' '), ' x-form-fieldcontainer ')]"},
                {new FieldContainer(container, "Entry"), "//*[contains(concat(' ', @class, ' '), ' container ')]//div[contains(concat(' ', @class, ' '), ' x-form-fieldcontainer ')]//label[text()='Entry']//following-sibling::*"},
                {new FieldContainer(container, "FieldSet", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//div[contains(concat(' ', @class, ' '), ' x-form-fieldcontainer ')]//label[contains(text(),'FieldSet')]//following-sibling::*"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(FieldContainer field, String expectedXpath) {
        assertThat(field.getXPath(), equalTo(expectedXpath));
    }
}
