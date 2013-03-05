package com.extjs.selenium.tab;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.tab.TabPanel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TabPanelTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TabPanel("Title"), "//*[contains(@class, 'x-tab-panel') and not(contains(@class, 'x-masked')) and count(*[contains(@class,'x-tab-panel-header')]//*[contains(@class, 'x-tab-strip-active')]//*[contains(text(),'Title')]) > 0]//*[contains(@class, 'x-tab-panel-body')]/*[not(contains(@class, 'x-hide-display'))]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TabPanel panel, String expectedXpath) {
        Assert.assertEquals(panel.getPath(), expectedXpath);
    }

}
