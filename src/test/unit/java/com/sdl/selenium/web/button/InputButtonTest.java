package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InputButtonTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new InputButton(),                  "//input"},
                {new InputButton(container),         "//*[contains(@class, 'container')]//input"},
                {new InputButton(container, "ButtonText"), "//*[contains(@class, 'container')]//input[@value='ButtonText']"},
                {new InputButton(container).setId("ID"), "//*[contains(@class, 'container')]//input[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(InputButton inputButton, String expectedXpath) {
        Assert.assertEquals(inputButton.getPath(), expectedXpath);
    }
}