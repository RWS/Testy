package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.window.Window;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DisplayFieldIntegrationTest extends TestBase {

    private Window displayFieldWindow = new Window("DisplayFieldWindow");
    private DisplayField displayField = new DisplayField("displayField", displayFieldWindow);

    @BeforeMethod
    public void startTests() {
        Button displayFieldButton = new Button(null, "DisplayField");
        displayFieldButton.click();
    }

    @AfterMethod
    public void endTests() {
        displayFieldWindow.close();
    }

    @Test
    public void displayFieldGetValue() {
        assertEquals(displayField.getValue(), "DisplayFieldValue");
    }

}
