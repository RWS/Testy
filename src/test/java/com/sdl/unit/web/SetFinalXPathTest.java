package com.sdl.unit.web;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SetFinalXPathTest {
    private static final WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new WebLocator().setFinalXPath("//final"), "//*//final"},
                {new WebLocator().setAttribute("atr", "2").setFinalXPath("//tab[1]"), "//*[@atr='2']//tab[1]"},
                {new WebLocator().setTag("div").setFinalXPath("//final"), "//div//final"},
                {new WebLocator().setAttribute("atr", "2").setFinalXPath("//tab[1]"), "//*[@atr='2']//tab[1]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        assertThat(el.getXPath(), equalTo(expectedXpath));
    }
}
