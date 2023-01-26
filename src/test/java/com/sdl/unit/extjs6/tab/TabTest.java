package com.sdl.unit.extjs6.tab;

import com.sdl.selenium.extjs6.tab.Tab;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TabTest {
    private static final WebLocator container = new WebLocator("container");
    private static final WebLocator child = new WebLocator().setClasses("fa-globe");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Tab(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab().setIconCls("fa-filter"), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ') and count(.//*[contains(concat(' ', @class, ' '), ' fa-filter ')]) > 0]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab("Title"), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ') and count(*//text()[.='Title']) > 0]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab("/Title/Text", SearchType.CONTAINS_ANY), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ') and (count(*//text()[.='Title']) > 0 or count(*//text()[.='Text']) > 0)]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab(container, "Title"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ') and count(*//text()[.='Title']) > 0]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab(container).setChildNodes(child), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//*[contains(concat(' ', @class, ' '), ' fa-globe ')]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Tab tab, String expectedXpath) {
        assertThat(tab.getXPath(), equalTo(expectedXpath));
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {((Tab) new Tab(container).setChildNodes(child)).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and count(.//*[contains(concat(' ', @class, ' '), ' fa-globe ')]) > 0]"},
                {((Tab) new Tab("Test").setChildNodes(child)).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and count(*//text()[.='Test']) > 0 and count(.//*[contains(concat(' ', @class, ' '), ' fa-globe ')]) > 0]"},
                {new Tab(container).setIconCls("fa-close").getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and count(.//*[contains(concat(' ', @class, ' '), ' fa-close ')]) > 0]"},
                {new Tab("Test").getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and count(*//text()[.='Test']) > 0]"},
                {new Tab("|Test|New", SearchType.CONTAINS_ANY).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and (count(*//text()[.='Test']) > 0 or count(*//text()[.='New']) > 0)]"},
                {new Tab("|Test|New", SearchType.DEEP_CHILD_NODE_OR_SELF, SearchType.CONTAINS_ANY).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and ((.='Test' or count(*//text()[.='Test']) > 0) or (.='New' or count(*//text()[.='New']) > 0))]"},
                {new Tab("|Test|New", SearchType.CHILD_NODE, SearchType.CONTAINS_ANY).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and (contains(.,'Test') or contains(.,'New'))]"},
                {new Tab("|Test|New", SearchType.CONTAINS_ALL).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and count(*//text()[.='Test']) > 0 and count(*//text()[.='New']) > 0]"},
                {new Tab("|Test|New", SearchType.DEEP_CHILD_NODE_OR_SELF, SearchType.CONTAINS_ALL).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and (.='Test' or count(*//text()[.='Test']) > 0) and (.='New' or count(*//text()[.='New']) > 0)]"},
                {new Tab("|Test|New", SearchType.CHILD_NODE, SearchType.CONTAINS_ALL).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and contains(.,'Test') and contains(.,'New')]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(WebLocator tab, String expectedXpath) {
        assertThat(tab.getXPath(), equalTo(expectedXpath));
    }
}
