package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ButtonLinkTest {
    public static WebLocator container = new WebLocator().setId("ID");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new ButtonLink(),                  "//a[contains(@class, 'btn')]"},
                {new ButtonLink(container),         "//*[@id='ID']//a[contains(@class, 'btn')]"},
                {new ButtonLink(container, "ButtonText"), "//*[@id='ID']//a[contains(@class, 'btn') and text()='ButtonText']"},
                {new ButtonLink(container, "ButtonText").setSearchTextType("contains"), "//*[@id='ID']//a[contains(@class, 'btn') and contains(text(),'ButtonText')]"},
                {new ButtonLink(container).setId("ID"), "//*[@id='ID']//a[@id='ID' and contains(@class, 'btn')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(ButtonLink button, String expectedXpath) {
        Assert.assertEquals(button.getPath(), expectedXpath);
    }
}