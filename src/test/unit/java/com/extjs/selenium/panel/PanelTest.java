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
                {new Panel(), "//*[contains(@class, 'x-panel') and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel("PanelTest"), "//*[contains(@class, 'x-panel') and (count(*[contains(@class,'x-panel-header')]//*[text()='PanelTest']) > 0 or count(*[contains(@class, '-tl')]//*[contains(@class,'x-panel-header')]//*[text()='PanelTest']) > 0) and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(container), "//*[contains(@class, 'container')]//*[contains(@class, 'x-panel') and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(container, "PanelTest"), "//*[contains(@class, 'container')]//*[contains(@class, 'x-panel') and (count(*[contains(@class,'x-panel-header')]//*[text()='PanelTest']) > 0 or count(*[contains(@class, '-tl')]//*[contains(@class,'x-panel-header')]//*[text()='PanelTest']) > 0) and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
//                {new Panel("testCls", container, "excludeCls"), "//*[contains(@class, 'container')]//*[contains(@class, 'x-panel') and contains(@class, 'testCls') and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Panel panel, String expectedXpath) {
        Assert.assertEquals(panel.getPath(), expectedXpath);
    }

}
