package com.sdl.weblocator.extjs.form;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.form.DateField;
import com.extjs.selenium.window.Window;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DateFieldTest extends TestBase {

    Window dateFieldWindow = new Window("DateFieldWindow");
    DateField dateField = new DateField("dateField", dateFieldWindow);

    @BeforeMethod
    public void startTests() {
        showComponent("DateField");
    }

    @AfterMethod
    public void endTests() {
        dateFieldWindow.close();
    }

    @Test
    public void testEditorType() {
        assertTrue(dateField.select("27/03/2013"));
    }

    @Test
    public void setDateField() {
        assertTrue(dateField.select("25/05/2013"));
        assertEquals(dateField.getValue(), "25/05/2013");

        assertTrue(dateField.select("05/05/2013"));
        assertEquals(dateField.getValue(), "05/05/2013");

        assertTrue(dateField.select("05/May/2013", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "05/05/2013");
    }
}
