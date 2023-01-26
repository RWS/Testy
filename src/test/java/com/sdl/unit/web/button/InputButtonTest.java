package com.sdl.unit.web.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.InputButton;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class InputButtonTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new InputButton(), "//input"},
                {new InputButton(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input"},
                {new InputButton(container).setText("ButtonText", SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@value='ButtonText']"},
                {new InputButton(container, "ButtonText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@value='ButtonText']"},
                {new InputButton(container).setId("ID"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@id='ID']"},
                {new InputButton().setText("Create Account").setVisibility(true), "//input[@value='Create Account' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new InputButton(container, "t").setAttribute("accept", ".exe"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@accept='.exe' and @value='t']"},
                {new InputButton(container, "t").setAttribute("accept", "exe", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(@accept,'exe') and @value='t']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(InputButton inputButton, String expectedXpath) {
        assertThat(inputButton.getXPath(), equalTo(expectedXpath));
    }
}