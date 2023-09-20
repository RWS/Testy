package com.sdl.unit.materialui.alert;

import com.sdl.selenium.materialui.alert.Alert;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AlertTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Alert(), "//*[contains(concat(' ', @class, ' '), ' MuiAlert-root ')]"},
                {new Alert().setClasses("cls"), "//*[contains(concat(' ', @class, ' '), ' MuiAlert-root ') and contains(concat(' ', @class, ' '), ' cls ')]"},
                {new Alert("Text"), "//*[contains(concat(' ', @class, ' '), ' MuiAlert-root ') and (.='Text' or count(*//text()[.='Text']) > 0)]"},
                {new Alert("Text", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' MuiAlert-root ') and contains(text(),'Text')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Alert alert, String expectedXpath) {
        assertThat(alert.getXPath(), equalTo(expectedXpath));
    }
}
