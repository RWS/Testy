package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DownloadButtonTest {
    public static WebLocator container = new WebLocator(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DownloadButton(), "//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new DownloadButton(container, "Download"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[.='Download']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new DownloadButton(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new DownloadButton(container).setId("ID"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[@id='ID' and contains(concat(' ', @class, ' '), ' x-btn ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new DownloadButton(container).setElPath("//*[text()='Download']"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[text()='Download']"},
                {new DownloadButton().setElPath("//*[text()='Download']"), "//*[text()='Download']"},

                {new DownloadButton(By.container(container), By.text("Download")), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(*//text()[.='Download']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new DownloadButton(By.container(container)), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[contains(concat(' ', @class, ' '), ' x-btn ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new DownloadButton(By.container(container), By.id("ID")), "//*[contains(concat(' ', @class, ' '), ' container ')]//table[@id='ID' and contains(concat(' ', @class, ' '), ' x-btn ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new DownloadButton(By.container(container), By.xpath("//*[text()='Download']")), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[text()='Download']"},
                {new DownloadButton(By.xpath("//*[text()='Download']")), "//*[text()='Download']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DownloadButton downloadButton, String expectedXpath) {
        Assert.assertEquals(downloadButton.getPath(), expectedXpath);
    }
}