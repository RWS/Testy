package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleMultipleSelectTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SimpleMultipleSelect(),             "//select"},
                {new SimpleMultipleSelect(container),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select"},
                {new SimpleMultipleSelect(container).setId("id"),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select[@id='id']"},
                {new SimpleMultipleSelect(container, "Label"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Label']//following-sibling::*//select"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SimpleMultipleSelect list, String expectedXpath) {
        Assert.assertEquals(list.getPath(), expectedXpath);
    }
}
