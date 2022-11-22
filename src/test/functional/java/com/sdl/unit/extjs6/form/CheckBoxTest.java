package com.sdl.unit.extjs6.form;

import com.sdl.selenium.extjs6.form.CheckBox;
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
                {new CheckBox().setVersion("6.0.2"), "//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox().setVersion("6.0.2").setClasses("CheckBoxClass"), "//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ') and contains(concat(' ', @class, ' '), ' CheckBoxClass ')]"},
                {new CheckBox(container).setVersion("6.0.2"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox(container).setElPath("//table//tr[1]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new CheckBox(container, "CheckBox").setVersion("6.0.2"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'CheckBox') or count(*//text()[contains(.,'CheckBox')]) > 0)]//following-sibling::*//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox("BoxLabel", container).setVersion("6.0.2"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='BoxLabel']/../*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {new CheckBox().setVersion("6.7.0"), "//input[@type='checkbox']"},
                {new CheckBox().setVersion("6.7.0").setClasses("CheckBoxClass"), "//input[contains(concat(' ', @class, ' '), ' CheckBoxClass ') and @type='checkbox']"},
                {new CheckBox(container).setVersion("6.7.0"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new CheckBox(container).setElPath("//table//tr[1]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new CheckBox(container, "CheckBox").setVersion("6.7.0"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'CheckBox') or count(*//text()[contains(.,'CheckBox')]) > 0)]//following-sibling::*//input[@type='checkbox']"},
                {new CheckBox("BoxLabel", container).setVersion("6.7.0"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='BoxLabel']/..//input[@type='checkbox']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider2() {
        return new Object[][]{
                {new CheckBox(), "//input[@type='checkbox']"},
                {new CheckBox().setClasses("CheckBoxClass"), "//input[contains(concat(' ', @class, ' '), ' CheckBoxClass ') and @type='checkbox']"},
                {new CheckBox(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new CheckBox(container).setElPath("//table//tr[1]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new CheckBox(container, "CheckBox"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'CheckBox') or count(*//text()[contains(.,'CheckBox')]) > 0)]//following-sibling::*//input[@type='checkbox']"},
                {new CheckBox("BoxLabel", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='BoxLabel']/..//input[@type='checkbox']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider2")
    public void getPathSelectorCorrectlyFromConstructors2(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }
}