package com.sdl.weblocator.extjs.window;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.panel.Panel;
import com.extjs.selenium.window.MessageBox;
import com.sdl.weblocator.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageBoxTest extends TestBase {

    Panel conditionManagerPanel = new Panel("Condition Manager");
    Button expect1Button = new Button(conditionManagerPanel, "Expect1");
    Button instantMessageButton = new Button(conditionManagerPanel, "Instant Message");

    public static void assertThatMessageBoxExists(String expected, int waitSeconds) {
        String message = MessageBox.getMessage(waitSeconds);
        Assert.assertEquals(message, expected);
        MessageBox.pressOK();
    }

    public static void assertThatMessageBoxExists(String expected) {
        assertThatMessageBoxExists(expected, 0);
    }

    @Test
    public void getNullIfNoMessageExistTest() {
        String message = MessageBox.getMessage();
        Assert.assertNull(message);
    }

    @Test
    public void getMessageIn1SecTest() {
        expect1Button.click();
        String expected = "Expect1 button was pressed";
        MessageBoxTest.assertThatMessageBoxExists(expected, 5);
    }

    @Test
    public void getInstantMessageTest() {
        instantMessageButton.click();
        String expected = "Instant Message button was pressed";
        String message = MessageBox.getMessage();
        Assert.assertEquals(message, expected);
        MessageBox.pressOK();
    }
}
