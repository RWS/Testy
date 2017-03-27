package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class FieldSetIntegrationTest extends TestBase {

    private Panel form = new Panel(null, "Employee Information");
    private FieldSet field = new FieldSet(form, "Details");

    @BeforeClass
    public void startTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#form-fieldcontainer");
        field.ready(20);
    }

    @Test
    public void fieldSetTest() {
        assertFalse(field.isCollapsed());
        assertTrue(field.collapse());
        assertTrue(field.isCollapsed());
        assertTrue(field.expand());
        assertFalse(field.isCollapsed());
    }
}
