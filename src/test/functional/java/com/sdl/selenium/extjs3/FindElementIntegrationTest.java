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
import org.openqa.selenium.ElementNotVisibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class FindElementIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindElementIntegrationTest.class);
    
    private Window elementWindow = new Window("Element");
    private Button alertButton = new Button(elementWindow, "Alert");
    private Button closeButton = new Button(elementWindow, "Close");
    private Button findElementButton = new Button(null, "FindElement");
    private Panel findElementsAfterTimeoutFormPanel = new Panel("Find Elements after Timeout");
    private TextField timeOutTextField = new TextField(findElementsAfterTimeoutFormPanel, "Timeout:");
    private Button showButton = new Button(findElementsAfterTimeoutFormPanel, "Show");
    private Button showButton1 = new Button(findElementsAfterTimeoutFormPanel, "Show1").withRenderMillis(200);
    private Button showHiddenButton = new Button(findElementsAfterTimeoutFormPanel, "Show Hidden Button");
    private WebLocator hiddenElVisible = new WebLocator().withId("hiddenButton").withVisibility(true);
    private WebLocator hiddenElNotVisible = new WebLocator().withId("hiddenButton");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_URL);
    }

    @Test
    public void clickExtreme() {
        findElementButton.click();
        assertTrue(alertButton.click());
        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Alert button was pressed"));
        Assert.assertTrue(conditionManager.execute().isSuccess());
        MessageBox.pressOK();
        elementWindow.close();

        findElementButton.click();
        assertTrue(alertButton.click());
        ConditionManager conditionManager1 = new ConditionManager(10000);
        conditionManager1.add(new MessageBoxSuccessCondition("Alert button was pressed"));
        Assert.assertTrue(conditionManager1.execute().isSuccess());
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
        WebLocator el = new WebLocator().withText("Timeout element in 3000");
        showButton.click();

        long startMs = System.currentTimeMillis();
        Assert.assertTrue(el.waitToRender());
        long endMs = System.currentTimeMillis();

        MessageBox.pressOK();
        timeOutTextField.setValue("3001");
        LOGGER.debug("wait1 = " + (endMs - startMs));
    }

    private <T extends WebLocator> boolean hasStatus(String status, T t) {
        boolean is = false;
        if (status.equals("disabled")) {
            is =  t.isDisabled();
        } else if (status.equals("enabled")){
            is = !t.isDisabled();
        }
        return is;
    }

    @Test
    public void testNewMethod() {
        DownloadFile downloadFile = new DownloadFile().withLabel("Download");
        assertFalse(hasStatus("disabled", downloadFile));
        assertTrue(hasStatus("enabled", downloadFile));

        SelectPicker selectPicker = new SelectPicker().withLabel("SimpleTextField"); // TODO change label
        assertFalse(hasStatus("disabled", selectPicker));
        assertTrue(hasStatus("enabled", selectPicker));

//        WebLocator el = new WebLocator().withElxPath("SimpleTextField"); // TODO see why this has this path
//        assertFalse(hasStatus("disabled", el));
//        assertTrue(hasStatus("enabled", el));
//        assertFalse(hasStatus("test", el));
    }

    @Test (expectedExceptions = ElementNotVisibleException.class)
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
        assertTrue(conditionManager.execute().isSuccess());
        MessageBox.pressOK();
    }
}
