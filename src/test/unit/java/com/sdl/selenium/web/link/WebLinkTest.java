package com.sdl.selenium.web.link;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebLinkTest {
    private static WebLocator container = new WebLocator("container");

    private static String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new WebLink(), "//a"},
                {new WebLink(container), CONTAINER_PATH + "//a"},
                {new WebLink(container, "text"), CONTAINER_PATH + "//a[contains(.,'text')]"},
                {new WebLink(container, "text", SearchType.EQUALS), CONTAINER_PATH + "//a[text()='text']"},
                {new WebLink(container, "text", SearchType.DEEP_CHILD_NODE_OR_SELF), CONTAINER_PATH + "//a[(contains(.,'text') or count(*//text()[contains(.,'text')]) > 0)]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLink webLink, String expectedXpath) {
        Assert.assertEquals(webLink.getXPath(), expectedXpath);
    }
}