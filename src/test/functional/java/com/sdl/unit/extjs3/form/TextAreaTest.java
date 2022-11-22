package com.sdl.unit.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.extjs3.form.TextArea;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextAreaTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextArea(), "//textarea[not(@type='hidden')]"},
                {new TextArea(container, "TextAreaText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextAreaText']//following-sibling::*//textarea[not(@type='hidden')]"},
                {new TextArea("name", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[@name='name' and not(@type='hidden')]"},
                {new TextArea().setId("IdTextArea"), "//textarea[@id='IdTextArea' and not(@type='hidden')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextArea textArea, String expectedXpath) {
        assertThat(textArea.getXPath(), equalTo(expectedXpath));
    }
}