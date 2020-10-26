package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SelectPickerIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectPickerIntegrationTest.class);

    private Form form = new Form(null, "Form Title");
    private SelectPicker selectPicker = new SelectPicker(form, "Tech:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void select() {
        assertThat(selectPicker.select("Manual"), is(true));
        assertThat(selectPicker.getValue(), equalTo("Manual"));
    }
}
