package com.sdl.selenium.extjs6.tab;

import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TabIntegrationTest extends TestBase {

    private Tab activeTab = new Tab("Active Tab");
    private Tab inactiveTab = new Tab("Inactive Tab");
    private Tab closableTab = new Tab("Closable Tab");

    @BeforeClass
    public void startTests() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#basic-tabs");
        activeTab.ready(20);
    }

    @Test
    void tabTest() {
        boolean selected = inactiveTab.setActive() && inactiveTab.isActive();
        assertThat(selected, is(true));

        boolean selected1 = closableTab.setActive() && closableTab.close();
        assertThat(selected1, is(true));

        boolean selected2 = inactiveTab.isActive();
        assertThat(selected2, is(true));
    }

    @Test
    void isTabDisplayedTest() {
        boolean selected = inactiveTab.isTabDisplayed();
        assertThat(selected, is(true));
    }
}
