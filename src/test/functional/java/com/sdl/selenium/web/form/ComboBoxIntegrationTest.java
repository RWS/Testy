package com.sdl.selenium.web.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ComboBoxIntegrationTest extends TestBase {

    private WebLocator comboBoxDiv = new WebLocator().setClasses("combobox");
    private ComboBox comboBox = new ComboBox(comboBoxDiv);

    @BeforeClass
    public void startTest() {
        driver.get(InputData.WEB_LOCATOR_URL);
    }

    @Test
    public void testEditorType() {
        assertTrue(comboBox.select("Opel"));
        assertEquals(comboBox.getValue(), "Opel");
    }
}
