package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TextAriaIntegrationTest extends TestBase {

    private Panel form = new Panel(null, "Form Fields").setClasses("x-panel-default-framed");
    private TextArea area = new TextArea(form, "TextArea:");

    @BeforeClass
    public void startTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#form-fieldtypes");
        area.ready(20);
    }

    @Test
    public void comboBoxTest() {
        assertTrue(area.setValue("New York"));
        assertEquals(area.getValue(), "New York");
    }

}
