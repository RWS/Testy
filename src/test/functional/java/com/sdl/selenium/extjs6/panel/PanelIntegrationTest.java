package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PanelIntegrationTest extends TestBase {

    private Panel collapsiblePanel = new Panel(null, "Collapsible");

    @BeforeClass
    public void startTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#basic-panels");
        collapsiblePanel.ready(20);
    }

    @Test
    public void collapsedTest() {
        assertThat(collapsiblePanel.collapse(), is(true));
        assertThat(collapsiblePanel.expand(), is(true));
    }
}
