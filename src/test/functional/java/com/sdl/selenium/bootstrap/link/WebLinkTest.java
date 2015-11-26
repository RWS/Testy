package com.sdl.selenium.bootstrap.link;

import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.bootstrap.form.SelectPicker;
import com.sdl.selenium.web.link.WebLink;
import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WebLinkTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLinkTest.class);

    private Form form = new Form(null, "Form Title");
    private WebLink link = new WebLink(form, "Link");
    private SelectPicker selectPicker = new SelectPicker(form, "Tech:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void openNewTab() {
        selectPicker.select("No ADB");
        assertTrue(link.openInNewWindow());
        assertEquals(selectPicker.getValue(), "Auto");
    }

    @Test (dependsOnMethods = "openNewTab")
    public void clickWith() {
        assertTrue(link.returnDefaultWindow());
        assertEquals(selectPicker.getValue(), "No ADB");
    }
}
