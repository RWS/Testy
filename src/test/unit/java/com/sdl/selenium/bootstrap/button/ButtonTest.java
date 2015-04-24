package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ButtonTest {
    private static WebLocator container = new WebLocator(By.id("ID"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Button(),                  "//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new Button(container),         "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new Button(container, "ButtonText"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and text()='ButtonText']"},
                {new Button(container, "ButtonText").setSearchTextType(SearchType.CONTAINS), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and contains(text(),'ButtonText')]"},
                {new Button(container).setId("ID"), "//*[@id='ID']//button[@id='ID' and contains(concat(' ', @class, ' '), ' btn ')]"},
                {new Button(container).setIconCls("IconCls"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and count(.//*[contains(@class, 'IconCls')]) > 0]"},
                {new Button(container, "ButtonText").setIconCls("IconCls"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and count(.//*[contains(@class, 'IconCls')]) > 0 and text()='ButtonText']"},
                {new Button(container, "ButtonText").setIconCls("IconCls").setVisibility(true), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and count(.//*[contains(@class, 'IconCls')]) > 0 and text()='ButtonText' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},

                {new Button(By.container(container)),         "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new Button(By.container(container), By.text("ButtonText", SearchType.EQUALS)), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and text()='ButtonText']"},
                {new Button(By.container(container), By.text("ButtonText")), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and contains(text(),'ButtonText')]"},
                {new Button(By.container(container), By.id("ID")), "//*[@id='ID']//button[@id='ID' and contains(concat(' ', @class, ' '), ' btn ')]"},
                {new Button(By.container(container)).setIconCls("IconCls"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and count(.//*[contains(@class, 'IconCls')]) > 0]"},
                {new Button(By.container(container), By.text("ButtonText", SearchType.EQUALS)).setIconCls("IconCls"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and count(.//*[contains(@class, 'IconCls')]) > 0 and text()='ButtonText']"},
                {new Button(By.container(container), By.text("ButtonText", SearchType.EQUALS), By.visibility(true)).setIconCls("IconCls"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and count(.//*[contains(@class, 'IconCls')]) > 0 and text()='ButtonText' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        Assert.assertEquals(button.getPath(), expectedXpath);
    }
}