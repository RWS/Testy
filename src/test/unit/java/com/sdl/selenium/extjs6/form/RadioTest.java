package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RadioTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Radio(),                       "//input[contains(concat(' ', @class, ' '), ' x-form-cb-input ') and @role='radio']"},
                {new Radio(container),              "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' x-form-cb-input ') and @role='radio']"},
                {new Radio(container, "LabelText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='LabelText']/../input[contains(concat(' ', @class, ' '), ' x-form-cb-input ') and @role='radio']"},
                {new Radio(container, "LabelText", SearchType.DEEP_CHILD_NODE_OR_SELF), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'LabelText') or count(*//text()[contains(.,'LabelText')]) > 0)]/../input[contains(concat(' ', @class, ' '), ' x-form-cb-input ') and @role='radio']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Radio radio, String expectedXpath) {
        assertThat(radio.getXPath(), equalTo(expectedXpath));
    }
}
