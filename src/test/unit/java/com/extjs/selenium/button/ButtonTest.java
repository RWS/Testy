package com.extjs.selenium.button;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ButtonTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Button(), "//table[contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button("ButtonClass"), "//table[contains(@class, 'x-btn') and contains(@class, 'ButtonClass') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container), "//*[contains(@class, 'container')]//table[contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "ButtonText"), "//*[contains(@class, 'container')]//table[contains(@class, 'x-btn') and count(.//*[text()='ButtonText']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "ButtonText").setSearchTextType("contains"), "//*[contains(@class, 'container')]//table[contains(@class, 'x-btn') and count(.//*[contains(text(),'ButtonText')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container).setIconCls("x-tbar-loading"), "//*[contains(@class, 'container')]//table[contains(@class, 'x-btn') and count(.//*[contains(@class, 'x-tbar-loading')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container).setId("ID"), "//*[contains(@class, 'container')]//table[@id='ID' and contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        Assert.assertEquals(button.getPath(), expectedXpath);
    }

    @Test
    public void createInstancesWithBuilders() {
        Button locatorBuilder1 = new Button().setId("ID1").setCls("CLS");

        assertEquals(locatorBuilder1.getTag(), "table");
        assertEquals(locatorBuilder1.getId(), "ID1");
        assertEquals(locatorBuilder1.getCls(), "CLS");
        assertEquals(locatorBuilder1.getClassName(), "Button");
    }
}