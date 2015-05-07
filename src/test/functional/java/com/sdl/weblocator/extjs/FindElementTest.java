package com.sdl.weblocator.extjs;

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
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class FindElementTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindElementTest.class);
    
    Window elementWindow = new Window("Element");
    Button alertButton = new Button(elementWindow, "Alert");
    Button closeButton = new Button(elementWindow, "Close");
    Button findElementButton = new Button(null, "FindElement");
    Panel findElementsAfterTimeoutFormPanel = new Panel("Find Elements after Timeout");
    TextField timeOutTextField = new TextField(findElementsAfterTimeoutFormPanel, "Timeout:");
    Button showButton = new Button(findElementsAfterTimeoutFormPanel, "Show");
    Button showButton1 = new Button(findElementsAfterTimeoutFormPanel, "Show1").setRenderMillis(200);
    
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
        WebLocator el = new WebLocator().setText("Timeout element in 3000");
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
        DownloadFile downloadFile = new DownloadFile().setLabel("Download");
        assertFalse(hasStatus("disabled", downloadFile));
        assertTrue(hasStatus("enabled", downloadFile));

        SelectPicker selectPicker = new SelectPicker().setLabel("SimpleTextField"); // TODO change label
        assertFalse(hasStatus("disabled", selectPicker));
        assertTrue(hasStatus("enabled", selectPicker));

//        WebLocator el = new WebLocator().setElPath("SimpleTextField"); // TODO see why this has this path
//        assertFalse(hasStatus("disabled", el));
//        assertTrue(hasStatus("enabled", el));
//        assertFalse(hasStatus("test", el));
    }
}
