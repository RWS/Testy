package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WebLocatorCssTest {

    private static WebLocator cssContainer = new WebLocator().setElCssSelector(".experiment-tile");
    private static WebLocator xpathContainer = new WebLocator().setElxPath("//*[@class='error-msg']");

    private SoftAssert softAssert;
    private boolean generateCssSelector = false;

    @BeforeMethod
    public void start(){
        softAssert = new SoftAssert();
        generateCssSelector = WebLocatorConfig.isGenerateCssSelector();
        WebLocatorConfig.setGenerateCssSelector(true);
    }

    @AfterMethod
    public void stop(){
        WebLocatorConfig.setGenerateCssSelector(generateCssSelector);
    }


    @DataProvider
    public static Object[][] testConstructorCssSelectorDataProvider() {
        return new Object[][]{
                {new WebLocator(), "*", "//*"},
                {new WebLocator().setElCssSelector("div.error-msg"), "div.error-msg", "//*"},
                {new WebLocator().setId("email"), "#email", "//*[@id='email']"},
                {new WebLocator().setId("email").setClasses("input-block-level"), "#email.input-block-level", "//*[@id='email' and contains(concat(' ', @class, ' '), ' input-block-level ')]"},
                {new WebLocator().setTag("td"), "td", "//td"},
                {new WebLocator().setCls("error-msg"), "[class=error-msg]", "//*[@class='error-msg']"},
                {new WebLocator().setClasses("error-msg"), ".error-msg", "//*[contains(concat(' ', @class, ' '), ' error-msg ')]"},
                {new WebLocator().setTag("button").setExcludeClasses("error-msg"), "button:not(.error-msg)", "//button[not(contains(@class, 'error-msg'))]"},
                {new WebLocator().setExcludeClasses("error-msg", "msg"), ":not(.error-msg):not(.msg)", "//*[not(contains(@class, 'error-msg')) and not(contains(@class, 'msg'))]"},
                {new WebLocator().setClasses("error-msg", "error"), ".error-msg.error", "//*[contains(concat(' ', @class, ' '), ' error-msg ') and contains(concat(' ', @class, ' '), ' error ')]"},
                {new WebLocator().setName("newPassword"), "[name='newPassword']", "//*[@name='newPassword']"},
                {new WebLocator().setAttribute("data-toggle", "modal"), "[data-toggle='modal']", "//*[@data-toggle='modal']"},
                {new WebLocator().setTag("li").setPosition(1), "li:nth-child(1)", "//li[position() = 1]"},
                {new WebLocator().setTag("li").setPosition(Position.LAST), "li:last-child", "//li[position() = last()]"},
        };
    }

    @Test(dataProvider = "testConstructorCssSelectorDataProvider")
    public void whenHaveSupportedCssAttributesICanGenerateCssSelectorsAndXpath(WebLocator el, String expectedCss, String expectedXpath) {
        softAssert.assertEquals(el.getCssSelector(), expectedCss);
        softAssert.assertEquals(el.getXPath(), expectedXpath);

        el.setContainer(cssContainer);
        softAssert.assertEquals(el.getCssSelector(), cssContainer.getCssSelector() + " " + expectedCss);
        softAssert.assertEquals(el.getXPath(), cssContainer.getXPath() + expectedXpath);
        softAssert.assertAll();
    }

    @Test(dataProvider = "testConstructorCssSelectorDataProvider")
    public void whenParentHasXPathThenCssSelectorIsNull(WebLocator el, String expectedCss, String expectedXpath) {
        el.setContainer(xpathContainer);
        softAssert.assertEquals(el.getCssSelector(), null);
        softAssert.assertEquals(el.getXPath(), xpathContainer.getXPath() + expectedXpath);
        softAssert.assertAll();
    }
}
