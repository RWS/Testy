package com.sdl.selenium.toolsqa;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DatePickerIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatePickerIntegrationTest.class);

    private WebLocator container = new WebLocator("demo-frame");
    private DatePicker datePicker = new DatePicker(container, "Date:");

    @BeforeClass
    public void startTests() {
        driver.get("https://demoqa.com/datepicker/");
    }

    @Test
    public void selectDate() {
        long startMs = System.currentTimeMillis();
        datePicker.select("19/02/2016");
        long endMs = System.currentTimeMillis();
        LOGGER.info("selectDate1 took {} ms", endMs - startMs);
        assertThat(datePicker.getDate(), equalTo("02/19/2016"));

        startMs = System.currentTimeMillis();
        datePicker.select("22/09/2016");
        endMs = System.currentTimeMillis();
        LOGGER.info("selectDate2 took {}} ms", endMs - startMs);
        assertThat(datePicker.getDate(), equalTo("09/22/2016"));

        startMs = System.currentTimeMillis();
        datePicker.select("12/09/2016");
        endMs = System.currentTimeMillis();
        LOGGER.info("selectDate3 took {} ms", endMs - startMs);
        assertThat(datePicker.getDate(), equalTo("09/12/2016"));

        startMs = System.currentTimeMillis();
        datePicker.select("12/09/2016");
        endMs = System.currentTimeMillis();
        LOGGER.info("selectDate4 took {} ms", endMs - startMs);
        assertThat(datePicker.getDate(), equalTo("09/12/2016"));

        startMs = System.currentTimeMillis();
        datePicker.select("29/01/2016");
        endMs = System.currentTimeMillis();
        LOGGER.info("selectDate5 took {} ms", endMs - startMs);
        assertThat(datePicker.getDate(), equalTo("01/29/2016"));
    }

    @Test
    public void selectOldDate() {
        datePicker.select("11/08/2015");
        assertThat(datePicker.getDate(), equalTo("08/11/2015"));
    }

    @Test
    public void selectNewDate() {
        datePicker.select("02/08/2025");
        assertThat(datePicker.getDate(), equalTo("08/02/2025"));
    }

    @Test
    public void setValueDate() {
        datePicker.setValue("02/08/2020");
        assertThat(datePicker.getDate(), equalTo("02/08/2020"));
    }
}
