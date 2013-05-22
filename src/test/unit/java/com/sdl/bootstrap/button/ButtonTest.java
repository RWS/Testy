package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ButtonTest {
    public static WebLocator container = new WebLocator().setId("ID");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Button(),                  "//button[contains(@class, 'btn')]"},
                {new Button(container),         "//*[@id='ID']//button[contains(@class, 'btn')]"},
                {new Button(container, "ButtonText"), "//*[@id='ID']//button[contains(@class, 'btn') and text()='ButtonText']"},
                {new Button(container, "ButtonText").setSearchTextType("contains"), "//*[@id='ID']//button[contains(@class, 'btn') and contains(text(),'ButtonText')]"},
                {new Button(container).setId("ID"), "//*[@id='ID']//button[@id='ID' and contains(@class, 'btn')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        Assert.assertEquals(button.getPath(), expectedXpath);
    }
}