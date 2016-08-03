package com.sdl.selenium.web;

import com.sdl.selenium.web.utils.internationalization.InternationalizationUtils;
import com.sdl.selenium.web.utils.internationalization.InternationalizedTextRetriever;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by fratiu on 8/3/2016.
 */
public class InternationalizedWebLocatorTest {
    private WebLocator locator;

    @BeforeClass
    public void setupInternationalizedTextRetriever() {
        Map<String, String> record1Translations = new HashMap<>();
        record1Translations.put("English", "car");
        record1Translations.put("French", "voiture");
        record1Translations.put("Romanian", "mașină");
        record1Translations.put("Japanese", "車");
        record1Translations.put("Arabic", "سيارة");

        Map<String, String> record2Translations = new HashMap<>();
        record2Translations.put("English", "negative");
        record2Translations.put("French", "négatif");
        record2Translations.put("Romanian", null);
        record2Translations.put("Japanese", "負");
        record2Translations.put("Arabic", "السلبى");

        Map<String, String> record3Translations = new HashMap<>();
        record3Translations.put("English", "bird");
        record3Translations.put("French", "oiseau");
        record3Translations.put("Romanian", "pasăre");
        record3Translations.put("Japanese", "鳥");
        record3Translations.put("Arabic", "انفلونزا");

        InternationalizationUtils.setInternationalizedTextRetriever(new InternationalizedTextRetriever(true, Arrays.asList(record1Translations, record2Translations, record3Translations), "English"));
    }

    @Test
    public void setTextForBaseLanguage() {
        InternationalizationUtils.setCurrentLanguage("English");
        locator = new WebLocator().withText("car");
        assertThat(locator.getXPath(), containsString("car"));
    }

    @Test
    public void setTextForDefinedLanguage() {
        InternationalizationUtils.setCurrentLanguage("French");
        locator = new WebLocator().withText("car");
        assertThat(locator.getXPath(), containsString("voiture"));
    }

    @Test
    public void setTextForDefinedLanguagesWithSpecialCharacters() {
        InternationalizationUtils.setCurrentLanguage("Romanian");
        locator = new WebLocator().withText("car");
        assertThat(locator.getXPath(), containsString("mașină"));

        InternationalizationUtils.setCurrentLanguage("Japanese");
        locator = new WebLocator().withText("car");
        assertThat(locator.getXPath(), containsString("車"));

        InternationalizationUtils.setCurrentLanguage("Arabic");
        locator = new WebLocator().withText("car");
        assertThat(locator.getXPath(), containsString("سيارة"));
    }

    @Test
    public void setTextUsingOtherCsvRecordThanTheFirstOne() {
        InternationalizationUtils.setCurrentLanguage("French");
        locator = new WebLocator().withText("negative");
        assertThat(locator.getXPath(), containsString("négatif"));
    }

    @Test
    public void setTextForLanguageWithNullValue() {
        InternationalizationUtils.setCurrentLanguage("Romanian");
        locator = new WebLocator().withText("negative");
        assertThat(locator.getXPath(), containsString("null"));
    }

    @Test
    public void setLabelForDefinedLanguagesWithSpecialCharacters() {
        InternationalizationUtils.setCurrentLanguage("Romanian");
        locator = new WebLocator().withLabel("car");
        assertThat(locator.getXPath(), containsString("mașină"));

        InternationalizationUtils.setCurrentLanguage("Japanese");
        locator = new WebLocator().withLabel("car");
        assertThat(locator.getXPath(), containsString("車"));

        InternationalizationUtils.setCurrentLanguage("Arabic");
        locator = new WebLocator().withLabel("car");
        assertThat(locator.getXPath(), containsString("سيارة"));

        InternationalizationUtils.setCurrentLanguage("French");
        locator = new WebLocator().withLabel("negative");
        assertThat(locator.getXPath(), containsString("négatif"));
    }

    @Test
    public void setLabelAndTextForDefinedLanguagesWithSpecialCharacters() {
        InternationalizationUtils.setCurrentLanguage("Romanian");
        locator = new WebLocator().withLabel("car").withText("bird");
        assertThat(locator.getXPath(), containsString("mașină"));
        assertThat(locator.getXPath(), containsString("pasăre"));

        InternationalizationUtils.setCurrentLanguage("Japanese");
        locator = new WebLocator().withLabel("car").withText("bird");
        assertThat(locator.getXPath(), containsString("車"));
        assertThat(locator.getXPath(), containsString("鳥"));

        InternationalizationUtils.setCurrentLanguage("Arabic");
        locator = new WebLocator().withLabel("car").withText("bird");
        assertThat(locator.getXPath(), containsString("سيارة"));
        assertThat(locator.getXPath(), containsString("انفلونزا"));

        InternationalizationUtils.setCurrentLanguage("French");
        locator = new WebLocator().withLabel("negative").withText("bird");
        assertThat(locator.getXPath(), containsString("négatif"));
        assertThat(locator.getXPath(), containsString("oiseau"));
    }

}
