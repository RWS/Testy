package com.sdl.selenium.web.utils.internationalization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by fratiu on 8/2/2016.
 */
public class InternationalizedTextRetriever {

    private final Logger LOGGER = LoggerFactory.getLogger(InternationalizedTextRetriever.class);

    private boolean internationalizedTestsSuite;
    private List<Map<String, String>> translatedStrings;
    private String baseLanguage;
    private String currentLanguage;

    /***
     * Utility for retrieving text in the requested language based on text in the base language
     * @param internationalizedTestsSuite enables or disables the search for translations
     * @param translatedStrings a list of maps representing the collection of strings to be used in tests with their matching translations;
     *                          each map contains the translations for one string
     * @param baseLanguage the language in which the expected strings are written in tests
     */
    public InternationalizedTextRetriever(boolean internationalizedTestsSuite, List<Map<String, String>> translatedStrings, String baseLanguage) {
        this.internationalizedTestsSuite = internationalizedTestsSuite;
        this.translatedStrings = translatedStrings;
        this.baseLanguage = baseLanguage;
    }

    public boolean isInternationalizedTestsSuite() {
        return internationalizedTestsSuite;
    }

    public List<Map<String, String>> getTranslatedStrings() {
        return translatedStrings;
    }

    public String getBaseLanguage() {
        return baseLanguage;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    /***
     * Gets the translation to the {@link #currentLanguage} of the specified text
     * @param textInBaseLanguage text in the base language used in tests
     * @return  missing text if a translation is expected but not found;
     *          the matching translation if found;
     *          the original text if internationalization is disabled or languages are not set properly
     */
    public String getText(String textInBaseLanguage) {
        String textIfRetrievalFails = "Missing " + currentLanguage + " text for: " + textInBaseLanguage;
        if (!isInternationalizedTestsSuite() || currentLanguage == null || baseLanguage == null || baseLanguage.equals(currentLanguage)) {
            return textInBaseLanguage;
        } else if (!translatedStrings.isEmpty()) {
            // Searching for the translation of the base string to the current language
            for (Map<String, String> translationsMap : translatedStrings) {
                    String recordInBaseLanguage = translationsMap.get(baseLanguage);
                    if (recordInBaseLanguage != null && recordInBaseLanguage.equals(textInBaseLanguage)) {
                        String text = translationsMap.get(currentLanguage);
                        if (text == null || text.equals("")) {
                            return textIfRetrievalFails;
                        } else {
                            return text;
                        }
                    }
                }
        }
        return textIfRetrievalFails;
    }

}
