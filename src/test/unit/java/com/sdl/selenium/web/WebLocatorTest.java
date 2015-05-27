package com.sdl.selenium.web;


import com.sdl.selenium.extjs3.ExtJsComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WebLocatorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorTest.class);

    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new WebLocator(), "//*"},
                {new WebLocator().setTag("td"), "//td"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ')]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and contains(concat(' ', @class, ' '), ' classes ')]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ')]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls"), "//td[contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id"), "//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label"), "//label[text()='Label']//following-sibling::*//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls'))]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test']"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block"), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2), "//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(text(),'Text') and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},
                {new WebLocator().setTag("td").setBaseCls("BaseCls").setClasses("classes").setCls("Cls").setExcludeClasses("noCls").setId("Id").setLabel("Label").setLabelTag("info").setName("Name").setElPathSuffix("elPath", "@value='Test'").setVisibility(true).setStyle("display: block").setPosition(2).setContainer(container).setText("Text").setTitle("Title"), "//*[contains(concat(' ', @class, ' '), ' container ')]//info[text()='Label']//following-sibling::*//td[@id='Id' and @name='Name' and contains(concat(' ', @class, ' '), ' BaseCls ') and @class='Cls' and contains(concat(' ', @class, ' '), ' classes ') and not(contains(@class, 'noCls')) and @value='Test' and contains(text(),'Text') and contains(@style ,'display: block') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 2]"},

                {new WebLocator("testcls"), "//*[contains(concat(' ', @class, ' '), ' testcls ')]"},
                {new WebLocator(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*"},
                {new WebLocator(container).setClasses("Cls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' Cls ')]"},
                {new WebLocator(container).setElPath("//*[contains(@class, 'testcls')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"},
                {new WebLocator("testcls", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' testcls ')]"},
                {new WebLocator("text", "testcls", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]"},
                {new WebLocator().setId("ID"), "//*[@id='ID']"},
                {new WebLocator().setId("ID").setText("Text"), "//*[@id='ID' and contains(text(),'Text')]"},
                {new WebLocator().setId("ID").setClasses("Cls"), "//*[@id='ID' and contains(concat(' ', @class, ' '), ' Cls ')]"},
                {new WebLocator().setClasses("Cls").setText("Text"), "//*[contains(concat(' ', @class, ' '), ' Cls ') and contains(text(),'Text')]"},
                {new WebLocator().setClasses("Cls").setText("Text").setElPath("//a//div//input"), "//a//div//input"},
                {new WebLocator().setClasses("Cls").setElPathSuffix("has-test", "count(.//[text()='Texts']) > 0"), "//*[contains(concat(' ', @class, ' '), ' Cls ') and count(.//[text()='Texts']) > 0]"},
                {new WebLocator().setTag("textarea"), "//textarea"},
                {new WebLocator(container).setTag("textarea"), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea"},
                {new WebLocator().setClasses("Cls").setTag("textarea"), "//textarea[contains(concat(' ', @class, ' '), ' Cls ')]"},
                {new WebLocator(container).setElPath("//*[contains(@class, 'testcls')]").setTag("textarea"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"},
                {new WebLocator().setElPathSuffix("has-div", "count(div) > 0"), "//*[count(div) > 0]"},
                {new WebLocator().setExcludeClasses("cls1", "cls2"), "//*[not(contains(@class, 'cls1')) and not(contains(@class, 'cls2'))]"},
                {new WebLocator().setClasses("Cls").setTitle("TITLE"), "//*[contains(concat(' ', @class, ' '), ' Cls ')]"},
        };
    }

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
                {new ExtJsComponent().setVisibility(true), "//*[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0]"},
                {new ExtJsComponent().setCls("Cls").setVisibility(true), "//*[@class='Cls' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0]"},

        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        assertEquals(el.getPath(), expectedXpath);
    }

    @Test(dataProvider = "testConstructorPathDataProviderText")
    public void getPathSelectorCorrectlyFromConstructorsByText(WebLocator el, String expectedXpath) {
        assertEquals(el.getPath(), expectedXpath);
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasClsAndContainer() {
        WebLocator el = new WebLocator("testcls", container);
        el.setTag("textarea");
        assertEquals(el.getPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[contains(concat(' ', @class, ' '), ' testcls ')]");
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasTextAndClsAndContainer() {
        WebLocator el = new WebLocator("text", "testcls", container);
        el.setTag("textarea");
        assertEquals(el.getPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasContainerAndPath() {
        WebLocator el = new WebLocator(container).setElPath("//*[contains(@class, 'testcls')]");
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasClsAndContainer() {
        WebLocator el = new WebLocator("testcls", container);
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(concat(' ', @class, ' '), ' testcls ')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasTextAndClsAndContainer() {
        WebLocator el = new WebLocator("text", "testcls", container);
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]");
    }

    //@Test
    // TODO fix getPathSelectorSetIdWhenWebLocatorHasXPath
    public void getPathSelectorSetIdWhenWebLocatorHasXPath() {
        WebLocator el = new WebLocator().setElPath("//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls') and contains(text(),'text')]");
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(@class, 'testcls') and contains(text(),'text')]");
    }

    @Test
    public void createInstancesWithBuilders() {
        WebLocator locatorBuilder1 = new WebLocator().setTag("div").setId("ID1");
        assertEquals(locatorBuilder1.getTag(), "div");
        assertEquals(locatorBuilder1.getId(), "ID1");
        assertEquals(locatorBuilder1.getClassName(), "WebLocator");
    }

    @Test
    public void shouldShowClassInToStringWhenHasOneClass() {
        WebLocator locator = new WebLocator().setClasses("cls1");
        assertEquals(locator.getPathBuilder().toString(), "cls1");
    }

    @Test
    public void shouldShowClassesInToStringWhenHasManyClass() {
        WebLocator locator = new WebLocator().setClasses("cls1", "cls2");
        assertEquals(locator.getPathBuilder().toString(), "[cls1, cls2]");
    }

    @Test
    public void resetSearchTextType() {
        WebLocator locator = new WebLocator().setText("text", SearchType.EQUALS);
        assertEquals(locator.getPath(), "//*[text()='text']");
        locator.setSearchTextType(null);
        assertEquals(locator.getPath(), "//*[contains(text(),'text')]");
    }

    @Test
    public void setSearchTextType() {
        WebLocator locator = new WebLocator().setText("text", SearchType.STARTS_WITH);
        assertEquals(locator.getSearchTextType().size(), 1);
    }
}
