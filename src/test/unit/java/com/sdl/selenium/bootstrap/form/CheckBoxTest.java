package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckBoxTest {
    private static final WebLocator container = new WebLocator("container");

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
                {new CheckBox("TextFieldText", container).setAttribute("placeholder", "Search").setAttribute("role", "SearchRole", SearchType.NOT, SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and @placeholder='Search' and not(contains(@role,'SearchRole'))]"},
                {new CheckBox("TextFieldText", container).setAttribute("placeholder", "Search").setAttribute("role", "SearchRole"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and @placeholder='Search' and @role='SearchRole']"},
                {new CheckBox("TextFieldText", container).setAttribute("role", "SearchRole", SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and @role='SearchRole']"},
                {new CheckBox("TextFieldText", container).setAttribute("role", "SearchRole", SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and contains(normalize-space(@role),'SearchRole')]"},
                {new CheckBox("TextFieldText", container).setAttribute("role", "SearchRole", SearchType.STARTS_WITH), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and starts-with(@role,'SearchRole')]"},
                {new CheckBox("TextFieldText", container).setAttribute("role", "|Search|Role", SearchType.CONTAINS_ALL), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and contains(@role,'Search') and contains(@role,'Role')]"},
                {new CheckBox("TextFieldText", container).setAttribute("role", "|Search|Role", SearchType.CONTAINS_ANY), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox' and (contains(@role,'Search') or contains(@role,'Role'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }
}
