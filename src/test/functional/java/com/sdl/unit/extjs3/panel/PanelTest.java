package com.sdl.unit.extjs3.panel;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.extjs3.panel.Panel;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PanelTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Panel(), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(new Panel()), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel("PanelTest"), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(*[contains(@class,'x-panel-header') or contains(@class, '-tl')]//*[text()='PanelTest']) > 0 and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel("PanelTest").setTitle(null), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(container, "PanelTest"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(*[contains(@class,'x-panel-header') or contains(@class, '-tl')]//*[text()='PanelTest']) > 0 and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel("testCls", container, "excludeCls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and contains(concat(' ', @class, ' '), ' testCls ') and not(contains(@class, 'excludeCls')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Panel panel, String expectedXpath) {
        assertThat(panel.getXPath(), equalTo(expectedXpath));
    }
}