package com.sdl.selenium.extreact.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extreact.panel.Panel;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CheckBoxIntegrationTest extends TestBase {

    private final Panel panel = new Panel();
    private final CheckBox unchecked = new CheckBox("Unchecked", panel);
    private final CheckBox checked = new CheckBox("Checked", panel);
    private final CheckBox disabled = new CheckBox("Disabled", panel);
    private final CheckBox disabledChecked = new CheckBox("Disabled (checked)", panel);

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTREACT_EXAMPLE_URL + "forms/checkboxfield");
        driver.navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        checked.ready(Duration.ofSeconds(10));
        Utils.sleep(1);
    }

    @Test
    public void checkedTest() {
        assertThat(checked.check(true), is(true));
    }

    @Test(dependsOnMethods = "checkedTest")
    public void unCheckedTest() {
        assertThat(unchecked.check(false), is(true));
    }

    @Test(dependsOnMethods = "unCheckedTest")
    public void checkedTest1() {
        assertThat(disabled.isEnabled(), is(false));
    }

    @Test(dependsOnMethods = "checkedTest1")
    public void unCheckedTest1() {
        assertThat(disabledChecked.isChecked(), is(true));
    }
}