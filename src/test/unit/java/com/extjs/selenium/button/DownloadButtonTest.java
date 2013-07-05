package com.extjs.selenium.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DownloadButtonTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DownloadButton(), "//*"},
                {new DownloadButton(container, "Download"), "//*[contains(@class, 'container')]//table[contains(@class, 'x-btn') and count(.//*[text()='Download']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new DownloadButton(container).setId("ID"), "//*[contains(@class, 'container')]//table[contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DownloadButton downloadButton, String expectedXpath) {
        Assert.assertEquals(downloadButton.getPath(), expectedXpath);
    }
}