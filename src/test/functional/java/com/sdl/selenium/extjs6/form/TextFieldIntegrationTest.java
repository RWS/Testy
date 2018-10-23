package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TextFieldIntegrationTest extends TestBase {

    private Panel form = new Panel(null, "Form Fields");
    private TextField field = new TextField(form, "Text field:");

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-fieldtypes");
        driver.switchTo().frame("examples-iframe");
        field.ready(20);
    }

    @Test
    public void textFieldTest() {
        assertTrue(field.setValue("New York"));
        assertEquals(field.getValue(), "New York");
    }
}
