package com.sdl.unit.extreact.menu;

import com.sdl.selenium.extreact.menu.Menu;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MenuTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Menu(),       "//*[contains(concat(' ', @class, ' '), ' x-menu ') and not(contains(@class, 'x-hidden-display'))]"},
                {new Menu("Title"),"//*[contains(concat(' ', @class, ' '), ' x-menu ') and not(contains(@class, 'x-hidden-display')) and count(.//*[contains(concat(' ', @class, ' '), ' x-menuitem ') and (contains(.,'Title') or count(*//text()[contains(.,'Title')]) > 0)]) > 0]"},
                {new Menu("Title", SearchType.CASE_INSENSITIVE),"//*[contains(concat(' ', @class, ' '), ' x-menu ') and not(contains(@class, 'x-hidden-display')) and count(.//*[contains(concat(' ', @class, ' '), ' x-menuitem ') and (contains(translate(.,'TITLE','title'),'title') or count(*//text()[contains(translate(.,'TITLE','title'),'title')]) > 0)]) > 0]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Menu menu, String expectedXpath) {
        assertThat(menu.getXPath(), equalTo(expectedXpath));
    }
}
