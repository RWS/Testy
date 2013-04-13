package com.sdl.weblocator.tests;

import com.sdl.selenium.web.WebLocator;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class WebLocatorTest extends TestBase {
    private static final Logger logger = Logger.getLogger(WebLocatorTest.class);
    WebLocator webLocatorId = new WebLocator().setId("webLocatorId");
    WebLocator webLocatorCls = new WebLocator().setCls("webLocatorCls");
    WebLocator webLocatorNotAttribute = new WebLocator().setCls("notExist");

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


}
