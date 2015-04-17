package com.sdl.selenium.web.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MultipleSelectTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new MultipleSelect(),             "//select"},
                {new MultipleSelect(container),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select"},
                {new MultipleSelect(container).setId("id"),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select[@id='id']"},
                {new MultipleSelect(container, "Label"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Label']//following-sibling::*//select"},

                {new MultipleSelect(By.container(container)),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select"},
                {new MultipleSelect(By.container(container), By.id("id")),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select[@id='id']"},
                {new MultipleSelect(By.container(container), By.label("Label")), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Label']//following-sibling::*//select"},
                {new MultipleSelect(By.label("Label")), "//label[text()='Label']//following-sibling::*//select"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(MultipleSelect list, String expectedXpath) {
        Assert.assertEquals(list.getPath(), expectedXpath);
    }
}
