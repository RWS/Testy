package com.sdl.weblocator.extjs.panel;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.panel.Panel;
import com.extjs.selenium.tab.TabPanel;
import com.extjs.selenium.window.Window;
import com.sdl.selenium.web.WebLocator;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TabPanelTest extends TestBase {

    Window tabPanelWindow = new Window("TabPanel Win");

    TabPanel tabPanel1 = new TabPanel(tabPanelWindow, "Tab1");
    Panel panelWithFrame = new Panel(tabPanel1, "Panel with frame");
    WebLocator elTab1 = new WebLocator("element", panelWithFrame);
    TabPanel tabPanel11 = new TabPanel(panelWithFrame, "Tab11");
    WebLocator elTab11 = new WebLocator("element", tabPanel11);
    TabPanel tabPanel21 = new TabPanel(panelWithFrame, "Tab21");
    WebLocator elTab21 = new WebLocator("element", tabPanel21);

    TabPanel tabPanel2 = new TabPanel(tabPanelWindow, "Tab2");
    Panel panelNoWithFrame = new Panel(tabPanel2, "Panel no frame");
    WebLocator elTab12 = new WebLocator("element", panelNoWithFrame);

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
