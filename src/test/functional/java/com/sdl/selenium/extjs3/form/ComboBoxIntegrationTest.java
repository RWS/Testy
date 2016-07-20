package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ComboBoxIntegrationTest extends TestBase {

    private Window comboBoxWindow = new Window("ComboBoxWindow");
    private ComboBox comboBox = new ComboBox("comboBox", comboBoxWindow);

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_URL);
        showComponent("ComboBox");
    }

    @AfterClass
    public void endTests() {
        comboBoxWindow.close();
    }

    @Test
    public void testEditorType() {
        assertTrue(comboBox.select("Romanian"));
        assertEquals(comboBox.getValue(), "Romanian");
    }

    @Test
    public void searchTypeSelect() {
        assertTrue(comboBox.select("Bulgar", SearchType.STARTS_WITH));
        assertEquals(comboBox.getValue(), "Bulgarian");

        assertTrue(comboBox.select("United States", SearchType.CONTAINS));
        assertEquals(comboBox.getValue(), "English(United States)");
    }
}
