package com.extjs.selenium.button;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
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
                {new Button("ButtonClass"), "//table[contains(@class, 'x-btn') and contains(concat(' ', @class, ' '), ' ButtonClass ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "ButtonText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[contains(@class, 'x-btn') and count(*//text()[.='ButtonText']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "ButtonText").setSearchTextType(SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[contains(@class, 'x-btn') and count(*//text()[contains(.,'ButtonText')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container).setIconCls("x-tbar-loading"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[contains(@class, 'x-btn') and count(.//*[contains(@class, 'x-tbar-loading')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container).setId("ID"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[@id='ID' and contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        Assert.assertEquals(button.getPath(), expectedXpath);
    }

    @Test
    public void resetSearchTextType(){
        Button locator = new Button().setText("text", SearchType.STARTS_WITH);
        assertEquals(locator.getPath(), "//table[contains(@class, 'x-btn') and count(*//text()[starts-with(.,'text')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]");
        locator.setSearchTextType(null);
        assertEquals(locator.getPath(), "//table[contains(@class, 'x-btn') and count(*//text()[contains(.,'text')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]");
    }
}