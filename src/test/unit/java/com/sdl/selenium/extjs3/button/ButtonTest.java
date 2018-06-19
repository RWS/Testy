package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ButtonTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonTest.class);

    public static ExtJsComponent container = new ExtJsComponent("container");
    public static String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Button(), "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button().setClasses("ButtonClass"), "//table[contains(concat(' ', @class, ' '), ' x-btn ') and contains(concat(' ', @class, ' '), ' ButtonClass ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container), CONTAINER_PATH + "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "ButtonText"), CONTAINER_PATH + "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[.='ButtonText']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "ButtonText").setSearchTextType(SearchType.CONTAINS), CONTAINER_PATH + "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[contains(.,'ButtonText')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "Text").setIconCls("x-tbar-loading"), CONTAINER_PATH + "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[.='Text']) > 0 and count(.//*[contains(@class, 'x-tbar-loading')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container).setId("ID"), CONTAINER_PATH + "//table[@id='ID' and contains(concat(' ', @class, ' '), ' x-btn ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},

                {new Button(container, "Don't Accept"), CONTAINER_PATH + "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[.=\"Don't Accept\"]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "Don'\"t Accept"), CONTAINER_PATH + "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[.=concat(\"Don'\", '\"', \"t Accept\")]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "It was \"good\" ok!"), CONTAINER_PATH + "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[.='It was \"good\" ok!']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new Button(container, "Don't do it \"now\" ok!"), CONTAINER_PATH + "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[.=concat(\"Don't do it \", '\"', \"now\", '\"', \" ok!\")]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},

        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        assertThat(button.getXPath(), equalTo(expectedXpath));
    }

    @Test
    public void resetSearchTextType() {
        Button locator = new Button().setText("text", SearchType.STARTS_WITH);
        assertThat(locator.getXPath(), equalTo("//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[starts-with(.,'text')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"));
        locator.setSearchTextType(null);
        assertThat(locator.getXPath(), equalTo("//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[contains(.,'text')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"));
    }
}