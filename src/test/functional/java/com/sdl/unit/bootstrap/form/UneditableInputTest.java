package com.sdl.unit.bootstrap.form;

import com.sdl.selenium.bootstrap.form.UneditableInput;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UneditableInputTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new UneditableInput(),                                       "//span[contains(concat(' ', @class, ' '), ' uneditable-input ')]"},
                {new UneditableInput().setId("ID"),                           "//span[@id='ID' and contains(concat(' ', @class, ' '), ' uneditable-input ')]"},
                {new UneditableInput(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//span[contains(concat(' ', @class, ' '), ' uneditable-input ')]"},
                {new UneditableInput(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new UneditableInput(container, "TextFieldText"),             "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//span[contains(concat(' ', @class, ' '), ' uneditable-input ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(UneditableInput uneditableInput, String expectedXpath) {
        assertThat(uneditableInput.getXPath(), equalTo(expectedXpath));
    }
}
