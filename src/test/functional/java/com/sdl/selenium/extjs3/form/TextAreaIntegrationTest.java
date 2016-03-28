package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.window.Window;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TextAreaIntegrationTest extends TestBase {

    private Window textAreaWindow = new Window("TextAreaWindow");
    private TextArea textArea = new TextArea("textArea", textAreaWindow);

    @BeforeMethod
    public void startTests() {
        Button textAreaButton = new Button(null, "TextArea");
        textAreaButton.click();
    }

    @AfterMethod
    public void endTests() {
        textAreaWindow.close();
    }

    @Test
    public void displayFieldGetValue() {
        assertEquals(textArea.getValue(), "Value TextArea");
    }

}
