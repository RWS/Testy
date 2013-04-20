package com.sdl.weblocator.tests;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.form.ComboBox;
import com.extjs.selenium.window.Window;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ComboBoxTest extends TestBase {

    Window comboBoxWindow = new Window("ComboBoxWindow");
    ComboBox comboBox = new ComboBox("comboBox", comboBoxWindow);

    @BeforeMethod
    public void startTests() {
        Button editorGridPanelButton = new Button(null, "ComboBox");
        editorGridPanelButton.click();
    }

    @AfterMethod
    public void endTests() {
        comboBoxWindow.close();
    }

    @Test
    public void testEditorType() {
        assertTrue(comboBox.select("Romanian"));
    }
}
