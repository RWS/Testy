package com.sdl.selenium.extjs3.window;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.panel.Panel;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageBoxIntegrationTest extends TestBase {

    private Panel conditionManagerPanel = new Panel("Condition Manager");
    private Button expect1Button = new Button(conditionManagerPanel, "Expect1");
    private Button instantMessageButton = new Button(conditionManagerPanel, "Instant Message");

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
        MessageBoxIntegrationTest.assertThatMessageBoxExists(expected, 5);
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
