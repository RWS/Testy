package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PanelIntegrationTest extends TestBase {

    private final Panel collapsiblePanel = new Panel(null, "Collapsible");

    @BeforeClass
    public void startTest() {
        openEXTJSUrl("#basic-panels", collapsiblePanel);
    }

    @Test
    public void collapsedTest() {
        assertThat(collapsiblePanel.collapse(), is(true));
        assertThat(collapsiblePanel.expand(), is(true));
    }
}
