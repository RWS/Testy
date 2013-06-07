package com.sdl.weblocator.bootstrap;

import com.sdl.bootstrap.form.SelectPicker;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class SelectPickerTest extends TestBase {
    private static final Logger logger = Logger.getLogger(SelectPickerTest.class);

    SelectPicker selectPicker = new SelectPicker(null, "Tech:");

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
