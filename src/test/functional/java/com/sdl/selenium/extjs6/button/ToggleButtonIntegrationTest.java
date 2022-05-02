package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ToggleButtonIntegrationTest extends TestBase {

    private final Table table = new Table().setClasses("x-table-layout");
    private final Cell cell = table.getCell(1, 2);
    private final ToggleButton small = new ToggleButton(cell, "Small");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#toggle-buttons");
        driver.switchTo().frame("examples-iframe");
        small.ready(Duration.ofSeconds(20));
        Utils.sleep(1000);
    }

    @Test
    void buttonTest() {
        small.press(true);
        boolean pressed = small.isPressed();
        assertThat(pressed, is(true));
    }
}
