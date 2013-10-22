package com.sdl.selenium.web;


import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WebLocatorTest {
    private static final Logger logger = Logger.getLogger(WebLocatorTest.class);

    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new WebLocator(), "//*"},
                {new WebLocator("testcls"), "//*[contains(concat(' ', @class, ' '), ' testcls ')]"},
                {new WebLocator(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*"},
                {new WebLocator(container).setClasses("Cls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' Cls ')]"},
                {new WebLocator(container, "//*[contains(@class, 'testcls')]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"},
                {new WebLocator("testcls", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' testcls ')]"},
                {new WebLocator("text", "testcls", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' testcls ') and contains(text(),'text')]"},
                {new WebLocator().setId("ID"), "//*[@id='ID']"},
                {new WebLocator().setId("ID").setText("Text"), "//*[@id='ID' and contains(text(),'Text')]"},
                {new WebLocator().setId("ID").setClasses("Cls"), "//*[@id='ID' and contains(concat(' ', @class, ' '), ' Cls ')]"},
                {new WebLocator().setClasses("Cls").setText("Text"), "//*[contains(concat(' ', @class, ' '), ' Cls ') and contains(text(),'Text')]"},
                {new WebLocator().setClasses("Cls").setText("Text").setElPath("//a//div//input"), "//a//div//input"},
                {new WebLocator().setClasses("Cls").setElPathSuffix(" and count(.//[text()='Texts']) > 0"), "//*[contains(concat(' ', @class, ' '), ' Cls ') and count(.//[text()='Texts']) > 0]"},
                {new WebLocator().setTag("textarea"), "//textarea"},
                {new WebLocator(container).setTag("textarea"), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea"},
                {new WebLocator().setClasses("Cls").setTag("textarea"), "//textarea[contains(concat(' ', @class, ' '), ' Cls ')]"},
                {new WebLocator(container, "//*[contains(@class, 'testcls')]").setTag("textarea"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls')]"},
                {new WebLocator().setElPathSuffix("count(div) > 0"), "//*[count(div) > 0]"},
                {new WebLocator().setExcludeClasses("cls1", "cls2"), "//*[not(contains(@class, 'cls1')) and not(contains(@class, 'cls2'))]"},
        };
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProviderText() {
        String text = "WebLocator text for search type";
        String cls = "searchTextType";
        return new Object[][]{
                {new WebLocator().setClasses(cls).setText(text, SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and contains(text(),'" + text + "')]"},
                {new WebLocator().setClasses(cls).setText(text, SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and text()='" + text + "']"},
                {new WebLocator().setClasses(cls).setText(text , SearchType.STARTS_WITH), "//*[contains(concat(' ', @class, ' '), ' searchTextType ') and starts-with(text(),'" + text + "')]"},

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
        WebLocator el = new WebLocator(container, "//*[contains(@class, 'testcls')]");
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
        WebLocator el = new WebLocator(null, "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(@class, 'testcls') and contains(text(),'text')]");
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[@id='ID' and contains(@class, 'testcls') and contains(text(),'text')]");
    }

    @Test
    public void createInstancesWithBuilders(){
        WebLocator locatorBuilder1 = new WebLocator().setTag("div").setId("ID1");
        assertEquals(locatorBuilder1.getTag(), "div");
        assertEquals(locatorBuilder1.getId(), "ID1");
        assertEquals(locatorBuilder1.getClassName(), "WebLocator");
    }

    @Test
    public void shouldShowClassInToStringWhenHasOneClass(){
        WebLocator locator = new WebLocator().setClasses("cls1");
        assertEquals(locator.toString(), "cls1");
    }

    @Test
    public void shouldShowClassesInToStringWhenHasManyClass(){
        WebLocator locator = new WebLocator().setClasses("cls1", "cls2");
        assertEquals(locator.toString(), "[cls1, cls2]");
    }

    @Test
    public void resetSearchTextType(){
        WebLocator locator = new WebLocator().setText("text", SearchType.EQUALS);
        assertEquals(locator.getPath(), "//*[text()='text']");
        locator.setSearchTextType(null);
        assertEquals(locator.getPath(), "//*[contains(text(),'text')]");
    }
}
