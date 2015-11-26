package com.sdl.selenium.extjs.form;

import com.sdl.selenium.extjs3.form.ComboBox;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ComboBoxTest extends TestBase {

    private Window comboBoxWindow = new Window("ComboBoxWindow");
    private ComboBox comboBox = new ComboBox("comboBox", comboBoxWindow);

    @BeforeMethod
    public void startTests() {
        showComponent("ComboBox");
    }

    @AfterMethod
    public void endTests() {
        comboBoxWindow.close();
    }

    @Test
    public void testEditorType() {
        assertTrue(comboBox.select("Romanian"));
        assertEquals(comboBox.getValue(), "Romanian");
    }
}
