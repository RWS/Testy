package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TextFieldIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextFieldIntegrationTest.class);

    Window textFieldWindow = new Window("TextFieldWindow");
    TextField firstNameTextField = new TextField(textFieldWindow, "First Name:");
    TextField lastNameTextField = new TextField(textFieldWindow, "Las't Name:");
    TextField disableTextField = new TextField(textFieldWindow, "Disable TextField:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_URL);
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
        assertTrue(lastNameTextField.setValue("testValue9999990"));
        assertEquals(lastNameTextField.getValue(), "testValue9999990");
        assertEquals(disableTextField.getValue(), "Disable Name");
        assertEquals(firstNameTextField.getValue(), "First Name");
    }

    @Test
    public void performanceTestSetValue() {
        long startMs = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            lastNameTextField.setValue("Value" + i);
        }
        long endMs = System.currentTimeMillis();
        LOGGER.info(String.format("performanceTestSetValue took %s ms", endMs - startMs));
    }

    @Test
    public void performanceTestSetAndPasteValue() {
        long startMs = System.currentTimeMillis();
        lastNameTextField.clear();
        String value = "Value is very long for test, I hope that present is very use full for performance.";
        lastNameTextField.sendKeys(value);
        long endMs = System.currentTimeMillis();
        LOGGER.info(String.format("performanceTestSetValue took %s ms", endMs - startMs));
        startMs = System.currentTimeMillis();
        lastNameTextField.setValue(value);
        endMs = System.currentTimeMillis();
        LOGGER.info(String.format("performanceTestPasteValue took %s ms", endMs - startMs));
    }
}
