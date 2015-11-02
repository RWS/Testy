package com.sdl.weblocator;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Beni Lar on 10/27/2015.
 */
public class WebLocatorUtilsTest extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorUtilsTest.class);

    WebLocator webLocator = new WebLocator().setText("Some more", SearchType.CONTAINS).setCls("bold");
    WebLocator webLocator2 = new WebLocator().setText("Some more", SearchType.CONTAINS).setCls("bold").setLabel("foo").setId("bogusId");
    WebLocator webLocator3 = new WebLocator(webLocator2).setText("Some more", SearchType.CONTAINS).setCls("foo").setLabel("bar").setId("bogusId");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.WEB_LOCATOR_URL);
    }

    @Test
    public void getSuggestions() {

        assert WebLocatorUtils.getSuggestions(webLocator).equals("Found 4 matches for the given WebLocator");

        assert WebLocatorUtils.getSuggestions(webLocator2).equals("\n" +
                "Found 4 matches by removing setId(\"bogusId\") and setLabel(\"foo\") \n" +
                "<span class=\"bold\">Some more</span>\n" +
                "<span class=\"bold\">Some more</span>\n" +
                "<span class=\"bold\">Some more</span>\n" +
                "<span class=\"bold\">Some more</span>\n");

        assert WebLocatorUtils.getSuggestions(webLocator3).startsWith("\nFound 6 matches by removing setCls(\"foo\") and setContainer(\"Some more\") and setId(\"bogusId\") and setLabel(\"bar\") \n");

    }
}
