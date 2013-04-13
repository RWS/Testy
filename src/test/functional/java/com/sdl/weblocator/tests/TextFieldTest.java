package com.sdl.weblocator.tests;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.form.TextField;
import com.extjs.selenium.window.Window;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TextFieldTest extends TestBase {

    Window textFieldWindow = new Window("TextFieldWindow");
    TextField firstNameTextField = new TextField(textFieldWindow, "First Name:");
    TextField lastNameTextField = new TextField(textFieldWindow, "Last Name");

    @BeforeMethod
    public void startTests() {
        Button editorGridPanelButton = new Button(null, "TextField");
        editorGridPanelButton.click();
    }

    @AfterMethod
    public void endTests() {
        textFieldWindow.close();
    }

    @Test
    public void isEditable() {
        assertFalse(firstNameTextField.isEditable());
        assertTrue(lastNameTextField.isEditable());
    }
}
