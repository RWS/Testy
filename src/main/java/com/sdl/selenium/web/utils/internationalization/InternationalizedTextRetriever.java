package com.sdl.selenium.web.utils.internationalization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by fratiu on 8/2/2016.
 */
public class InternationalizedTextRetriever {

    private final Logger LOGGER = LoggerFactory.getLogger(InternationalizedTextRetriever.class);

    private boolean internationalizedTestsSuite;
    private List<TranslatedString> translatedStrings;
    private String baseLanguage;
    private String currentLanguage;

    public InternationalizedTextRetriever(boolean internationalizedTestsSuite, List<TranslatedString> translatedStrings, String baseLanguage) {
        this.internationalizedTestsSuite = internationalizedTestsSuite;
        this.translatedStrings = translatedStrings;
        this.baseLanguage = baseLanguage;
    }

    public boolean isInternationalizedTestsSuite() {
        return internationalizedTestsSuite;
    }

    public List<TranslatedString> getTranslatedStrings() {
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

    public String getText(String textInBaseLanguage) {
        if (!isInternationalizedTestsSuite() || currentLanguage == null || baseLanguage == null || baseLanguage.equals(currentLanguage)) {
            return textInBaseLanguage;
        } else if (!translatedStrings.isEmpty()) {
            // Searching for the translation of the base string to the current language
            for (TranslatedString translatedString : translatedStrings) {
                String recordInBaseLanguage = translatedString.getTextInBaseLanguage();
                if (recordInBaseLanguage != null && recordInBaseLanguage.equals(textInBaseLanguage)) {
                    String text = translatedString.getTranslations().get(currentLanguage);
                    return text == null ? "null" : text;
                }
            }
        }
        return "null";
    }

}
