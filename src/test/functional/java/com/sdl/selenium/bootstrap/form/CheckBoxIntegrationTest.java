package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CheckBoxIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBoxIntegrationTest.class);

    Form form = new Form(null, "Form Title");
    CheckBox checkBox = new CheckBox(form);
    CheckBox withEnterWebLocator = new CheckBox(form).setLabel("Label with Enter.", SearchType.CHILD_NODE).setLabelPosition("//");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void check() {
        assertTrue(checkBox.click());
        assertTrue(checkBox.isSelected());
    }

    @Test
    public void clickWith() {
        assertTrue(withEnterWebLocator.click());
    }
}
