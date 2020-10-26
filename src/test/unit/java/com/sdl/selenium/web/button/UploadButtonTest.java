package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UploadButtonTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new UploadButton(),     "//input"},
                {new UploadButton(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input"},
                {new UploadButton(container, "ID"), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@id='ID']"},
                {new UploadButton().setElPath("//table[contains(@class, 'x-btn') and count(.//*[text()='Browse...']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"), "//table[contains(@class, 'x-btn') and count(.//*[text()='Browse...']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(UploadButton uploadButton, String expectedXpath) {
        assertThat(uploadButton.getXPath(), equalTo(expectedXpath));
    }

}
