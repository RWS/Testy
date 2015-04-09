package com.sdl.weblocator.extjs.panel;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.extjs3.tab.TabPanel;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.WebLocator;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TabPanelTest extends TestBase {

    private Window tabPanelWindow = new Window("TabPanel Win");
    private TabPanel tabPanel1 = new TabPanel(tabPanelWindow, "Tab1");
    private Panel panelWithFrame = new Panel(tabPanel1, "Panel with frame");
    private WebLocator elTab1 = new WebLocator("element", panelWithFrame);
    private TabPanel tabPanel11 = new TabPanel(panelWithFrame, "Tab11");
    private WebLocator elTab11 = new WebLocator("element", tabPanel11);
    private TabPanel tabPanel21 = new TabPanel(panelWithFrame, "Tab21");
    private WebLocator elTab21 = new WebLocator("element", tabPanel21);
    private TabPanel tabPanel2 = new TabPanel(tabPanelWindow, "Tab2");
    private Panel panelNoWithFrame = new Panel(tabPanel2, "Panel no frame");
    private WebLocator elTab12 = new WebLocator("element", panelNoWithFrame);

    @BeforeMethod
    public void startTests() {
        Button tabPanelButton = new Button(null, "TabPanel");
        tabPanelButton.click();
    }

    @AfterMethod
    public void endTests() {
        tabPanelWindow.close();
    }

    @Test
    public void activeTab1() {
        tabPanel1.setActive();
        assertTrue(panelWithFrame.isElementPresent());
        assertEquals(elTab1.getHtmlText(), "element 1");
        assertEquals(elTab11.getHtmlText(), "element 00");
        tabPanel21.setActive();
        assertEquals(elTab21.getHtmlText(), "element 01");
    }

    @Test
    public void activeTab2() {
        tabPanel2.setActive();
        assertTrue(panelNoWithFrame.isElementPresent());
        assertEquals(elTab12.getHtmlText(), "element 2");
    }
}
