package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DatePickerIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatePickerIntegrationTest.class);

    private Form form = new Form(null, "Form Title");
    private DatePicker datePicker = new DatePicker(form, "dp3");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void selectDate() {
        long startMs = System.currentTimeMillis();
        datePicker.select("19/02/2016");
        long endMs = System.currentTimeMillis();
        LOGGER.info(String.format("selectDate1 took %s ms", endMs - startMs));
        assertThat(datePicker.getDate(), equalTo("19-02-2016"));

        startMs = System.currentTimeMillis();
        datePicker.select("22/09/2016");
        endMs = System.currentTimeMillis();
        LOGGER.info(String.format("selectDate2 took %s ms", endMs - startMs));
        assertThat(datePicker.getDate(), equalTo("22-09-2016"));

        startMs = System.currentTimeMillis();
        datePicker.select("12/09/2016");
        endMs = System.currentTimeMillis();
        LOGGER.info(String.format("selectDate3 took %s ms", endMs - startMs));
        assertThat(datePicker.getDate(), equalTo("12-09-2016"));

        startMs = System.currentTimeMillis();
        datePicker.select("12/09/2016");
        endMs = System.currentTimeMillis();
        LOGGER.info(String.format("selectDate4 took %s ms", endMs - startMs));
        assertThat(datePicker.getDate(), equalTo("12-09-2016"));

        startMs = System.currentTimeMillis();
        datePicker.select("29/01/2016");
        endMs = System.currentTimeMillis();
        LOGGER.info(String.format("selectDate5 took %s ms", endMs - startMs));
        assertThat(datePicker.getDate(), equalTo("29-01-2016"));
    }

    @Test
    public void selectOldDate() {
        datePicker.select("11/08/1872");
        assertThat(datePicker.getDate(), equalTo("11-08-1872"));
    }

    @Test
    public void selectNewDate() {
        datePicker.select("02/08/2052");
        assertThat(datePicker.getDate(), equalTo("02-08-2052"));
    }
}
