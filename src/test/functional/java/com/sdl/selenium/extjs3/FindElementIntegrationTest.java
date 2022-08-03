package com.sdl.selenium.extjs3;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.button.DownloadFile;
import com.sdl.selenium.bootstrap.form.SelectPicker;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.conditions.MessageBoxSuccessCondition;
import com.sdl.selenium.extjs3.form.TextField;
import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.extjs3.window.MessageBox;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FindElementIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindElementIntegrationTest.class);
    
    private final Window elementWindow = new Window("Element");
    private final Button alertButton = new Button(elementWindow, "Alert");
    private final Button closeButton = new Button(elementWindow, "Close");
    private final Button findElementButton = new Button(null, "FindElement");
    private final Panel findElementsAfterTimeoutFormPanel = new Panel("Find Elements after Timeout");
    private final TextField timeOutTextField = new TextField(findElementsAfterTimeoutFormPanel, "Timeout:");
    private final Button showButton = new Button(findElementsAfterTimeoutFormPanel, "Show");
    private final Button showButton1 = new Button(findElementsAfterTimeoutFormPanel, "Show1").setRender(Duration.ofMillis(200));
    private final Button showHiddenButton = new Button(findElementsAfterTimeoutFormPanel, "Show Hidden Button");
    private final WebLocator hiddenElVisible = new WebLocator().setId("hiddenButton").setVisibility(true);
    private final WebLocator hiddenElNotVisible = new WebLocator().setId("hiddenButton");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_URL);
    }

    @Test
    public void clickExtreme() {
        findElementButton.click();
        assertThat(alertButton.click(), is(true));
        ConditionManager conditionManager = new ConditionManager(Duration.ofSeconds(10));
        conditionManager.add(new MessageBoxSuccessCondition("Alert button was pressed"));
        assertThat(conditionManager.execute().isSuccess(), is(true));
        MessageBox.pressOK();
        elementWindow.close();

        findElementButton.click();
        assertThat(alertButton.click(), is(true));
        ConditionManager conditionManager1 = new ConditionManager(Duration.ofSeconds(10));
        conditionManager1.add(new MessageBoxSuccessCondition("Alert button was pressed"));
        assertThat(conditionManager1.execute().isSuccess(), is(true));
        MessageBox.pressOK();
    }

    @Test
    public void findElementAfterTimeOut() {
        long startMs = System.currentTimeMillis();
        showButton1.waitToRender();
        long endMs = System.currentTimeMillis();
        LOGGER.debug("wait1 = " + (endMs - startMs));
    }

    @Test
    public void findElementAfterTimeOut2() {
        WebLocator el = new WebLocator().setText("Timeout element in 3000");
        showButton.click();

        long startMs = System.currentTimeMillis();
        assertThat(el.waitToRender(), is(true));
        long endMs = System.currentTimeMillis();

        MessageBox.pressOK();
        timeOutTextField.setValue("3001");
        LOGGER.debug("wait1 = " + (endMs - startMs));
    }

    private <T extends WebLocator> boolean hasStatus(String status, T t) {
        boolean is = false;
        if (status.equals("disabled")) {
            is =  !t.isEnabled();
        } else if (status.equals("enabled")){
            is = t.isEnabled();
        }
        return is;
    }

    @Test
    public void testNewMethod() {
        DownloadFile downloadFile = new DownloadFile().setLabel("Download");
        assertThat(hasStatus("disabled", downloadFile),  is(false));
        assertThat(hasStatus("enabled", downloadFile), is(true));

        SelectPicker selectPicker = new SelectPicker().setLabel("SimpleTextField"); // TODO change label
        assertThat(hasStatus("disabled", selectPicker), is(false));
        assertThat(hasStatus("enabled", selectPicker), is(true));

//        WebLocator el = new WebLocator().setElPath("SimpleTextField"); // TODO see why this has this path
//        assertFalse(hasStatus("disabled", el));
//        assertThat(hasStatus("enabled", el));
//        assertFalse(hasStatus("test", el));
    }

    @Test
    public void whenClickOnNotVisibleElIGetError() {
        showHiddenButton.click();
        hiddenElNotVisible.click();
    }

    @Test (dependsOnMethods = "whenClickOnNotVisibleElIGetError")
    public void whenClickLocatorWithVisibleAttributeIWaitUntilDisplayed() {
        showHiddenButton.click();
        hiddenElVisible.click();
        ConditionManager conditionManager = new ConditionManager();
        conditionManager.add(new MessageBoxSuccessCondition("Hidden Button was clicked"));
        assertThat(conditionManager.execute().isSuccess(), is(true));
        MessageBox.pressOK();
    }
}
