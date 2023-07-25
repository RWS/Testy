package com.sdl.unit.materialui.form;

import com.sdl.selenium.materialui.form.TextField;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TextFieldTest {

    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextField(), "//input[contains(concat(' ', @class, ' '), ' MuiInputBase-input ')]"},
                {new TextField().setClasses("cls"), "//input[contains(concat(' ', @class, ' '), ' MuiInputBase-input ') and contains(concat(' ', @class, ' '), ' cls ')]"},
                {new TextField(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' MuiInputBase-input ')]"},
                {new TextField(container, "Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Text']//following-sibling::*//input[contains(concat(' ', @class, ' '), ' MuiInputBase-input ')]"},
                {new TextField(container, "Text", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'Text')]//following-sibling::*//input[contains(concat(' ', @class, ' '), ' MuiInputBase-input ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextField textField, String expectedXpath) {
        assertThat(textField.getXPath(), equalTo(expectedXpath));
    }
}
