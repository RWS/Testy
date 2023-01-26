package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.window.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TextFieldIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextFieldIntegrationTest.class);

    private Window textFieldWindow = new Window("TextFieldWindow");
    private TextField firstNameTextField = new TextField(textFieldWindow, "First Name:");
    private TextField lastNameTextField = new TextField(textFieldWindow, "Las't Name:");
    private TextField disableTextField = new TextField(textFieldWindow, "Disable TextField:");

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
        assertThat(firstNameTextField.isEditable(), is(false));
        assertThat(lastNameTextField.isEditable(), is(true));
        assertThat(disableTextField.isEnabled(), is(false));
    }

    @Test
    public void getValue() {
        assertThat(lastNameTextField.setValue("testValue"), is(true));
        assertThat(lastNameTextField.getValue(), equalTo("testValue"));
        assertThat(lastNameTextField.setValue("testValue9999990"), is(true));
        assertThat(lastNameTextField.getValue(), equalTo("testValue9999990"));
        assertThat(disableTextField.getValue(), equalTo("Disable Name"));
        assertThat(firstNameTextField.getValue(), equalTo("First Name"));
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
