package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DisplayFieldIntegrationTest extends TestBase {

    private Panel form = new Panel(null, "Form Fields");
    private DisplayField field = new DisplayField(form, "Display field:");

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-fieldtypes");
        driver.switchTo().frame("examples-iframe");
        field.ready(20);
    }

    @Test
    public void displayFieldTest() {
        assertEquals(field.getText(), "Display field value");
    }
}
