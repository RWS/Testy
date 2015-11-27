package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class WebLocatorCssTest {

    private static WebLocator cssContainer = new WebLocator().setElCssSelector(".experiment-tile");
    private static WebLocator xpathContainer = new WebLocator().setElPath("//*[@class='error-msg']");
    SoftAssert softAssert;
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
                {new WebLocator().setTag("td"), "td", "//td"},
                {new WebLocator().setCls("error-msg"), ".error-msg", "//*[@class='error-msg']"}, //TODO see if supports only one class
                {new WebLocator().setClasses("error-msg"), ".error-msg", "//*[contains(concat(' ', @class, ' '), ' error-msg ')]"},
                {new WebLocator().setClasses("error-msg", "error"), ".error-msg.error", "//*[contains(concat(' ', @class, ' '), ' error-msg ') and contains(concat(' ', @class, ' '), ' error ')]"},
                {new WebLocator().setName("newPassword"), "[name='newPassword']", "//*[@name='newPassword']"},
                {new WebLocator().setAttribute("data-toggle", "modal"), "[data-toggle='modal']", "//*[@data-toggle='modal']"},
        };
    }

    @Test(dataProvider = "testConstructorCssSelectorDataProvider")
    public void whenHaveSupportedCssAtributesICanGenerateCssSelectorsAndXpath(WebLocator el, String expectedCss, String expectedXpath) {
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
