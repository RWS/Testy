package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DateFieldIntegrationTest extends TestBase {

    private final DateField dateField = new DateField(null, "Date Field:");

    @BeforeClass
    public void startTest() {
        openEXTJSUrl("#form-fieldtypes", dateField);
    }

    @Test
    public void setDateField() {
        assertThat(dateField.select("27/03/2013"), is(true));
        assertThat(dateField.getValue(), equalTo("03/27/13"));
    }

    @Test
    public void setDateField0() {
        assertThat(dateField.select("25/05/2013"), is(true));
        assertThat(dateField.getValue(), equalTo("05/25/13"));
    }

    @Test
    public void setDateField1() {
        assertThat(dateField.select("05/05/2013"), is(true));
        assertThat(dateField.getValue(), equalTo("05/05/13"));
    }

    @Test
    public void setDateField2() {
        assertThat(dateField.select("05/May/2013", "dd/MMM/yyyy"), is(true));
        assertThat(dateField.getValue(), equalTo("05/05/13"));
    }

    @Test
    public void setDateField3() {
        assertThat(dateField.select("06/May/2033", "dd/MMM/yyyy"), is(true));
        assertThat(dateField.getValue(), equalTo("05/06/33"));
    }

    @Test //(dependsOnMethods = "setDateField3")
    public void setDateField4() {
        assertThat(dateField.select("07/May/1920", "dd/MMM/yyyy"), is(true));
        assertThat(dateField.getValue(), equalTo("05/07/20"));
    }

    @Test
    public void setDateField5() {
        assertThat(dateField.select("05/Apr/2216", "dd/MMM/yyyy"), is(true));
        assertThat(dateField.getValue(), equalTo("04/05/16"));
    }

    @Test
    public void setDateField6() {
        assertThat(dateField.select("15/07/2022"), is(true));
        assertThat(dateField.getValue(), equalTo("07/15/22"));
    }
}