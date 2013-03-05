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
                {new WebLocator("testcls"), "//*[contains(@class, 'testcls')]"},
                {new WebLocator(container), "//*[contains(@class, 'container')]//*"},
                {new WebLocator(container).setCls("Cls"), "//*[contains(@class, 'container')]//*[contains(@class, 'Cls')]"},
                {new WebLocator(container, "//*[contains(@class, 'testcls')]"), "//*[contains(@class, 'container')]//*[contains(@class, 'testcls')]"},
                {new WebLocator("testcls", container), "//*[contains(@class, 'container')]//*[contains(@class, 'testcls')]"},
                {new WebLocator("text", "testcls", container), "//*[contains(@class, 'container')]//*[contains(@class, 'testcls') and contains(text(),'text')]"},
                {new WebLocator().setId("ID"), "//*[@id='ID']"},
                {new WebLocator().setId("ID").setText("Text"), "//*[@id='ID' and contains(text(),'Text')]"},
                {new WebLocator().setId("ID").setCls("Cls"), "//*[@id='ID' and contains(@class, 'Cls')]"},
                {new WebLocator().setCls("Cls").setText("Text"), "//*[contains(@class, 'Cls') and contains(text(),'Text')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        assertEquals(el.getPath(), expectedXpath);
    }

    @Test
    public void getPathSelectorSetTag() {
        WebLocator el = new WebLocator();
        el.setTag("textarea");
        assertEquals(el.getPath(), "//textarea");
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasCls() {
        WebLocator el = new WebLocator("testcls");
        el.setTag("textarea");
        assertEquals(el.getPath(), "//textarea[contains(@class, 'testcls')]");
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasContainer() {
        WebLocator el = new WebLocator(container);
        el.setTag("textarea");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//textarea");
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasContainerAndPath() {
        WebLocator el = new WebLocator(container, "//*[contains(@class, 'testcls')]");
        el.setTag("textarea");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//*[contains(@class, 'testcls')]");
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasClsAndContainer() {
        WebLocator el = new WebLocator("testcls", container);
        el.setTag("textarea");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//textarea[contains(@class, 'testcls')]");
    }

    @Test
    public void getPathSelectorSetTagWhenWebLocatorHasTextAndClsAndContainer() {
        WebLocator el = new WebLocator("text", "testcls", container);
        el.setTag("textarea");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//textarea[contains(@class, 'testcls') and contains(text(),'text')]");
    }

    @Test
    public void getPathSelectorSetId() {
        WebLocator el = new WebLocator();
        el.setId("ID");
        assertEquals(el.getPath(), "//*[@id='ID']");
    }

    @Test
    public void getPathSelectorSetId2() {
        WebLocator el = new WebLocator().setId("ID");
        assertEquals(el.getPath(), "//*[@id='ID']");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasCls() {
        WebLocator el = new WebLocator("testcls");
        el.setId("ID");
        assertEquals(el.getPath(), "//*[@id='ID' and contains(@class, 'testcls')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasContainer() {
        WebLocator el = new WebLocator(container);
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//*[@id='ID']");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasContainerAndPath() {
        WebLocator el = new WebLocator(container, "//*[contains(@class, 'testcls')]");
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//*[contains(@class, 'testcls')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasClsAndContainer() {
        WebLocator el = new WebLocator("testcls", container);
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//*[@id='ID' and contains(@class, 'testcls')]");
    }

    @Test
    public void getPathSelectorSetIdWhenWebLocatorHasTextAndClsAndContainer() {
        WebLocator el = new WebLocator("text", "testcls", container);
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//*[@id='ID' and contains(@class, 'testcls') and contains(text(),'text')]");
    }

//    @Test
    // TODO fix getPathSelectorSetIdWhenWebLocatorHasXPath
    public void getPathSelectorSetIdWhenWebLocatorHasXPath() {
        WebLocator el = new WebLocator(null, "//*[contains(@class, 'container')]//*[contains(@class, 'testcls') and contains(text(),'text')]");
        el.setId("ID");
        assertEquals(el.getPath(), "//*[contains(@class, 'container')]//*[@id='ID' and contains(@class, 'testcls') and contains(text(),'text')]");
    }

    @Test
    public void createInstancesWithBuilders(){
        WebLocator locatorBuilder1 = new WebLocator().setTag("div").setId("ID1");

        assertEquals(locatorBuilder1.getTag(), "div");
        assertEquals(locatorBuilder1.getId(), "ID1");
        assertEquals(locatorBuilder1.getClassName(), "WebLocator");
    }
}
