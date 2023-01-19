package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.form.CheckBox;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ButtonIntegrationTest extends TestBase {

    private final Table table = new Table().setClasses("x-table-layout");
    private final Cell cell = table.getCell(1, 2);
    private final Button small = new Button(cell, "Small").setVisibility(true);
    private final Button icon = new Button(table).setIconCls("button-home-small").setVisibility(true);
    private final Button iconAndText = new Button(table, "Medium").setIconCls("button-home-medium").setVisibility(true);
    private final CheckBox disabledCheckBox = new CheckBox("Disabled", null);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#basic-buttons");
        driver.switchTo().frame("examples-iframe");
        small.ready(Duration.ofSeconds(20));
        Utils.sleep(1000);
    }

    @Test
    void buttonTest() {
        small.click();
    }

    @Test(dependsOnMethods = "buttonTest")
    public void iconButtonTest() {
        icon.click();
    }

    @Test(dependsOnMethods = "iconButtonTest")
    public void iconAndTextButtonTest() {
        iconAndText.click();
    }

    @Test(dependsOnMethods = "iconAndTextButtonTest")
    public void clickOnDisabledButtonTest() {
        disabledCheckBox.check(true);
        boolean enabled = small.isEnabled();
        assertThat(enabled, is(false));
        small.doClick();
    }
}
