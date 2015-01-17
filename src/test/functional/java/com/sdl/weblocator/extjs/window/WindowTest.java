package com.sdl.weblocator.extjs.window;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.window.Window;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class WindowTest extends TestBase {

    private Window editorGridPanelWindow = new Window("EditorGridPanel Win");

    @BeforeMethod
    public void startTests() {
        showComponent("EditorGridPanel");
    }

    @AfterMethod
    public void endTests() {
        editorGridPanelWindow.close();
    }

    @Test
    public void maximizeWindow() {
        assertTrue(editorGridPanelWindow.maximize());
        assertTrue(editorGridPanelWindow.maximize());
    }
}