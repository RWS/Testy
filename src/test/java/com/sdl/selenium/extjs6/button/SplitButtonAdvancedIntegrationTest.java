package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SplitButtonAdvancedIntegrationTest extends TestBase {

    private final Panel panel = new Panel(null, "Panel with toolbar with diverse contents");
    private final SplitButton splitButton = new SplitButton(panel, "Button w/ Menu");

    @BeforeClass
    public void startTests() {
        openEXTJSUrl("#toolbar-menus", splitButton);
    }

    @Test
    void splitButton() {
        splitButton.mouseOverAndClickOnMenu(SearchType.EQUALS, "Radio Options", "Vista Black");
    }
}
