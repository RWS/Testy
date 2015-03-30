package com.sdl.weblocator.bootstrap.form;

import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.bootstrap.form.SelectPicker;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class SelectPickerTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectPickerTest.class);

    Form form = new Form(null, "Form Title");
    SelectPicker selectPicker = new SelectPicker(form, "Tech:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void select() {
        assertTrue(selectPicker.select("Manual"));
        assertTrue(("Manual").equals(selectPicker.getValue()));
    }
}
