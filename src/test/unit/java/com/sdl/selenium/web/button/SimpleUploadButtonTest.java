package com.sdl.selenium.web.button;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleUploadButtonTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SimpleUploadButton(),     "//input"},
                {new SimpleUploadButton("ID"), "//input[@id='ID']"},
                {new SimpleUploadButton().setElPath("//table[contains(@class, 'x-btn') and count(.//*[text()='Browse...']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"), "//table[contains(@class, 'x-btn') and count(.//*[text()='Browse...']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SimpleUploadButton simpleUploadButton, String expectedXpath) {
        Assert.assertEquals(simpleUploadButton.getPath(), expectedXpath);
    }

}
