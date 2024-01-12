package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SplitButtonIntegrationTest extends TestBase {

    private final Table table = new Table().setClasses("x-table-layout");
    private final Cell cell = table.getCell(1, 2);
    private final SplitButton splitButton = new SplitButton(cell, "Small").setVisibility(true);

    @BeforeClass
    public void startTests() {
        openEXTJSUrl("#split-buttons", splitButton);
    }

    @Test
    void splitButton() {
        Utils.sleep(200);
        splitButton.clickOnMenu("Menu Item 3");
    }

    @Test(dependsOnMethods = "splitButton")
    public void getAllMenuValuesTest() {
        assertThat(splitButton.getAllMenuValues(), is(Arrays.asList("Menu Item 1", "Menu Item 2", "Menu Item 3")));
    }

    @Test(dependsOnMethods = "getAllMenuValuesTest")
    public void getAllEnabledMenuValuesTest() {
        assertThat(splitButton.getAllEnabledValues(), is(Arrays.asList("Menu Item 1", "Menu Item 2", "Menu Item 3")));
    }
}
