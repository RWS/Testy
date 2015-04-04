package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.sdl.selenium.web.By.*;
import static org.testng.Assert.assertEquals;

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
                {new CheckBox(id("ID")),                               "//input[@id='ID' and @type='checkbox']"},
                {new CheckBox(container(container)),                   "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new CheckBox(container(container), xpath("//*[contains(text(), 'Register')]")), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new CheckBox(container(container), label("TextFieldText", SearchType.CONTAINS), labelPosition("//")),"//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'TextFieldText')]//input[@type='checkbox']"},
                {new CheckBox(label("TextFieldText"), container(container)),             "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[@type='checkbox']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox checkBox, String expectedXpath) {
        assertEquals(checkBox.getPathBuilder().getPath(), expectedXpath);
    }
}
