package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ButtonIntegrationTest extends TestBase {

    private Table table = new Table().setClasses("x-table-layout");
    private Cell cell = table.getCell(1, 2);
    private Button small = new Button(cell, "Small").setVisibility(true);
    private Button icon = new Button(table).setIconCls("button-home-small").setVisibility(true);
    private Button iconAndText = new Button(table, "Medium").setIconCls("button-home-medium").setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#basic-buttons");
        driver.switchTo().frame("examples-iframe");
        small.ready(10);
        Utils.sleep(1000);
    }

    @Test
    void buttonTest() {
        small.click();
    }

    @Test
    public void iconButtonTest() {
        icon.click();
    }

    @Test
    public void iconAndTextButtonTest() {
        iconAndText.click();
    }
}
