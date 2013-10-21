package com.extjs.selenium.button;

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
                {new DownloadLink(container, "Download"), "//*[contains(@class, 'container')]//a[count(*//text()[.='Download']) > 0]"},
                {new DownloadLink(container), "//*[contains(@class, 'container')]//a"},
                {new DownloadLink(container).setId("ID"), "//*[contains(@class, 'container')]//a[@id='ID']"},
                {new DownloadLink(container).setElPath("//*[text()='Download']"), "//*[contains(@class, 'container')]//*[text()='Download']"},
                {new DownloadLink().setElPath("//*[text()='Download']"), "//*[text()='Download']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DownloadLink downloadLink, String expectedXpath) {
        Assert.assertEquals(downloadLink.getPath(), expectedXpath);
    }
}