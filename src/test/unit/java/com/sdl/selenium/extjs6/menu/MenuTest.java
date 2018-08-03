package com.sdl.selenium.extjs6.menu;

import com.sdl.selenium.web.SearchType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MenuTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Menu(),       "//*[contains(concat(' ', @class, ' '), ' x-menu ') and @aria-hidden='false' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Menu("Title"),"//*[contains(concat(' ', @class, ' '), ' x-menu ') and count(.//*[contains(concat(' ', @class, ' '), ' x-menu-item-plain ') and (contains(.,'Title') or count(*//text()[contains(.,'Title')]) > 0)]) > 0 and @aria-hidden='false' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Menu("Title", SearchType.CASE_INSENSITIVE),"//*[contains(concat(' ', @class, ' '), ' x-menu ') and count(.//*[contains(concat(' ', @class, ' '), ' x-menu-item-plain ') and contains(translate(text(),'TITLE','title'),'title')]) > 0 and @aria-hidden='false' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Menu menu, String expectedXpath) {
        assertThat(menu.getXPath(), equalTo(expectedXpath));
    }
}
