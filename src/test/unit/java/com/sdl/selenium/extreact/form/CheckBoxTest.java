package com.sdl.selenium.extreact.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CheckBoxTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider2() {
        return new Object[][]{
                {new CheckBox(), "//input[@type='checkbox']"},
                {new CheckBox().setClasses("CheckBoxClass"), "//input[contains(concat(' ', @class, ' '), ' CheckBoxClass ') and @type='checkbox']"},
                {new CheckBox(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new CheckBox(container).setElPath("//table//tr[1]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new CheckBox(container, "CheckBox"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'CheckBox') or count(*//text()[contains(.,'CheckBox')]) > 0)]/..//input[@type='checkbox']"},
                {new CheckBox("BoxLabel", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='BoxLabel']/..//input[@type='checkbox']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider2")
    public void getPathSelectorCorrectlyFromConstructors2(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }
}