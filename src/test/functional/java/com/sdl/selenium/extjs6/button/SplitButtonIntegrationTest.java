package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SplitButtonIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SplitButtonIntegrationTest.class);

    private Table table = new Table().setClasses("x-table-layout");
    Cell cell = table.getCell(1, 2);
    private SplitButton small = new SplitButton(cell, "Small").setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#split-buttons");
        small.ready(10);
        Utils.sleep(1000);
    }

    @Test
    void splitButton() {
        Utils.sleep(200);
        small.clickOnMenu("Menu Item 3");
    }

    @Test(dependsOnMethods = "splitButton")
    public void getAllMenuValuesTest() {
        assertThat(small.getAllMenuValues(), is(Arrays.asList("Menu Item 1", "Menu Item 2", "Menu Item 3")));
    }
}
