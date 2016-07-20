package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.window.Window;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DateFieldIntegrationTest extends TestBase {

    private Window dateFieldWindow = new Window("DateFieldWindow");
    private DateField dateField = new DateField("dateField", dateFieldWindow);

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_URL);
        showComponent("DateField");
    }

    @AfterClass
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
    }

    @Test
    public void setDateField1() {
        assertTrue(dateField.select("05/05/2013"));
        assertEquals(dateField.getValue(), "05/05/2013");
    }

    @Test
    public void setDateField2() {
        assertTrue(dateField.select("05/May/2013", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "05/05/2013");
    }

    @Test
    public void setDateField3() {
        assertTrue(dateField.select("05/May/2033", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "05/05/2033");
    }

    @Test
    public void setDateField4() {
        assertTrue(dateField.select("05/May/1880", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "05/05/1880");
    }

    @Test
    public void setDateField5() {
        assertTrue(dateField.select("05/Apr/2216", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "05/04/2216");
    }
}
