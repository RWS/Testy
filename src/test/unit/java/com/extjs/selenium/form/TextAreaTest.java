package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextAreaTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextArea(), "//textarea[not (@type='hidden') ]"},
                {new TextArea(container, "TextAreaText"), "//*[contains(@class, 'container')]//label[text()='TextAreaText']//following-sibling::*//textarea[not (@type='hidden') ]"},
                {new TextArea("name", container), "//*[contains(@class, 'container')]//textarea[contains(@name,'name') and not (@type='hidden') ]"},
                {new TextArea().setId("IdTextArea"), "//textarea[@id='IdTextArea' and not (@type='hidden') ]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextArea textArea, String expectedXpath) {
        Assert.assertEquals(textArea.getPath(), expectedXpath);
    }
}