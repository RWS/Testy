package com.sdl.unit.bootstrap.button;

import com.sdl.selenium.bootstrap.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ButtonTest {
    public static WebLocator container = new WebLocator().setId("ID");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Button(),                  "//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new Button(container),         "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new Button(container, "ButtonText"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and text()='ButtonText']"},
                {new Button(container, "ButtonText").setSearchTextType(SearchType.CONTAINS), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and contains(text(),'ButtonText')]"},
                {new Button(container).setId("ID"), "//*[@id='ID']//button[@id='ID' and contains(concat(' ', @class, ' '), ' btn ')]"},
                {new Button(container).setIconCls("IconCls"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and count(.//*[contains(@class, 'IconCls')]) > 0]"},
                {new Button(container, "ButtonText").setIconCls("IconCls"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and text()='ButtonText' and count(.//*[contains(@class, 'IconCls')]) > 0]"},
                {new Button(container, "ButtonText").setIconCls("IconCls").setVisibility(true), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and text()='ButtonText' and count(.//*[contains(@class, 'IconCls')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        assertThat(button.getXPath(), equalTo(expectedXpath));
    }
}