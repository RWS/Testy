package com.extjs.selenium.panel;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PanelTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Panel(), "//*[contains(@class, 'x-panel') and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc') or contains(@class, 'x-panel-tl') or contains(@class, 'x-panel-tr')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel("PanelTest"), "//*[contains(@class, 'x-panel') and count(div[1]//*[text()='PanelTest']) > 0 and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc') or contains(@class, 'x-panel-tl') or contains(@class, 'x-panel-tr')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'x-panel') and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc') or contains(@class, 'x-panel-tl') or contains(@class, 'x-panel-tr')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(container, "PanelTest"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'x-panel') and count(div[1]//*[text()='PanelTest']) > 0 and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc') or contains(@class, 'x-panel-tl') or contains(@class, 'x-panel-tr')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel("testCls", container, "excludeCls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'x-panel') and contains(concat(' ', @class, ' '), ' testCls ') and not(contains(@class, 'excludeCls')) and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc') or contains(@class, 'x-panel-tl') or contains(@class, 'x-panel-tr')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Panel panel, String expectedXpath) {
        Assert.assertEquals(panel.getPath(), expectedXpath);
    }

}
