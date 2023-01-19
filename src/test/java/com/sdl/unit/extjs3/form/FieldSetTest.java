package com.sdl.unit.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.extjs3.form.FieldSet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FieldSetTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new FieldSet(),                                         "//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new FieldSet(container, "FieldSetText"),                "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(.)='FieldSetText']) > 0]"},
                {new FieldSet(container).setText("FieldSetText"),        "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(.)='FieldSetText']) > 0]"},
                {new FieldSet(container, "FieldSetCls", "FieldSetText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//fieldset[contains(concat(' ', @class, ' '), ' x-fieldset ') and contains(concat(' ', @class, ' '), ' FieldSetCls ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked')) and count(.//*[normalize-space(.)='FieldSetText']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(FieldSet fieldSet, String expectedXpath) {
        assertThat(fieldSet.getXPath(), equalTo(expectedXpath));
    }
}
