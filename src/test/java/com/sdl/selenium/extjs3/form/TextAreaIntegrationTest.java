package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.window.Window;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        assertThat(textArea.getValue(), equalTo("Value TextArea"));
    }

}
