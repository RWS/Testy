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
                {new InputButton(container),         "//*[contains(concat(' ', @class, ' '), ' container ')]//input"},
                {new InputButton(container).setText("ButtonText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@value='ButtonText']"},
                {new InputButton(container, "ButtonText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@value='ButtonText']"},
                {new InputButton(container).setId("ID"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@id='ID']"},
                {new InputButton().setText("Create Account").setVisibility(true), "//input[@value='Create Account' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(InputButton inputButton, String expectedXpath) {
        Assert.assertEquals(inputButton.getPath(), expectedXpath);
    }
}