package com.sdl.unit.extjs6.form;

import com.sdl.selenium.extjs6.form.TextField;
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
                {new TextField(),                                     "//input[contains(concat(' ', @class, ' '), ' x-form-text ')]"},
                {new TextField().setClasses("cls"),                   "//input[contains(concat(' ', @class, ' '), ' x-form-text ') and contains(concat(' ', @class, ' '), ' cls ')]"},
                {new TextField(container),                            "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' x-form-text ')]"},
                {new TextField(container).setElPath("//table//tr[1]"),"//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new TextField(container, "LabelText"),          "//*[contains(concat(' ', @class, ' '), ' container ')]//label[count(*//text()[contains(.,'LabelText')]) > 0]//following-sibling::*//input[contains(concat(' ', @class, ' '), ' x-form-text ')]"},
                {new TextField(container, "LabelText", SearchType.TRIM),"//*[contains(concat(' ', @class, ' '), ' container ')]//label[count(*//text()[contains(normalize-space(.),'LabelText')]) > 0]//following-sibling::*//input[contains(concat(' ', @class, ' '), ' x-form-text ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextField field, String expectedXpath) {
        assertThat(field.getXPath(), equalTo(expectedXpath));
    }
}
