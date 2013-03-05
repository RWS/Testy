package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DisplayFieldTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DisplayField(),                    "//*[contains(@class, 'x-form-display-field') and not (@type='hidden') ]"},
                {new DisplayField(container, "Label"),  "//*[contains(@class, 'container')]//label[text()='Label']//following-sibling::*//*[contains(@class, 'x-form-display-field') and not (@type='hidden') ]"},
                {new DisplayField("name", container),   "//*[contains(@class, 'container')]//*[contains(@class, 'x-form-display-field') and contains(@name,'name') and not (@type='hidden') ]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DisplayField displayField, String expectedXpath) {
        Assert.assertEquals(displayField.getPath(), expectedXpath);
    }
}
