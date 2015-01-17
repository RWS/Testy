package com.sdl.weblocator.extjs.form;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.form.TextField;
import com.extjs.selenium.window.Window;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TextFieldTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(TextFieldTest.class);

    Window textFieldWindow = new Window("TextFieldWindow");
    TextField firstNameTextField = new TextField(textFieldWindow, "First Name:");
    TextField lastNameTextField = new TextField(textFieldWindow, "Las't Name:");
    TextField disableTextField = new TextField(textFieldWindow, "Disable TextField:");

    @BeforeClass
    public void startTests() {
        showComponent("TextField");
    }

    @AfterClass
    public void endTests() {
        textFieldWindow.close();
    }

    @Test
    public void isEditable() {
        assertFalse(firstNameTextField.isEditable());
        assertTrue(lastNameTextField.isEditable());
        assertTrue(disableTextField.isDisabled());
    }

    @Test
    public void getValue() {
        assertTrue(lastNameTextField.setValue("testValue"));
        assertEquals(lastNameTextField.getValue(), "testValue");
        assertEquals(disableTextField.getValue(), "Disable Name");
        assertEquals(firstNameTextField.getValue(), "First Name");
    }

    @Test
    public void performanceTestSetValue() {
        long startMs = System.currentTimeMillis();
        for (int i = 0; i < 10; i++){
            lastNameTextField.setValue("Value" + i);
        }
        long endMs = System.currentTimeMillis();
        logger.info(String.format("performanceTestSetValue took %s ms", endMs - startMs));
    }
}
