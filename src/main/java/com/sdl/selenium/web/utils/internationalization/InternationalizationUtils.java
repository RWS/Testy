package com.sdl.selenium.web.utils.internationalization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fratiu on 8/2/2016.
 */
public class InternationalizationUtils {

    private static InternationalizedTextRetriever internationalizedTextRetriever = new InternationalizedTextRetriever(false, new ArrayList<Map<String, String>>(), "");

    /***
     * @see InternationalizedTextRetriever#InternationalizedTextRetriever(boolean, List, String)
     */
    public static void setInternationalizedTextRetriever(InternationalizedTextRetriever internationalizedTextRetriever) {
        InternationalizationUtils.internationalizedTextRetriever = internationalizedTextRetriever;
    }

    public static void setCurrentLanguage(String language) {
        internationalizedTextRetriever.setCurrentLanguage(language);
    }

    public static void setInternationalizedTestsSuite(boolean enabled) {
        internationalizedTextRetriever.setInternationalizedTestsSuite(enabled);
    }

    /***
     * @see InternationalizedTextRetriever#setVariableNotationPatterns(String...)
     */
    public static void setVariablePatterns(String... variablePatterns) {
        internationalizedTextRetriever.setVariableNotationPatterns(variablePatterns);
    }

    public static String getInternationalizedText(String text) {
        return internationalizedTextRetriever.getText(text);
    }

    public static InternationalizedTextRetriever getInternationalizedTextRetriever() {
        return internationalizedTextRetriever;
    }
}
