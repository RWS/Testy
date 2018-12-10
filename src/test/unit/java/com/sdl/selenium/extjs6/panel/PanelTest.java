package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PanelTest {
    private static WebLocator container = new WebLocator("container");
    private static WebLocator title = new WebLocator().setClasses("x-title").setText("Title");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Panel(), "//*[contains(concat(' ', @class, ' '), ' x-panel ')]"},
                {new Panel(new Panel()), "//*[contains(concat(' ', @class, ' '), ' x-panel ')]//*[contains(concat(' ', @class, ' '), ' x-panel ')]"},
                {new Panel(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ')]"},
                {new Panel(container, "PanelTest"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(./*[contains(concat(' ', @class, ' '), ' x-panel-header ')]//*[contains(concat(' ', @class, ' '), ' x-title ')]//*[text()='PanelTest']) > 0]"},
                {new Panel().setTemplateTitle(title), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(.//*[contains(concat(' ', @class, ' '), ' x-title ') and contains(text(),'Title')]) > 0]"},
                {new Panel().setTemplateTitle(title).setSearchTitleType(SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(.//*[contains(concat(' ', @class, ' '), ' x-title ') and contains(text(),'Title')]) > 0]"},
                {new Panel(null, "TitlePanel").setTemplateTitle(title).setSearchTitleType(SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(.//*[contains(concat(' ', @class, ' '), ' x-title ') and text()='TitlePanel']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Panel panel, String expectedXpath) {
        assertThat(panel.getXPath(), equalTo(expectedXpath));
    }
}