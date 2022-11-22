package com.sdl.unit.web;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SetElPathSuffixTest {
    private static final WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new WebLocator().setElPathSuffix("suffix", "//suffix"), "//*[//suffix]"},
                {new WebLocator().setAttribute("suffix", "2").setElPathSuffix("fin", "@tab='1'"), "//*[@suffix='2' and @tab='1']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        assertThat(el.getXPath(), equalTo(expectedXpath));
    }
}
