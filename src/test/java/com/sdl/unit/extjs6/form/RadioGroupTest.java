package com.sdl.unit.extjs6.form;

import com.sdl.selenium.extjs6.form.RadioGroup;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RadioGroupTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new RadioGroup(),                       "//*[contains(concat(' ', @class, ' '), ' x-form-checkboxgroup ')]"},
                {new RadioGroup(container),              "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-checkboxgroup ')]"},
                {new RadioGroup(container, "LabelText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-checkboxgroup ') and count(.//label[contains(text(),'LabelText')]) > 0]"},
                {new RadioGroup(container, "LabelText", SearchType.DEEP_CHILD_NODE_OR_SELF), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-checkboxgroup ') and count(.//label[(contains(.,'LabelText') or count(*//text()[contains(.,'LabelText')]) > 0)]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(RadioGroup group, String expectedXpath) {
        assertThat(group.getXPath(), equalTo(expectedXpath));
    }
}
