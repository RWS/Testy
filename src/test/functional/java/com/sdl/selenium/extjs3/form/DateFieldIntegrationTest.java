package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.window.Window;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

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
        assertThat(dateField.select("27/03/2013"), is(true));
    }

    @Test
    public void setDateField() {
        assertThat(dateField.select("25/05/2013"), is(true));
        assertThat(dateField.getValue(), equalTo("25/05/2013"));
    }

    @Test
    public void setDateField1() {
        assertThat(dateField.select("05/05/2013"), is(true));
        assertThat(dateField.getValue(), equalTo("05/05/2013"));
    }

    @Test
    public void setDateField2() {
        assertThat(dateField.select("05/May/2013", "dd/MMM/yyyy"), is(true));
        assertThat(dateField.getValue(), equalTo("05/05/2013"));
    }

    @Test
    public void setDateField3() {
        assertThat(dateField.select("05/May/2033", "dd/MMM/yyyy"), is(true));
        assertThat(dateField.getValue(), equalTo("05/05/2033"));
    }

    @Test
    public void setDateField4() {
        assertThat(dateField.select("05/May/1880", "dd/MMM/yyyy"), is(true));
        assertThat(dateField.getValue(), equalTo("05/05/1880"));
    }

    @Test
    public void setDateField5() {
        assertThat(dateField.select("05/Apr/2216", "dd/MMM/yyyy"), is(true));
        assertThat(dateField.getValue(), equalTo("05/04/2216"));
    }
}
