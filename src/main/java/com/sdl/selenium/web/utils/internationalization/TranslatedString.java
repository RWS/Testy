package com.sdl.selenium.web.utils.internationalization;

import java.util.Map;

/**
 * Created by fratiu on 8/3/2016.
 */
public class TranslatedString {

    private String textInBaseLanguage;
    private Map<String, String> translations;

    public TranslatedString(String textInBaseLanguage) {
        this.textInBaseLanguage = textInBaseLanguage;
    }

    public String getTextInBaseLanguage() {
        return textInBaseLanguage;
    }

    public TranslatedString setTextInBaseLanguage(String textInBaseLanguage) {
        this.textInBaseLanguage = textInBaseLanguage;
        return this;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }

    public TranslatedString setTranslations(Map<String, String> translations) {
        this.translations = translations;
        return this;
    }
}
