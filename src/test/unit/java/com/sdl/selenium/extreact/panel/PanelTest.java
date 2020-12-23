package com.sdl.selenium.extreact.panel;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PanelTest {
    private static final WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Panel(), "//*[contains(concat(' ', @class, ' '), ' x-panel ')]"},
                {new Panel(new Panel()), "//*[contains(concat(' ', @class, ' '), ' x-panel ')]//*[contains(concat(' ', @class, ' '), ' x-panel ')]"},
                {new Panel(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ')]"},
                {new Panel(container, "PanelTest"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(.//*[contains(concat(' ', @class, ' '), ' x-paneltitle ')]//*[contains(concat(' ', @class, ' '), ' x-text-el ') and text()='PanelTest']) > 0]"},
                {new Panel(null, "TitlePanel"), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(.//*[contains(concat(' ', @class, ' '), ' x-paneltitle ')]//*[contains(concat(' ', @class, ' '), ' x-text-el ') and text()='TitlePanel']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Panel panel, String expectedXpath) {
        assertThat(panel.getXPath(), equalTo(expectedXpath));
    }
}