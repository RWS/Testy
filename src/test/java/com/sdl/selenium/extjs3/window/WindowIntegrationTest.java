package com.sdl.selenium.extjs3.window;

import com.sdl.selenium.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WindowIntegrationTest extends TestBase {

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
        assertThat(editorGridPanelWindow.maximize(), is(true));
        assertThat(editorGridPanelWindow.maximize(), is(true));
    }
}