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
import static org.hamcrest.Matchers.nullValue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WebLocatorSuggestionsIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorSuggestionsIntegrationTest.class);

    private boolean suggestAttributes = false;

    @BeforeClass
    public void startTests() {
        driver.get(InputData.SUGGESTIONS_URL);
    }

    @BeforeClass(alwaysRun = true)
    public void startTest() throws Exception {
        suggestAttributes = WebLocatorSuggestions.isSuggestAttributes();
        WebLocatorSuggestions.setSuggestAttributes(true);
    }

    @AfterClass(alwaysRun = true)
    public void stopTest() {
        WebLocatorSuggestions.setSuggestAttributes(suggestAttributes);
    }

    @Test
    public void whenContainerDoesNotExistIAmInformed() {
        WebLocator parent = new WebLocator().setId("foo");

        WebLocator textLocator = new WebLocator().setText("Search Type", SearchType.TRIM);

        assertFalse(parent.isElementPresent(), "parent should not be present");
        assertTrue(textLocator.isElementPresent(), "textLocator should be present");

        textLocator.setContainer(parent);

        String originalXpath = textLocator.getXPath();

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(textLocator);

        assertThat(suggestedElement, is(nullValue()));
        assertThat("original element should not be changed", textLocator.getXPath(), is(originalXpath));
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
        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(inputWithLabel);

        assertThat("original element should not be changed", inputWithLabel.getXPath(), is(originalXpath));

        assertThat(suggestedElement, is(notNullValue()));
        LOGGER.debug("found suggestion: {}", suggestedElement.getXPath());
        suggestedElement.assertExists();

        assertTrue("userName".equals(suggestedElement.getAttribute("id")));
    }

    /**
     * The suggested element has a different tag(expected input but found span)
     */
    @Test
    public void elementTagCorrection() {
        TextField inputWithLabel = new TextField().setLabel("User Name:", SearchType.TRIM).setLabelPosition("//following-sibling::*//");
        String originalXPath = inputWithLabel.getXPath();

        assertFalse(inputWithLabel.isElementPresent(), "Element should not be present.");

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(inputWithLabel);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertExists();

        assertTrue("userName".equals(suggestedElement.getAttribute("id")), "The id of the found element should be 'userName'");

        assertThat("original element should not be changed", inputWithLabel.getXPath(), is(originalXPath));
    }

    /**
     * The suggested element has a different label tag(expected label but found div)
     */
    @Test
    public void labelTagCorrection() {
        WebLocator inputWithLabel = new WebLocator().setLabel("Email:", SearchType.TRIM).setLabelPosition("//following-sibling::*//");
        String originalXPath = inputWithLabel.getXPath();

        assertFalse(inputWithLabel.isElementPresent(), "Element should not be present.");

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(inputWithLabel);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertExists();

        assertTrue("email".equals(suggestedElement.getAttribute("id")), "The id of the found element should be 'email'");

        assertThat("original element should not be changed", inputWithLabel.getXPath(), is(originalXPath));
    }

    /**
     * The suggested element has a different title search type.
     */
    @Test
    public void titleSearchTypeCorrection() {
        Form form = new Form().setTitle("Form Title");
        String originalXPath = form.getXPath();

        assertFalse(form.isElementPresent(), "The element should not be present.");

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(form);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertExists();

        assertTrue("myForm".equals(suggestedElement.getAttribute("id")), "The id of the found element should be 'myForm'");
        assertThat("original element should not be changed", form.getXPath(), is(originalXPath));
    }

    /**
     * The suggested element has a different text search type.
     */
    @Test
    public void textSearchTypeCorrection() {
        WebLocator webLocator = new WebLocator().setText("Search Type", SearchType.EQUALS, SearchType.CHILD_NODE);
        String originalXPath = webLocator.getXPath();

        assertFalse(webLocator.isElementPresent(), "The element should not be present.");

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(webLocator);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertExists();

        assertThat("original element should not be changed", webLocator.getXPath(), is(originalXPath));
    }

    /**
     * The suggested element does not have class foo
     */
    @Test
    public void classIsWrong() {
        WebLocator textLocator = new WebLocator().setText("Search Type", SearchType.TRIM).setCls("foo");
        String originalXPath = textLocator.getXPath();

        assertFalse(textLocator.isElementPresent(), "Element should not be present.");

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(textLocator);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertExists();

        assertThat("original element should not be changed", textLocator.getXPath(), is(originalXPath));
    }
}
