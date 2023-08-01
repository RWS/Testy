package com.sdl.unit.web.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.Button;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ButtonTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Button(), "//button"},
                {new Button(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//button"},
                {new Button(container).setText("ButtonText", SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[text()='ButtonText']"},
                {new Button(container, "ButtonText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[text()='ButtonText']"},
                {new Button(container).setId("ID"), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[@id='ID']"},
                {new Button().setText("Create Account").setVisibility(true), "//button[contains(text(),'Create Account') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Button(container, "t").setAttribute("accept", ".exe"), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[@accept='.exe' and text()='t']"},
                {new Button(container, "t").setAttribute("accept", "exe", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[contains(@accept,'exe') and text()='t']"},
                {new Button(container).setIconCls("IconCls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[count(.//*[contains(concat(' ', @class, ' '), ' IconCls ')]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        assertThat(button.getXPath(), equalTo(expectedXpath));
    }
}