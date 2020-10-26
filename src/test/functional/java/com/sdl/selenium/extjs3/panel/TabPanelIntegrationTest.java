package com.sdl.selenium.extjs3.panel;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.tab.TabPanel;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TabPanelIntegrationTest extends TestBase {

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

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_URL);
    }

    @BeforeMethod
    public void startMethod() {
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
        assertThat(panelWithFrame.isPresent(), is(true));
        assertThat(elTab1.getText(), equalTo("element 1"));
        assertThat(elTab11.getText(), equalTo("element 00"));
        tabPanel21.setActive();
        assertThat(elTab21.getText(), equalTo("element 01"));
    }

    @Test
    public void activeTab2() {
        tabPanel2.setActive();
        assertThat(panelNoWithFrame.isPresent(), is(true));
        assertThat(elTab12.getText(), equalTo("element 2"));
    }
}
