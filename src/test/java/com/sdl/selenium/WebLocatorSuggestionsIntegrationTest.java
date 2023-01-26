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
import static org.hamcrest.Matchers.*;

public class WebLocatorSuggestionsIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorSuggestionsIntegrationTest.class);

    private boolean suggestAttributes = false;

    @BeforeClass
    public void startTests() {
        driver.get(InputData.SUGGESTIONS_URL);
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
        WebLocator parent = new WebLocator().setId("foo");

        WebLocator textLocator = new WebLocator().setText("Search Type", SearchType.TRIM);

        assertThat("parent should not be present", parent.isPresent(), is(false));
        assertThat("textLocator should be present", textLocator.isPresent(), is(true));

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

        assertThat(inputWithLabel.isPresent(), is(false));

        LOGGER.debug("searching for suggestions:");
        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(inputWithLabel);

        assertThat("original element should not be changed", inputWithLabel.getXPath(), is(originalXpath));

        assertThat(suggestedElement, is(notNullValue()));
        LOGGER.debug("found suggestion: {}", suggestedElement.getXPath());
        suggestedElement.assertReady();

        assertThat(suggestedElement.getAttribute("id"), equalTo("userName"));
    }

    /**
     * The suggested element has a different tag(expected input but found span)
     */
    @Test
    public void elementTagCorrection() {
        TextField inputWithLabel = new TextField().setLabel("User Name:", SearchType.TRIM).setLabelPosition("//following-sibling::*//");
        String originalXPath = inputWithLabel.getXPath();

        assertThat("Element should not be present.", inputWithLabel.isPresent(), is(false));

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(inputWithLabel);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertReady();

        assertThat("The id of the found element should be 'userName'", suggestedElement.getAttribute("id"), equalTo("userName"));

        assertThat("original element should not be changed", inputWithLabel.getXPath(), is(originalXPath));
    }

    /**
     * The suggested element has a different label tag(expected label but found div)
     */
    @Test
    public void labelTagCorrection() {
        WebLocator inputWithLabel = new WebLocator().setLabel("Email:", SearchType.TRIM).setLabelPosition("//following-sibling::*//");
        String originalXPath = inputWithLabel.getXPath();

        assertThat("Element should not be present.", inputWithLabel.isPresent(), is(false));

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(inputWithLabel);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertReady();

        assertThat("The id of the found element should be 'email'", suggestedElement.getAttribute("id"), equalTo("email"));

        assertThat("original element should not be changed", inputWithLabel.getXPath(), is(originalXPath));
    }

    /**
     * The suggested element has a different title search type.
     */
    @Test
    public void titleSearchTypeCorrection() {
        Form form = new Form().setTitle("Form Title");
        String originalXPath = form.getXPath();

        assertThat("The element should not be present.", form.isPresent(), is(false));

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(form);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertReady();

        assertThat("The id of the found element should be 'myForm'", suggestedElement.getAttribute("id"), equalTo("myForm"));
        assertThat("original element should not be changed", form.getXPath(), is(originalXPath));
    }

    /**
     * The suggested element has a different text search type.
     */
    @Test
    public void textSearchTypeCorrection() {
        WebLocator webLocator = new WebLocator().setText("Search Type", SearchType.EQUALS, SearchType.CHILD_NODE);
        String originalXPath = webLocator.getXPath();

        assertThat("The element should not be present.", webLocator.isPresent(), is(false));

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(webLocator);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertReady();

        assertThat("original element should not be changed", webLocator.getXPath(), is(originalXPath));
    }

    /**
     * The suggested element does not have class foo
     */
    @Test
    public void classIsWrong() {
        WebLocator textLocator = new WebLocator().setText("Search Type", SearchType.TRIM).setCls("foo");
        String originalXPath = textLocator.getXPath();

        assertThat("Element should not be present.", textLocator.isPresent(), is(false));

        WebLocator suggestedElement = WebLocatorSuggestions.getSuggestion(textLocator);

        assertThat(suggestedElement, is(notNullValue()));
        suggestedElement.assertReady();

        String actualXPath = textLocator.getXPath();
        assertThat("original element should not be changed!", actualXPath, equalTo(originalXPath));
    }
}
