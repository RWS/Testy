package com.sdl.selenium.web.link;

import com.sdl.selenium.web.By;
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
                {new WebLink(container, "text"), CONTAINER_PATH + "//a[contains(text(),'text')]"},
                {new WebLink(By.container(container)), CONTAINER_PATH + "//a"},
                {new WebLink(container, By.text("text")), CONTAINER_PATH + "//a[contains(text(),'text')]"},
                {new WebLink(container, By.text("text"), By.title("title")), CONTAINER_PATH + "//a[@title='title' and contains(text(),'text')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLink webLink, String expectedXpath) {
        Assert.assertEquals(webLink.getPathBuilder().getPath(), expectedXpath);
    }
}