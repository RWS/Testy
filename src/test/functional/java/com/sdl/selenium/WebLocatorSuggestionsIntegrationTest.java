package com.sdl.selenium;

import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.TextField;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by Beni Lar on 10/27/2015.
 */
public class WebLocatorSuggestionsIntegrationTest extends TestBase {

    @BeforeClass
    public void startTests() {
        driver.get(InputData.SUGGESTIONS_URL);
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

        assertFalse(inputWithLabel.isElementPresent());

        WebLocator suggestedElement = WebLocatorSuggestions.getElementSuggestion(inputWithLabel);

        assertTrue(suggestedElement != null && suggestedElement.isElementPresent());

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
