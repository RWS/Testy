package com.sdl.selenium.web;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.utils.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WebLocator1IntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocator1IntegrationTest.class);

    private final WebLocator webLocatorId = new WebLocator().setId("webLocatorId");
    private final WebLocator webLocatorCls = new WebLocator().setClasses("webLocatorCls");
    private final WebLocator webLocatorNotAttribute = new WebLocator().setClasses("notExist");

    private final WebLocator webLocatorWithMoreEnter = new WebLocator().setClasses("more-elements-inside").setText("more enter inside div");
    private final WebLocator webLocatorWithMoreEnterMoreElements = new WebLocator().setClasses("more-elements-inside").setText("more enter inside div", SearchType.DEEP_CHILD_NODE);
    private final WebLocator webLocatorNoWithMoreEnterMoreElements = new WebLocator().setClasses("more-elements-inside no-enter").setText("more enter inside div", SearchType.DEEP_CHILD_NODE);
    private final WebLocator webLocatorNoWithMoreEnter = new WebLocator().setClasses("more-elements-inside no-enter").setText("more enter inside div");
    private final WebLocator webLocatorWithMoreText = new WebLocator().setElPath("//*[contains(@class, 'element7') and concat(text()[1], ./*/text(), text()[2], ./*/text()[contains(.,'care')], text()[3])='Some important text care trebuie']");
    private final WebLocator webLocatorComplex = new WebLocator().setClasses("element11").setText("Some more important text that is very important . end", SearchType.HTML_NODES);
    private final WebLocator webLocatorLogger = new WebLocator().setId("logger");

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
        assertThat(webLocatorId.getAttributeId(), equalTo("webLocatorId"));
        assertThat(webLocatorId.getAttributeClass(), equalTo(""));
    }

    @Test
    public void identifyByClass() {
        assertThat(webLocatorCls.getAttributeClass(),  equalTo("webLocatorCls"));
        assertThat(webLocatorCls.getAttributeId(),  equalTo(""));
    }

    @Test
    public void attributeForNotFoundElement() {
        assertThat(webLocatorNotAttribute.getAttributeClass(), is(nullValue()));
    }

    //@Test
    public void webDriverConfig() {
        WebLocator l = new WebLocator().setClasses("x-tool-maximize");
        LOGGER.debug(l.getXPath());
        LOGGER.debug("//*[contains(@class,'x-tool-maximize')]");

        String browserConfig = InputData.BROWSER_CONFIG;
        PropertiesReader properties = new PropertiesReader(browserConfig);
        String browserName = properties.getProperty("browser");

        Browser browser = Browser.valueOf(browserName.toUpperCase());
        if (browser == Browser.FIREFOX) {
            assertThat(WebDriverConfig.isFireFox(), is(true));
        } else if (browser == Browser.CHROME) {
            assertThat(WebDriverConfig.isChrome(), is(true));
        } else if (browser == Browser.IEXPLORE) {
            assertThat(WebDriverConfig.isIE(), is(true));
        }
    }

    @Test
    public void getTextFromDiv() {
        assertThat(webLocatorWithMoreEnter.getAttributeClass(),  equalTo("more-elements-inside no-enter element4"));
        assertThat(webLocatorWithMoreEnterMoreElements.getAttributeClass(),  equalTo("more-elements-inside with-enter element1"));
        assertThat(webLocatorNoWithMoreEnterMoreElements.getAttributeClass(),  equalTo("more-elements-inside no-enter element3"));
        assertThat(webLocatorNoWithMoreEnter.getAttributeClass(),  equalTo("more-elements-inside no-enter element4"));
        assertThat(webLocatorWithMoreText.getAttributeClass(),  equalTo("more-elements-inside with-enter element7"));
        assertThat(webLocatorComplex.getAttributeClass(),  equalTo("more-elements-inside with-enter element11"));
    }

    @Test(dataProvider = "testConstructorPathDataProviderText")
    public void shouldFindAllCombinationsForTextSearchTypeTest(WebLocator el, String expectedPath) {

        LOGGER.debug(el.getXPath());
        assertThat(el.click(), is(true));

        boolean useChildNodesSearch = el.getPathBuilder().getSearchTextType().contains(SearchType.DEEP_CHILD_NODE);

        String expected = "WebLocator text for search type-searchTextType" + (useChildNodesSearch ? " deep" : "");
        assertThat(webLocatorLogger.getText(), equalTo(expected));

        webLocatorWithMoreEnter.click();
    }
}
