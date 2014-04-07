package com.sdl.selenium.web;

import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WebLocator1Test extends TestBase {
    private static final Logger logger = Logger.getLogger(WebLocator1Test.class);

    WebLocator webLocatorId = new WebLocator().setId("webLocatorId");
    WebLocator webLocatorCls = new WebLocator().setClasses("webLocatorCls");
    WebLocator webLocatorNotAttribute = new WebLocator().setClasses("notExist");

    WebLocator webLocatorWithMoreEnter = new WebLocator().setClasses("more-elements-inside").setText("more enter inside div");
    WebLocator webLocatorWithMoreEnterMoreElements = new WebLocator().setClasses("more-elements-inside").setText("more enter inside div", SearchType.DEEP_CHILD_NODE);
    WebLocator webLocatorNoWithMoreEnterMoreElements = new WebLocator().setClasses("more-elements-inside no-enter").setText("more enter inside div", SearchType.DEEP_CHILD_NODE);
    WebLocator webLocatorNoWithMoreEnter = new WebLocator().setClasses("more-elements-inside no-enter").setText("more enter inside div");
    WebLocator webLocatorWithMoreText = new WebLocator().setElPath("//*[contains(@class, 'element7') and concat(text()[1], ./*/text(), text()[2], ./*/text()[contains(.,'care')], text()[3])='Some important text care trebuie']");
    WebLocator webLocatorComplex = new WebLocator().setClasses("element11").setText("Some more important text that is very important . end", SearchType.HTML_NODE);
    WebLocator webLocatorLogger = new WebLocator().setId("logger");

    @DataProvider
    public static Object[][] testConstructorPathDataProviderText() {
        String text = "WebLocator text for search type";
        String cls = "searchTextType";
        return new Object[][]{
                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(text(),'" + text + "')]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and text()='" + text + "']"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(text(),'" + text + "')]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[contains(.,'" + text + "')]) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[.='" + text + "']) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[starts-with(.,'" + text + "')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(.,'" + text + "')]) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[.='" + text + "']) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(.,'" + text + "')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(normalize-space(text()),'" + text + "')]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and normalize-space(text())='" + text + "']"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.TRIM), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(normalize-space(text()),'" + text + "')]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[contains(normalize-space(.),'" + text + "')]) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[normalize-space(.)='" + text + "']) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.TRIM, SearchType.CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(text()[starts-with(normalize-space(.),'" + text + "')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[contains(normalize-space(.),'" + text + "')]) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[normalize-space(.)='" + text + "']) > 0]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.STARTS_WITH, SearchType.TRIM, SearchType.DEEP_CHILD_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and count(*//text()[starts-with(normalize-space(.),'" + text + "')]) > 0]"},

                {new WebLocator().setClasses(cls).setText(text, SearchType.HTML_NODE), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and (normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))='WebLocator text for search type' or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))='WebLocator text for search type')]"},
        };
    }

    @BeforeClass
    public void startTests() {
        driver.get(InputData.WEB_LOCATOR_URL);
    }

    @Test
    public void identifyById() {
        assertEquals(webLocatorId.getAttributeId(), "webLocatorId");
        assertEquals(webLocatorId.getAttributeClass(), "");
    }

    @Test
    public void identifyByClass() {
        assertEquals(webLocatorCls.getAttributeClass(), "webLocatorCls");
        assertEquals(webLocatorCls.getAttributeId(), "");
    }

    @Test
    public void attributeForNotFoundElement() {
        assertEquals(webLocatorNotAttribute.getAttributeClass(), null);
    }

    @Test
    public void webDriverConfig() {
        WebLocator l = new WebLocator().setClasses("x-tool-maximize");
        logger.debug(l.getPath());
        logger.debug("//*[contains(@class,'x-tool-maximize')]");

//        final String browser = InputData.BROWSER;
        if (Browser.FIREFOX.name().equalsIgnoreCase("*firefox")) {
            assertTrue(WebDriverConfig.isFireFox());
        } else if (Browser.CHROME.name().equalsIgnoreCase("*chrome")) {
            assertTrue(WebDriverConfig.isChrome());
        } else if (Browser.IEXPLORE.name().equalsIgnoreCase("*iexplore")) {
            assertTrue(WebDriverConfig.isIE());
        }
    }

    @Test
    public void getHtmlTextFromDiv() {
        assertEquals(webLocatorWithMoreEnter.getAttributeClass(), "more-elements-inside no-enter element4");
        assertEquals(webLocatorWithMoreEnterMoreElements.getAttributeClass(), "more-elements-inside with-enter element1");
        assertEquals(webLocatorNoWithMoreEnterMoreElements.getAttributeClass(), "more-elements-inside no-enter element3");
        assertEquals(webLocatorNoWithMoreEnter.getAttributeClass(), "more-elements-inside no-enter element4");
        assertEquals(webLocatorWithMoreText.getAttributeClass(), "more-elements-inside with-enter element7");
        assertEquals(webLocatorComplex.getAttributeClass(), "more-elements-inside with-enter element11");
    }

    @Test(dataProvider = "testConstructorPathDataProviderText")
    public void shouldFindAllCombinationsForTextSearchTypeTest(WebLocator el, String expectedPath) {

        logger.debug(el.getPath());
        assertTrue(el.click());

        boolean useChildNodesSearch = el.getSearchTextType().contains(SearchType.DEEP_CHILD_NODE);

        String expected = "WebLocator text for search type-searchTextType" + (useChildNodesSearch ? " deep" : "");
        assertEquals(webLocatorLogger.getHtmlText(), expected);

        webLocatorWithMoreEnter.click();
    }
}
