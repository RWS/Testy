package com.sdl.unit.web.link;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.link.WebLink;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WebLinkTest {
    private static WebLocator container = new WebLocator("container");

    private static String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new WebLink(), "//a"},
                {new WebLink(container), CONTAINER_PATH + "//a"},
                {new WebLink(container, "text"), CONTAINER_PATH + "//a[contains(text(),'text')]"},
                {new WebLink(container, "text", SearchType.EQUALS), CONTAINER_PATH + "//a[text()='text']"},
                {new WebLink(container, "text", SearchType.DEEP_CHILD_NODE_OR_SELF), CONTAINER_PATH + "//a[(contains(.,'text') or count(*//text()[contains(.,'text')]) > 0)]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLink webLink, String expectedXpath) {
        assertThat(webLink.getXPath(), equalTo(expectedXpath));
    }
}