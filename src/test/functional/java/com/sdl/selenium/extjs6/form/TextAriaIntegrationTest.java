package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TextAriaIntegrationTest extends TestBase {

    private Panel form = new Panel(null, "Form Fields").setClasses("x-panel-default-framed");
    private TextArea area = new TextArea(form, "TextArea:");
    private ComboBox time = new ComboBox(form, "Time Field:");

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-fieldtypes");
        area.ready(20);
    }

    @Test
    public void textAreaTest() {
        assertTrue(area.setValue("New York"));
        assertEquals(area.getValue(), "New York");
    }

    @Test
    public void comboBoxTest() {
        assertTrue(time.setValue("2:45 AM"));
        assertEquals(time.getValue(), "2:45 AM");
    }

}
