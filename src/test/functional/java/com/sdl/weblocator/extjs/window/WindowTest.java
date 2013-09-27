package com.sdl.weblocator.extjs.window;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.grid.EditorGridPanel;
import com.extjs.selenium.window.Window;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WindowTest extends TestBase {

    Window editorGridPanelWindow = new Window("EditorGridPanel Win");
    EditorGridPanel editorGridPanel = new EditorGridPanel(editorGridPanelWindow, "1").setTitle("EditableGrid");
    Button submitButton = new Button(editorGridPanelWindow, "Submit");

    @BeforeMethod
    public void startTests() {
        Button editorGridPanelButton = new Button(null, "EditorGridPanel");
        editorGridPanelButton.click();
    }

    @AfterMethod
    public void endTests() {
        editorGridPanelWindow.close();
    }

    @Test
    public void maximizeWindow() {
        assertTrue(editorGridPanelWindow.maximize());
        assertFalse(editorGridPanelWindow.maximize());
    }
}