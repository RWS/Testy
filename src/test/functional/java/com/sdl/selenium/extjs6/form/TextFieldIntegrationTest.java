package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TextFieldIntegrationTest extends TestBase {

    private Panel form = new Panel(null, "Form Fields");
    private TextField field = new TextField(form, "Text field:");

    @BeforeClass
    public void startTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#form-fieldtypes");
        field.ready(20);
    }

    @Test
    public void comboBoxTest() {
        assertTrue(field.setValue("New York"));
        assertEquals(field.getValue(), "New York");
    }

}
