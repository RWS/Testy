package com.sdl.selenium.extjs6.tab;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.link.WebLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TabTest {
    private static WebLocator container = new WebLocator("container");

    private static WebLink link = new WebLink().setClasses("x-tab-active");
    private static WebLocator child = new WebLocator(link).setClasses("fa-globe");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Tab(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab().setIconCls("fa-filter"), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ') and count(.//*[contains(concat(' ', @class, ' '), ' fa-filter ')]) > 0]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab("Title"), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ') and count(*//text()[.='Title']) > 0]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab("/Title/Text", SearchType.CONTAINS_ANY), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ') and (count(*//text()['Title']) > 0 or count(*//text()['Text']) > 0)]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab(container, "Title"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ') and count(*//text()[.='Title']) > 0]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
                {new Tab(container).setChildNodes(child), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ')]//*[contains(concat(' ', @class, ' '), ' fa-globe ')]) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' x-panel-body ')]/*[contains(concat(' ', @class, ' '), ' x-tabpanel-child ') and not(contains(@class, 'x-hidden-offsets'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Tab tab, String expectedXpath) {
        assertThat(tab.getXPath(), equalTo(expectedXpath));
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {((Tab) new Tab(container).setChildNodes(child)).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ')]//*[contains(concat(' ', @class, ' '), ' fa-globe ')]) > 0]"},
                {((Tab) new Tab("Test").setChildNodes(child)).getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and count(*//text()[.='Test']) > 0 and count(.//a[contains(concat(' ', @class, ' '), ' x-tab-active ')]//*[contains(concat(' ', @class, ' '), ' fa-globe ')]) > 0]"},
                {new Tab("Test").getTitleInactiveEl(), "//*[contains(concat(' ', @class, ' '), ' x-tab-bar ')]//a[contains(concat(' ', @class, ' '), ' x-tab ') and count(*//text()[.='Test']) > 0]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(WebLocator tab, String expectedXpath) {
        assertThat(tab.getXPath(), equalTo(expectedXpath));
    }
}
