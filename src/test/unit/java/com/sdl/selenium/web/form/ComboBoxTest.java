package com.sdl.selenium.web.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ComboBoxTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new ComboBox(),             "//select"},
                {new ComboBox(container),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select"},
                {new ComboBox().setId("ID"), "//select[@id='ID']"},

                {new ComboBox(By.container(container)),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select"},
                {new ComboBox(By.id("ID")), "//select[@id='ID']"},
                {new ComboBox(By.id("ID"), By.tag("tag")), "//tag[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(ComboBox combo, String expectedXpath) {
        Assert.assertEquals(combo.getPathBuilder().getPath(), expectedXpath);
    }
}
