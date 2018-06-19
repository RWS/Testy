package com.sdl.selenium.extjs3.tab;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TabPanelTest {
    private static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TabPanel("Title"), "//*[contains(concat(' ', @class, ' '), ' x-tab-panel ') and not(contains(@class, 'x-masked')) and count(*[contains(@class,'x-tab-panel-header')]//*[contains(@class, 'x-tab-strip-active')]//*[text()='Title']) > 0]/*/*[contains(@class, 'x-tab-panel-body')]/*[not(contains(@class, 'x-hide-display'))]"},
                {new TabPanel(container, "Title"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-panel ') and not(contains(@class, 'x-masked')) and count(*[contains(@class,'x-tab-panel-header')]//*[contains(@class, 'x-tab-strip-active')]//*[text()='Title']) > 0]/*/*[contains(@class, 'x-tab-panel-body')]/*[not(contains(@class, 'x-hide-display'))]"},
                {new TabPanel(container, "|Title|Test").setSearchTextType(SearchType.CONTAINS_ANY), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-panel ') and not(contains(@class, 'x-masked')) and count(*[contains(@class,'x-tab-panel-header')]//*[contains(@class, 'x-tab-strip-active')]//*[(contains(.,'Title') or contains(.,'Test'))]) > 0]/*/*[contains(@class, 'x-tab-panel-body')]/*[not(contains(@class, 'x-hide-display'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TabPanel panel, String expectedXpath) {
        Assert.assertEquals(panel.getXPath(), expectedXpath);
    }
}