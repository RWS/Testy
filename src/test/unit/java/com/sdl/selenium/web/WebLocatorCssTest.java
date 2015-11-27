package com.sdl.selenium.web;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class WebLocatorCssTest {

    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorCssSelectorDataProvider() {
        return new Object[][]{
                {new WebLocator(), null},
                {new WebLocator().setElCssSelector("div.error-msg"), "div.error-msg"},
                {new WebLocator().setId("id"), null},
                {new WebLocator().setTag("td"), "td"},
                {new WebLocator().setCls("error-msg"), ".error-msg"}, //TODO see if supports only one class
                {new WebLocator().setClasses("error-msg"), ".error-msg"},
                {new WebLocator().setClasses("error-msg", "error"), ".error-msg.error"},
                {new WebLocator().setName("error-msg"), ".error-msg.error"},
        };
    }

    @Test(dataProvider = "testConstructorCssSelectorDataProvider")
    public void getCssSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        assertThat(el.getCssSelector(), equalTo(expectedXpath));
    }
}
