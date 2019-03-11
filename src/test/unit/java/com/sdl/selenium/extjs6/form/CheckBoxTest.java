package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CheckBoxTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new CheckBox(),                                       "//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox().setClasses("ComboBoxClass"),          "//input[contains(concat(' ', @class, ' '), ' x-form-text ') and contains(concat(' ', @class, ' '), ' ComboBoxClass ')]"},
                {new CheckBox(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' x-form-text ')]"},
                {new CheckBox(container).setElPath("//table//tr[1]"),"//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new CheckBox(container, "ComboBoxText"),              "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'ComboBoxText') or count(*//text()[contains(.,'ComboBoxText')]) > 0)]//following-sibling::*//input[contains(concat(' ', @class, ' '), ' x-form-text ')]"},
                {new CheckBox("BoxLabel", container),              "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'ComboBoxText') or count(*//text()[contains(.,'ComboBoxText')]) > 0)]//following-sibling::*//input[contains(concat(' ', @class, ' '), ' x-form-text ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }
}
