package com.sdl.selenium.web.utils.internationalization;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fratiu on 8/2/2016.
 */
public class InternationalizedTextRetriever {

    private boolean internationalizedTestsSuite;
    private List<Map<String, String>> translatedStrings;
    private String baseLanguage;
    private String currentLanguage;
    private List<String> variableNotationPatterns;
    private Pattern regexPattern;
    private String allVariablesPattern;
    private final String[] defaultVariableNotations = {"\\{.+?\\}", "%s"};

    private List<String> variableValues = new LinkedList<>();

    /***
     * Utility for retrieving text in the requested language based on text in the base language
     *
     * @param internationalizedTestsSuite enables or disables the search for translations
     * @param translatedStrings           a list of maps representing the collection of strings to be used in tests with their matching translations;
     *                                    each map contains the translations for one string.
     *                                    Strings should not contain html code
     * @param baseLanguage                the language in which the expected strings are written in tests
     */
    public InternationalizedTextRetriever(boolean internationalizedTestsSuite, List<Map<String, String>> translatedStrings, String baseLanguage) {
        this.internationalizedTestsSuite = internationalizedTestsSuite;
        this.translatedStrings = translatedStrings;
        this.baseLanguage = baseLanguage;
        setVariableNotationPatterns(defaultVariableNotations);
    }

    public boolean isInternationalizedTestsSuite() {
        return internationalizedTestsSuite;
    }

    public void setInternationalizedTestsSuite(boolean internationalizedTestsSuite) {
        this.internationalizedTestsSuite = internationalizedTestsSuite;
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
     * @return a copy of the variable notation patterns list
     */
    public List<String> getVariableNotationPatterns() {
        return new LinkedList<>(variableNotationPatterns);
    }

    /***
     * Set the list of patterns for variable notations used in the provided translated strings
     *
     * @param variableNotationPatterns patterns as should be used in regex
     *                                 By default, these are set to: {@link #defaultVariableNotations}
     */
    public void setVariableNotationPatterns(String... variableNotationPatterns) {
        this.variableNotationPatterns = Arrays.asList(variableNotationPatterns);

        String pattern = "(.*?)";
        allVariablesPattern = "((" + this.variableNotationPatterns.get(0) + ")";
        for (int i = 1; i < this.variableNotationPatterns.size(); i++) {
            allVariablesPattern += "|(" + this.variableNotationPatterns.get(i) + ")";
        }
        allVariablesPattern += ")";
        pattern += allVariablesPattern;
        regexPattern = Pattern.compile(pattern);
    }

    /**
     * Gets the translation to the {@link #currentLanguage} of the specified text.
     * If the translation contains variables, the values from textInBaseLanguage will be used.
     *
     * It doesn't work with partial translations for now.
     *
     * @param textInBaseLanguage text in the base language used in tests
     * @return missing text if a translation is expected but not found;
     * the matching translation if found;
     * the original text if internationalization is disabled or languages are not set properly
     */
    public String getText(String textInBaseLanguage) {
        String textIfRetrievalFails = "Missing " + currentLanguage + " text for: " + textInBaseLanguage;
        if (!isInternationalizedTestsSuite() || currentLanguage == null || baseLanguage == null || baseLanguage.equals(currentLanguage)) {
            return textInBaseLanguage;
        } else if (!translatedStrings.isEmpty()) {
            // Searching for the translation of the base string to the current language
            for (Map<String, String> translationsMap : translatedStrings) {
                String recordInBaseLanguage = translationsMap.get(baseLanguage);
                // Identifying the matching record by text in base language
                if (recordInBaseLanguage != null) {
                    String recordInBaseLanguageWithResolvedVariables = getTextWithResolvedVariables(textInBaseLanguage, recordInBaseLanguage, false);
                    if (recordInBaseLanguageWithResolvedVariables.equals(textInBaseLanguage)) {
                        String expectedTextWithVariables = translationsMap.get(currentLanguage);
                        if (expectedTextWithVariables != null && !expectedTextWithVariables.equals("")) {
                            // Resolving variable values if the matching translation contains variables
                            return getTextWithResolvedVariables(textInBaseLanguage, expectedTextWithVariables, true);
                        }
                    }
                }
            }
        }
        return textIfRetrievalFails;
    }

    private String getTextWithResolvedVariables(String finalTextInBaseLanguage, String textWithVariables, boolean isTranslation) {
        Matcher matcherInTranslation = regexPattern.matcher(textWithVariables);
        List<String> invariableTextParts = new ArrayList<>();
        while (matcherInTranslation.find()) {
            invariableTextParts.add(matcherInTranslation.group(1));
        }

        String finalStringInCurrentLanguage = "";
        if (!invariableTextParts.isEmpty()) {
            // Checking if the current text with variables matches finalTextInBaseLanguage
            for (String invariableTextPart : invariableTextParts) {
                if (isTranslation && !textWithVariables.contains(invariableTextPart)) {
                    return textWithVariables;
                } else if (!isTranslation && !finalTextInBaseLanguage.contains(invariableTextPart)) {
                    return textWithVariables;
                }
            }

            // Storing the actual values for text variables
//            DiffMatchPatch diffMatchPatch = new DiffMatchPatch();
//            LinkedList<DiffMatchPatch.Diff> allDifferences = diffMatchPatch.diffMain(textWithVariables, finalTextInBaseLanguage);
//            for (DiffMatchPatch.Diff difference : allDifferences) {
//                if (difference.operation.equals(DiffMatchPatch.Operation.INSERT)) {
//                    variableValues.add(difference.text);
//                }
//            }

            // Build the final string in current language replacing variables with actual values
            matcherInTranslation = regexPattern.matcher(textWithVariables);
            int matchNumber = 0;
            while (matcherInTranslation.find()) {
                finalStringInCurrentLanguage += matcherInTranslation.group(1) + variableValues.get(matchNumber);
                matchNumber++;
            }

            // Checking if there are other text parts after the last variable to be added to the final string
            Pattern lastPartPattern = Pattern.compile("([^" + allVariablesPattern + "]*?)$");
            Matcher lastPartMatcher = lastPartPattern.matcher(textWithVariables);
            if (lastPartMatcher.find()) {
                finalStringInCurrentLanguage += lastPartMatcher.group(1);
            }

        }

        return finalStringInCurrentLanguage.equals("") ? textWithVariables : finalStringInCurrentLanguage;
    }

}
