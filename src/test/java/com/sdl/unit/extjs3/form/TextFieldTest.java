package com.sdl.unit.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.extjs3.form.TextField;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextFieldTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextField(),                                       "//input[not(@type='hidden')]"},
                {new TextField().setId("ID"),                           "//input[@id='ID' and not(@type='hidden')]"},
                {new TextField("TextFieldClass"),                       "//input[contains(concat(' ', @class, ' '), ' TextFieldClass ') and not(@type='hidden')]"},
                {new TextField(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//input[not(@type='hidden')]"},
                {new TextField(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(), 'Register')]"},
                {new TextField(container, "TextFieldText"),             "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextFieldText']//following-sibling::*//input[not(@type='hidden')]"},
                {new TextField("name", container),                      "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and not(@type='hidden')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextField textField, String expectedXpath) {
        assertThat(textField.getXPath(), equalTo(expectedXpath));
    }
}
