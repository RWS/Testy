package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.link.WebLink;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WebDriverConfigTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverConfigTest.class);

    private WebLink textExamplesLink = new WebLink().setText("open text examples");

    private WebLocator header = new WebLocator().setText("Text with different SearchTypes examples");


    @BeforeClass
    public void startTests() {
        driver.get(InputData.WEB_LOCATOR_URL);
    }

    @Test
    public void shouldInteractWithElementsOpenedInNewWindowThenReturnToPreviewTab() {
        textExamplesLink.openInNewWindow();
        header.assertExists();
        textExamplesLink.returnDefaultWindow();
        textExamplesLink.assertExists();
    }

    @Test
    public void shouldSwitchToFirstTab() {
        textExamplesLink.assertClick();
        WebDriverConfig.switchToLastTab();
        header.assertExists();
        WebDriverConfig.switchToFirstTab(); // Tab is not visible but we can interact with it, TODO see how to make it active
        textExamplesLink.assertExists();
    }
}
