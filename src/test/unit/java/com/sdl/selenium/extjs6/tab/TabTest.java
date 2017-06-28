package com.sdl.selenium.extjs6.tab;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TabTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Tab(),                   "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab("Title"),            "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ')]//*[count(*//text()[.='Title']) > 0]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab("/Title/Text", SearchType.CONTAINS_ANY), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ')]//*[(contains(text(),'Title') or contains(text(),'Text'))]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab(container),          "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab(container, "Title"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ')]//*[count(*//text()[.='Title']) > 0]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Tab tab, String expectedXpath) {
        assertThat(tab.getXPath(), equalTo(expectedXpath));
    }
}
