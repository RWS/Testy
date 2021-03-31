package com.sdl.selenium;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WebLocatorSuggestionsIntegrationTest2 extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorSuggestionsIntegrationTest2.class);

    private boolean suggestAttributes = false;

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-fieldtypes");
        driver.switchTo().frame("examples-iframe");
    }

    @BeforeClass(alwaysRun = true)
    public void startTest() {
        suggestAttributes = WebLocatorSuggestions.isSuggestAttributes();
        WebLocatorSuggestions.setSuggestAttributes(true);
    }

    @AfterClass(alwaysRun = true)
    public void stopTest() {
        WebLocatorSuggestions.setSuggestAttributes(suggestAttributes);
    }

    @Test
    public void whenContainerDoesNotExistIAmInformed() {
        WebLocator parent = new WebLocator().setId("content-panel-body");
//        Panel parent = new Panel(null, "Form Fields");
        parent.ready(Duration.ofSeconds(10));
        assertThat("parent should not be present", parent.isPresent(), is(true));
        WebLocatorSuggestions.discoverElements(parent);
    }
}
