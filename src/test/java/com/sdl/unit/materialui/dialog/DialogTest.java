package com.sdl.unit.materialui.dialog;

import com.sdl.selenium.materialui.dialog.Dialog;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DialogTest {

    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Dialog(), "//*[contains(concat(' ', @class, ' '), ' MuiDialog-root ') and not(contains(@class, 'MuiModal-hidden'))]"},
                {new Dialog().setClasses("cls"), "//*[contains(concat(' ', @class, ' '), ' MuiDialog-root ') and contains(concat(' ', @class, ' '), ' cls ') and not(contains(@class, 'MuiModal-hidden'))]"},
                {new Dialog("Text"), "//*[contains(concat(' ', @class, ' '), ' MuiDialog-root ') and not(contains(@class, 'MuiModal-hidden')) and count(.//h2[contains(concat(' ', @class, ' '), ' MuiTypography-root ') and contains(text(),'Text')]) > 0]"},
                {new Dialog("Text", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' MuiDialog-root ') and not(contains(@class, 'MuiModal-hidden')) and count(.//h2[contains(concat(' ', @class, ' '), ' MuiTypography-root ') and contains(text(),'Text')]) > 0]"},
                {new Dialog("Google's User", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' MuiDialog-root ') and not(contains(@class, 'MuiModal-hidden')) and count(.//h2[contains(concat(' ', @class, ' '), ' MuiTypography-root ') and contains(text(),\"Google's User\")]) > 0]"},
                {new Dialog("Google", "Message"), "//*[contains(concat(' ', @class, ' '), ' MuiDialog-root ') and not(contains(@class, 'MuiModal-hidden')) and count(.//h2[contains(concat(' ', @class, ' '), ' MuiTypography-root ') and contains(text(),'Google')]) > 0 and count(.//*[(contains(.,'Message') or count(*//text()[contains(.,'Message')]) > 0)]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Dialog dialog, String expectedXpath) {
        assertThat(dialog.getXPath(), equalTo(expectedXpath));
    }
}
