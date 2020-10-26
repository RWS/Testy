package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FormTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
//                {new Form(),                      "//form"},
//                {new Form().setId("ID"),          "//form[@id='ID']"},
//                {new Form(container),             "//*[contains(concat(' ', @class, ' '), ' container ')]//form"},
//                {new Form(container, "TitleForm"),"//*[contains(concat(' ', @class, ' '), ' container ')]//form[count(.//legend[text()='TitleForm']) > 0]"},
//                {new Form(container).setTitle("TitleForm"),"//*[contains(concat(' ', @class, ' '), ' container ')]//form[count(.//legend[contains(text(),'TitleForm')]) > 0]"},
//                {new Form(container, "TitleForm").setId("ID"), "//*[contains(concat(' ', @class, ' '), ' container ')]//form[@id='ID' and count(.//legend[text()='TitleForm']) > 0]"},
                {new Form(container, "TitleForm").setTemplateTitle(null), "//*[contains(concat(' ', @class, ' '), ' container ')]//form[@title='TitleForm']"},
                {new Form(container).setTitle("|Title|Form", SearchType.CONTAINS_ANY), "//*[contains(concat(' ', @class, ' '), ' container ')]//form[count(.//legend[(contains(.,'Title') or contains(.,'Form'))]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Form form, String expectedXpath) {
        assertThat(form.getXPath(), equalTo(expectedXpath));
    }
}
