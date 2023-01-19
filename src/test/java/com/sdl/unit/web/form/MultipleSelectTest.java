package com.sdl.unit.web.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.MultipleSelect;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MultipleSelectTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new MultipleSelect(),             "//select"},
                {new MultipleSelect(container),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select"},
                {new MultipleSelect(container).setId("id"),    "//*[contains(concat(' ', @class, ' '), ' container ')]//select[@id='id']"},
                {new MultipleSelect(container, "Label"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Label']//following-sibling::*//select"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(MultipleSelect list, String expectedXpath) {
        assertThat(list.getXPath(), equalTo(expectedXpath));
    }
}
