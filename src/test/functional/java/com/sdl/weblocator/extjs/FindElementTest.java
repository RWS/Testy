package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.conditions.MessageBoxSuccessCondition;
import com.extjs.selenium.window.MessageBox;
import com.extjs.selenium.window.Window;
import com.sdl.selenium.conditions.ConditionManager;
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
}
