package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckBoxTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new CheckBox(),                                       "//input[@type='checkbox']"},
                {new CheckBox().setId("ID"),                           "//input[@id='ID' and @type='checkbox']"},
                {new CheckBox(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new CheckBox(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new CheckBox(container, "TextFieldText"),             "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'TextFieldText')]//input[@type='checkbox']"},
                {new CheckBox("TextFieldText", container),             "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox']"},
                {new CheckBox("TextFieldText", container).setAttribute("placeholder", "Search"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and @placeholder='Search']"},
                {new CheckBox("TextFieldText", container).setAttribute("placeholder", "Search").setAttribute("placeholder", null), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox']"},
                {new CheckBox("TextFieldText", container).setAttribute("placeholder", "Search").setAttribute(null, "Search"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and @placeholder='Search']"},
                {new CheckBox("TextFieldText", container).setAttribute("placeholder", "Search").setAttribute("role", "SearchRole"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and @placeholder='Search' and @role='SearchRole']"},
                {new CheckBox("TextFieldText", container).setAttribute("placeholder", "Search").setAttribute("role", "SearchRole", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and @placeholder='Search' and contains(@role,'SearchRole')]"},
                {new CheckBox("TextFieldText", container).setAttribute("placeholder", "Search").setAttribute("role", "SearchRole", null), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and @placeholder='Search' and contains(@role,'SearchRole')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox checkBox, String expectedXpath) {
        Assert.assertEquals(checkBox.getXPath(), expectedXpath);
    }
}
