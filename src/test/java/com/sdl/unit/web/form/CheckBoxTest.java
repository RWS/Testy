package com.sdl.unit.web.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.CheckBox;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckBoxTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new CheckBox(),           "//input[@type='checkbox']"},
                {new CheckBox(container),  "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new CheckBox("Id"), "//input[@id='Id' and @type='checkbox']"},
                {new CheckBox("Id").setType("check"), "//input[@id='Id' and @type='check']"},
                {new CheckBox(container).setTag("span").setLabel("No Limit", SearchType.DEEP_CHILD_NODE_OR_SELF, SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'No Limit') or count(*//text()[contains(.,'No Limit')]) > 0)]//following-sibling::*//span[@type='checkbox']"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(CheckBox combo, String expectedXpath) {
        assertThat(combo.getXPath(), equalTo(expectedXpath));
    }
}
