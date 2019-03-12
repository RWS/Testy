package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CheckBoxTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        WebLocatorConfig.setExtJsVersion("6.0.2");
        return new Object[][]{
                {new CheckBox(),                                     "//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox().setClasses("CheckBoxClass"),         "//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ') and contains(concat(' ', @class, ' '), ' CheckBoxClass ')]"},
                {new CheckBox(container),                            "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox(container).setElPath("//table//tr[1]"),"//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new CheckBox(container, "CheckBox"),       "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'CheckBox') or count(*//text()[contains(.,'CheckBox')]) > 0)]//following-sibling::*//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox("BoxLabel", container),         "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='BoxLabel']/../*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        WebLocatorConfig.setExtJsVersion("6.7.0");
        return new Object[][]{
                {new CheckBox(),                                     "//input[@type='checkbox']"},
                {new CheckBox().setClasses("CheckBoxClass"),         "//input[contains(concat(' ', @class, ' '), ' CheckBoxClass ') and @type='checkbox']"},
                {new CheckBox(container),                            "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new CheckBox(container).setElPath("//table//tr[1]"),"//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new CheckBox(container, "CheckBox"),       "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'CheckBox') or count(*//text()[contains(.,'CheckBox')]) > 0)]//following-sibling::*//input[@type='checkbox']"},
                {new CheckBox("BoxLabel", container),         "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='BoxLabel']/..//input[@type='checkbox']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }
}
