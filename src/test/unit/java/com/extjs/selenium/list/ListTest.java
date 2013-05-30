package com.extjs.selenium.list;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ListTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new List(),          "//*[contains(@class, 'ux-form-multiselect')]"},
                {new List(container), "//*[contains(@class, 'container')]//*[contains(@class, 'ux-form-multiselect')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(List list, String expectedXpath) {
        Assert.assertEquals(list.getPath(), expectedXpath);
    }
}
