package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextAreaTest {
    public static ExtJsComponent container = new ExtJsComponent(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextArea(), "//textarea[not(@type='hidden')]"},
                {new TextArea(container, "TextAreaText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextAreaText']//following-sibling::*//textarea[not(@type='hidden')]"},
                {new TextArea("name", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[@name='name' and not(@type='hidden')]"},
                {new TextArea().setId("IdTextArea"), "//textarea[@id='IdTextArea' and not(@type='hidden')]"},

                {new TextArea(By.container(container), By.label("TextAreaText")), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='TextAreaText']//following-sibling::*//textarea[not(@type='hidden')]"},
                {new TextArea(By.container(container), By.name("name")), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[@name='name' and not(@type='hidden')]"},
                {new TextArea(By.id("IdTextArea")), "//textarea[@id='IdTextArea' and not(@type='hidden')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextArea textArea, String expectedXpath) {
        Assert.assertEquals(textArea.getPath(), expectedXpath);
    }
}