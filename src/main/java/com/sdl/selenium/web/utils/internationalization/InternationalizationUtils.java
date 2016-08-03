package com.sdl.selenium.web.utils.internationalization;

import java.util.ArrayList;

/**
 * Created by fratiu on 8/2/2016.
 */
public class InternationalizationUtils {

    private static InternationalizedTextRetriever internationalizedTextRetriever = new InternationalizedTextRetriever(false, new ArrayList<TranslatedString>(), "");

    public static void setInternationalizedTextRetriever(InternationalizedTextRetriever internationalizedTextRetriever) {
        InternationalizationUtils.internationalizedTextRetriever = internationalizedTextRetriever;
    }

    public static void setCurrentLanguage(String language) {
        internationalizedTextRetriever.setCurrentLanguage(language);
    }

    public static String getInternationalizedText(String text) {
        return internationalizedTextRetriever.getText(text);
    }
}
