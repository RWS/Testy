package com.sdl.selenium.extjs6.tab;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class TabIntegrationTest extends TestBase {

    private final Tab activeTab = new Tab("Active Tab");
    private final Tab inactiveTab = new Tab("Inactive Tab");
    private final Tab closableTab = new Tab("Closable Tab");
    private final Tab inactiveTab1 = new Tab("/Inactive Tab/Closable Tab", SearchType.CONTAINS_ANY);
    private final Tab anotherInactiveTab = new Tab("Another inactive Tab");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#basic-tabs");
        driver.switchTo().frame("examples-iframe");
        activeTab.ready(Duration.ofSeconds(20));
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
        boolean selected = inactiveTab1.isTabDisplayed();
        assertThat(selected, is(true));
    }

    @Test
    void getTabsTest() {
        List<String> tabs = Arrays.asList("Active Tab", "Inactive Tab", "Disabled Tab", "Closable Tab", "Another inactive Tab");
        List<String> tabsName = activeTab.getTabsName();
        assertThat("Actual tabs: " + String.join(",", tabsName), tabsName, containsInAnyOrder(tabs.toArray()));
    }
}
