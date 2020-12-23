package com.sdl.selenium.extreact.panel;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PanelIntegrationTest extends TestBase {

    private final Panel titlePanel = new Panel(null, "Title");
    private final Panel collapsiblePanel = new Panel(null, "Collapsible");

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTREACT_EXAMPLE_URL + "panels/basic_panels");
        driver.switchTo().frame("examples-iframe");
        titlePanel.ready(Duration.ofSeconds(10));
    }

    @Test
    public void isPresentTest() {
        assertThat(titlePanel.isPresent(), is(true));
    }

//    @Test (dependsOnMethods = "isPresentTest")
//    public void collapsedTest() {
//        assertThat(collapsiblePanel.collapse(), is(true));
//        assertThat(collapsiblePanel.expand(), is(true));
//    }
}
