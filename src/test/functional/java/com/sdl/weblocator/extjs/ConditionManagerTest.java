package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.conditions.MessageBoxFailCondition;
import com.extjs.selenium.conditions.MessageBoxSuccessCondition;
import com.extjs.selenium.panel.Panel;
import com.extjs.selenium.window.MessageBox;
import com.extjs.selenium.window.Window;
import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderCondition;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConditionManagerTest extends TestBase {
    private static final Logger logger = Logger.getLogger(ConditionManagerTest.class);
    Panel conditionManagerPanel = new Panel("Condition Manager");
    Button expect1Button = new Button(conditionManagerPanel, "Expect1");
    Button expect2Button = new Button(conditionManagerPanel, "Expect2");
    Button expect3Button = new Button(conditionManagerPanel, "Expect3");

    private Window messageBoxWindow = new Window(true).setClasses("x-window-dlg").setInfoMessage("MessageBox");
    Button button = new Button(messageBoxWindow, "Nossss");

    private Condition doClick(Button button){
        button.click();

        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Expect1 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect2 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect3 button was pressed"));

        Condition condition = conditionManager.execute();
        RenderCondition renderCondition = (RenderCondition)condition;

        return condition;
    }

    @Test
    public void conditionManagerTest() {
        Condition condition = doClick(expect1Button);

        if(condition.isFail()){
            logger.warn(condition.getResultMessage());
        }
        Assert.assertTrue(condition.isSuccess());

        MessageBox.pressOK();
    }

    @Test
    public void conditionManagerTest1() {
        expect2Button.click();
        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Expect2 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect1 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect3 button was pressed"));
        Assert.assertTrue(conditionManager.execute().isSuccess());
        MessageBox.pressOK();

    }

    @Test
    public void conditionManagerTest2() {
        expect3Button.click();
        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Are you sure you want to do that?"));
        conditionManager.add(new MessageBoxFailCondition("Expect2 button was pressed"));
        conditionManager.add(new MessageBoxFailCondition("Expect1 button was pressed"));
        Assert.assertTrue(conditionManager.execute().isSuccess()); //&& new MessageBox("Are you sure you want to do that?").pressYES());
        MessageBox.pressYes();

    }

    /*@Test
    public void conditionManagerContainsMessage() {
        showYesAlert.click();

        ConditionManager conditionManager = new ConditionManager(10000);
        conditionManager.add(new MessageBoxSuccessCondition("Would you like to save your changes?", true));
        String msg = "";
        if(conditionManager.execute().isSuccess()){
            msg = MessageBox.pressYes();
        }
        Assert.assertEquals(msg, "You are closing a tab that has unsaved changes.\n" +
                "Would you like to save your changes?");
    }

    @Test
    public void messageBoxTest() {
        showAlert.click();
        MessageBox.pressOK();
        //Assert.assertTrue(new MessageBox("Changes saved successfully.").pressOk());
        showAlert.click();
        Assert.assertEquals(MessageBox.pressOK(), "Changes saved successfully.");
    }*/
}
