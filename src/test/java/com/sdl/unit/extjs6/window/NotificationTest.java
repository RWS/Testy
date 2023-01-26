package com.sdl.unit.extjs6.window;

import com.sdl.selenium.extjs6.notification.Notification;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class NotificationTest {

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Notification(),                                    "//*[contains(concat(' ', @class, ' '), ' msg-container ')]//*[contains(concat(' ', @class, ' '), ' msg ') and contains(concat(' ', @class, ' '), ' x-border-box ') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Notification("Message"),                      "//*[contains(concat(' ', @class, ' '), ' msg-container ')]//*[contains(concat(' ', @class, ' '), ' msg ') and contains(concat(' ', @class, ' '), ' x-border-box ') and (contains(.,'Message') or count(*//text()[contains(.,'Message')]) > 0) and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Notification("Message", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' msg-container ')]//*[contains(concat(' ', @class, ' '), ' msg ') and contains(concat(' ', @class, ' '), ' x-border-box ') and (contains(.,'Message') or count(*//text()[contains(.,'Message')]) > 0) and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Notification("Successfully started to download eng.txt.").setTemplate("text", ".=%s"), "//*[contains(concat(' ', @class, ' '), ' msg-container ')]//*[contains(concat(' ', @class, ' '), ' msg ') and contains(concat(' ', @class, ' '), ' x-border-box ') and .='Successfully started to download eng.txt.' and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Notification notification, String expectedXpath) {
        assertThat(notification.getXPath(), equalTo(expectedXpath));
    }
}
