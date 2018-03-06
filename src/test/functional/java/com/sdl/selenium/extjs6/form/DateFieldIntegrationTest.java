package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DateFieldIntegrationTest extends TestBase {

    private DateField dateField = new DateField(null, "Date Field:");

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-fieldtypes");
        dateField.ready(20);
        Utils.sleep(3000);
    }

    @Test
    public void setDateField() {
        assertTrue(dateField.select("27/03/2013"));
        assertEquals(dateField.getValue(), "03/27/13");
    }

    @Test
    public void setDateField0() {
        assertTrue(dateField.select("25/05/2013"));
        assertEquals(dateField.getValue(), "05/25/13");
    }

    @Test
    public void setDateField1() {
        assertTrue(dateField.select("05/05/2013"));
        assertEquals(dateField.getValue(), "05/05/13");
    }

    @Test
    public void setDateField2() {
        assertTrue(dateField.select("05/May/2013", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "05/05/13");
    }

    @Test
    public void setDateField3() {
        assertTrue(dateField.select("06/May/2033", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "05/06/33");
    }

    @Test //(dependsOnMethods = "setDateField3")
    public void setDateField4() {
        assertTrue(dateField.select("07/May/1920", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "05/07/20");
    }

    @Test
    public void setDateField5() {
        assertTrue(dateField.select("05/Apr/2216", "dd/MMM/yyyy"));
        assertEquals(dateField.getValue(), "04/05/16");
    }

    @Test
    public void setDateField6() {
        assertTrue(dateField.select("15/07/2022"));
        assertEquals(dateField.getValue(), "07/15/22");
    }
}
