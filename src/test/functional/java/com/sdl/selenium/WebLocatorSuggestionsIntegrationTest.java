package com.sdl.selenium;

import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WebLocatorSuggestionsIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorSuggestionsIntegrationTest.class);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.SUGGESTIONS_URL);
    }

    @BeforeClass(alwaysRun = true)
    public void startTest() throws Exception {
        WebLocatorSuggestions.setSuggestAttributes(true);
    }

    @AfterClass(alwaysRun = true)
    public void stopTest() {
        WebLocatorSuggestions.setSuggestAttributes(false);
    }

    /**
     * The suggested element has container null
     */
    @Test
    public void parentNotFound() {
        WebLocator parent = new WebLocator().setId("foo");

        WebLocator textLocator = new WebLocator(parent).setText("Search Type", SearchType.TRIM);

        assertFalse(textLocator.isElementPresent());

        WebLocator suggestedElement = WebLocatorSuggestions.getElementSuggestion(textLocator);

        assertTrue(suggestedElement != null && suggestedElement.isElementPresent());
    }

    /**
     * The suggested element has a different label search type
     */
    @Test
    public void labelSearchTypeCorrection() {
        WebLocator inputWithLabel = new WebLocator().setLabel("User Name").setLabelPosition("//following-sibling::*//");
        String originalXpath = inputWithLabel.getXPath();

        assertFalse(inputWithLabel.isElementPresent());

        LOGGER.debug("searching for suggestions:");
        WebLocator suggestedElement = WebLocatorSuggestions.getElementSuggestion(inputWithLabel);

        assertThat("original element should not be changed", inputWithLabel.getXPath(), is(originalXpath));

        assertThat(suggestedElement, is(notNullValue()));
        LOGGER.debug("found suggestion: {}", suggestedElement.getXPath());
        suggestedElement.assertExists();

        // TODO we can assert final element xpath
        assertTrue("userName".equals(suggestedElement.getAttribute("id")));
    }

    /**
     * The suggested element has a different tag(expected input but found span)
     */
    @Test
    public void elementTagCorrection() {
        TextField inputWithLabel = new TextField().setLabel("User Name:", SearchType.TRIM).setLabelPosition("//following-sibling::*//");

        assertFalse(inputWithLabel.isElementPresent());

        WebLocator suggestedElement = WebLocatorSuggestions.getElementSuggestion(inputWithLabel);

        assertTrue(suggestedElement != null && suggestedElement.isElementPresent());

        assertTrue("userName".equals(suggestedElement.getAttribute("id")));
    }

    /**
     * The suggested element has different tag(expected input but found span) and different label tag(expected label but found div)
     */
    @Test
    public void labelTagAndElementTagCorrection() {
        TextField inputWithLabel = new TextField().setLabel("Email:", SearchType.TRIM).setLabelPosition("//following-sibling::*//");

        assertFalse(inputWithLabel.isElementPresent());

        WebLocator suggestedElement = WebLocatorSuggestions.getElementSuggestion(inputWithLabel);

        assertTrue(suggestedElement != null && suggestedElement.isElementPresent());

        assertTrue("email".equals(suggestedElement.getAttribute("id")));
    }

    /**
     * The suggested element has a different title search type.
     */
    @Test
    public void titleSearchTypeCorrection() {
        Form form = new Form().setTitle("Form Title");

        assertFalse(form.isElementPresent());

        WebLocator suggestedElement = WebLocatorSuggestions.getElementSuggestion(form);

        assertTrue(suggestedElement != null && suggestedElement.isElementPresent());

        assertTrue("myForm".equals(suggestedElement.getAttribute("id")));
    }

    /**
     * The suggested element does not have class foo
     */
    @Test
    public void classIsWrong() {
        WebLocator textLocator = new WebLocator().setText("Search Type", SearchType.TRIM).setCls("foo");

        assertFalse(textLocator.isElementPresent());

        WebLocator suggestedElement = WebLocatorSuggestions.getElementSuggestion(textLocator);

        assertTrue(suggestedElement != null && suggestedElement.isElementPresent());
    }
}
