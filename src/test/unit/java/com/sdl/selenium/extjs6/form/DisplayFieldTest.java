package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DisplayFieldTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DisplayField(), "//div[contains(concat(' ', @class, ' '), ' x-form-display-field ')]"},
                {new DisplayField(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//div[contains(concat(' ', @class, ' '), ' x-form-display-field ')]"},
                {new DisplayField(container, "Project"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'Project') or count(*//text()[contains(.,'Project')]) > 0)]//following-sibling::*//div[contains(concat(' ', @class, ' '), ' x-form-display-field ')]"},
                {new DisplayField(container, "|File Name|Project", SearchType.CONTAINS_ANY), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(('File Name' or count(*//text()['File Name']) > 0) or ('Project' or count(*//text()['Project']) > 0))]//following-sibling::*//div[contains(concat(' ', @class, ' '), ' x-form-display-field ')]"},
                {new DisplayField(container).setLabel("|File Name|Project", SearchType.CONTAINS_ANY), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'File Name') or contains(.,'Project'))]//following-sibling::*//div[contains(concat(' ', @class, ' '), ' x-form-display-field ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DisplayField field, String expectedXpath) {
        assertThat(field.getXPath(), equalTo(expectedXpath));
    }
}
