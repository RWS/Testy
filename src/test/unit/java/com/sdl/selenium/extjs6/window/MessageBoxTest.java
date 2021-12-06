package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.web.SearchType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MessageBoxTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new MessageBox("Title"), "//*[contains(concat(' ', @class, ' '), ' x-message-box ') and not(contains(@class, 'x-hidden-offsets')) and count(.//*[contains(concat(' ', @class, ' '), ' x-header ')]//*[normalize-space(text())='Title']) > 0]"},
                {new MessageBox("Title", "Message", SearchType.DEEP_CHILD_NODE_OR_SELF),  "//*[contains(concat(' ', @class, ' '), ' x-message-box ') and not(contains(@class, 'x-hidden-offsets')) and count(.//*[contains(concat(' ', @class, ' '), ' x-header ')]//*[normalize-space(text())='Title']) > 0 and count(//*[contains(concat(' ', @class, ' '), ' x-window-body ')]//*[(contains(.,'Message') or count(*//text()[contains(.,'Message')]) > 0)]) > 0]"},
                {new MessageBox("Title", "Message"), "//*[contains(concat(' ', @class, ' '), ' x-message-box ') and not(contains(@class, 'x-hidden-offsets')) and count(.//*[contains(concat(' ', @class, ' '), ' x-header ')]//*[normalize-space(text())='Title']) > 0 and count(//*[contains(concat(' ', @class, ' '), ' x-window-body ')]//*[contains(text(),'Message')]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(MessageBox box, String expectedXpath) {
        assertThat(box.getXPath(), equalTo(expectedXpath));
    }
}
