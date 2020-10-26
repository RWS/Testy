package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LabelTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Label("LabelText"), "//label[contains(text(),'LabelText')]"},
                {new Label(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//label"},
                {new Label(container).setLabel("Label", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'Label')]//following-sibling::*//label"},
                {new Label(container).setLabel("Label", SearchType.CONTAINS, SearchType.HTML_NODE), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(.,'Label')]//following-sibling::*//label"},
                {new Label(container, "LabelText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'LabelText')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Label label, String expectedXpath) {
        assertThat(label.getXPath(), equalTo(expectedXpath));
    }
}
