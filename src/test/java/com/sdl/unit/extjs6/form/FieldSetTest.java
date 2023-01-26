package com.sdl.unit.extjs6.form;

import com.sdl.selenium.extjs6.form.FieldSet;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FieldSetTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new FieldSet(),                      "//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new FieldSet(container),             "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new FieldSet(container, "FieldSet"), "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(.)='FieldSet']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(FieldSet field, String expectedXpath) {
        assertThat(field.getXPath(), equalTo(expectedXpath));
    }
}
