package com.sdl.weblocator.bootstrap;

import com.sdl.bootstrap.form.CheckBox;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CheckBoxTest extends TestBase {
    private static final Logger logger = Logger.getLogger(CheckBoxTest.class);

    CheckBox checkBox = new CheckBox();

//    @BeforeClass
//    public void startTests() {
//        driver.get(InputData.BOOTSTRAP_URL);
//    }

    @Test
    public void check() {
        assertTrue(checkBox.click());
        assertTrue(checkBox.isSelected());
    }
}
