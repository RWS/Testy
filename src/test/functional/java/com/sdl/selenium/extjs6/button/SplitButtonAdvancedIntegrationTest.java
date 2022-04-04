package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SplitButtonAdvancedIntegrationTest extends TestBase {

    private final Panel panel = new Panel(null, "Panel with toolbar with diverse contents");
    private final SplitButton splitButton = new SplitButton(panel, "Button w/ Menu");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#toolbar-menus");
        driver.switchTo().frame("examples-iframe");
        splitButton.ready(Duration.ofSeconds(10));
        Utils.sleep(1000);
    }

    @Test
    void splitButton() {
        splitButton.mouseOverAndClickOnMenu(SearchType.EQUALS, "Radio Options", "Vista Black");
    }
}
