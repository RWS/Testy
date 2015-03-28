package com.sdl.selenium.extjs3.list;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ListTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new List(),          "//*[contains(concat(' ', @class, ' '), ' ux-form-multiselect ')]"},
                {new List(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' ux-form-multiselect ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(List list, String expectedXpath) {
        Assert.assertEquals(list.getPath(), expectedXpath);
    }
}
