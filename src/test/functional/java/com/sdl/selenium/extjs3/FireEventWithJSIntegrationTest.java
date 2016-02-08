package com.sdl.selenium.extjs3;

import com.sdl.demo.extjs3.form.SimpleForm;
import com.sdl.selenium.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class FireEventWithJSIntegrationTest extends TestBase {

    private SimpleForm simpleForm = new SimpleForm();

    @Test
    public void fireEvent() {
        simpleForm.cancelButton.focus();
        assertTrue(simpleForm.cancelButton.blur());
        assertTrue(simpleForm.cancelButton.mouseOver());
        assertTrue(simpleForm.cancelButton.doubleClickAt());
    }
}
