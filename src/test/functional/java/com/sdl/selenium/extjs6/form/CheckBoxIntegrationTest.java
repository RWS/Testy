package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CheckBoxIntegrationTest extends TestBase {

    private Panel panel = new Panel(null, "Form Fields");
    private CheckBox checkBox = new CheckBox(panel, "Checkbox:");
    private CheckBox boxLabel = new CheckBox("box label", panel);

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-fieldtypes");
        driver.switchTo().frame("examples-iframe");
        checkBox.setVersion(version);
        boxLabel.setVersion(version);
        boxLabel.ready(10);
        Utils.sleep(1);
    }

    @Test
    public void checkedTest() {
        assertThat(boxLabel.check(true), is(true));
    }

    @Test(dependsOnMethods = "checkedTest")
    public void unCheckedTest() {
        assertThat(boxLabel.check(false), is(true));
    }

    @Test(dependsOnMethods = "unCheckedTest")
    public void checkedTest1() {
        assertThat(checkBox.check(true), is(true));
    }

    @Test(dependsOnMethods = "checkedTest1")
    public void unChecked1Test() {
        assertThat(checkBox.check(false), is(true));
    }
}