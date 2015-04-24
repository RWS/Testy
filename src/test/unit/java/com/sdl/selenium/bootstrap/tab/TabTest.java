package com.sdl.selenium.bootstrap.tab;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TabTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Tab("Tab"),                      "//ul[contains(concat(' ', @class, ' '), ' nav nav-tabs ') and count(.//li[@class='active']//a[text()='Tab']) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' tab-content ')]//*[contains(concat(' ', @class, ' '), ' tab-pane ') and contains(concat(' ', @class, ' '), ' active ')]"},
                {new Tab(container, "Tab"),          "//*[contains(concat(' ', @class, ' '), ' container ')]//ul[contains(concat(' ', @class, ' '), ' nav nav-tabs ') and count(.//li[@class='active']//a[text()='Tab']) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' tab-content ')]//*[contains(concat(' ', @class, ' '), ' tab-pane ') and contains(concat(' ', @class, ' '), ' active ')]"},
                {new Tab(container, "Tab").setId("IdTab"),   "//*[contains(concat(' ', @class, ' '), ' container ')]//ul[@id='IdTab' and contains(concat(' ', @class, ' '), ' nav nav-tabs ') and count(.//li[@class='active']//a[text()='Tab']) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' tab-content ')]//*[contains(concat(' ', @class, ' '), ' tab-pane ') and contains(concat(' ', @class, ' '), ' active ')]"},
                {new Tab(container, "Tab").setClasses("Class"),   "//*[contains(concat(' ', @class, ' '), ' container ')]//ul[contains(concat(' ', @class, ' '), ' nav nav-tabs ') and contains(concat(' ', @class, ' '), ' Class ') and count(.//li[@class='active']//a[text()='Tab']) > 0]//following-sibling::*[contains(concat(' ', @class, ' '), ' tab-content ')]//*[contains(concat(' ', @class, ' '), ' tab-pane ') and contains(concat(' ', @class, ' '), ' active ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Tab tab, String expectedXpath) {
        Assert.assertEquals(tab.getPath(), expectedXpath);
    }
}
