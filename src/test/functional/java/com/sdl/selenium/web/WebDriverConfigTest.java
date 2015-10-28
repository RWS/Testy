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

    private class FirstTab {
        public WebLink textExamplesLink = new WebLink().setText("open text examples");
    }
    private class SecondTab {
        public WebLocator header = new WebLocator().setText("Text with different SearchTypes examples");
    }

    private FirstTab firstTab = new FirstTab();
    private SecondTab secondTab = new SecondTab();

    @BeforeClass
    public void startTests() {
        driver.get(InputData.WEB_LOCATOR_URL);
    }

    @Test
    public void shouldInteractWithElementsOpenedInNewWindowThenReturnToPreviewTab() {
        firstTab.textExamplesLink.openInNewWindow();
        secondTab.header.assertExists();
        firstTab.textExamplesLink.returnDefaultWindow();
        firstTab.textExamplesLink.assertExists();
    }

    @Test
    public void shouldSwitchToFirstTab() {
        firstTab.textExamplesLink.assertClick();
        WebDriverConfig.switchToLastTab();
        secondTab.header.assertExists();
        WebDriverConfig.getDriver().close();
        WebDriverConfig.switchToFirstTab();
        firstTab.textExamplesLink.assertExists();
    }
}
