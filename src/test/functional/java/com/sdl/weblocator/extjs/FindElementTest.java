package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.conditions.MessageBoxSuccessCondition;
import com.extjs.selenium.form.TextField;
import com.extjs.selenium.panel.Panel;
import com.extjs.selenium.window.MessageBox;
import com.extjs.selenium.window.Window;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.web.WebLocator;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class FindElementTest extends TestBase {
    private static final Logger logger = Logger.getLogger(FindElementTest.class);
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
        logger.debug("wait1 = " + (endMs - startMs));
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
        logger.debug("wait1 = " + (endMs - startMs));
    }
}
