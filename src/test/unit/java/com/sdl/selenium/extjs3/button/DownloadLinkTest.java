package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DownloadLinkTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DownloadLink(), "//a"},
                {new DownloadLink(container, "Download"), "//*[contains(concat(' ', @class, ' '), ' container ')]//a[text()='Download']"},
                {new DownloadLink(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//a"},
                {new DownloadLink(container).setId("ID"), "//*[contains(concat(' ', @class, ' '), ' container ')]//a[@id='ID']"},
                {new DownloadLink(container).setElxPath("//*[text()='Download']"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[text()='Download']"},
                {new DownloadLink().setElxPath("//*[text()='Download']"), "//*[text()='Download']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DownloadLink downloadLink, String expectedXpath) {
        Assert.assertEquals(downloadLink.getXPath(), expectedXpath);
    }
}