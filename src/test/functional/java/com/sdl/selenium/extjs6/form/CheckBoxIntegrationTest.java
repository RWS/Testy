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
        boxLabel.ready(20);
        Utils.sleep(1000);
    }

    @Test
    public void checkedTest() {
        boxLabel.click();
        assertThat(boxLabel.isSelected(), is(true));
    }

    @Test(dependsOnMethods = "checkedTest")
    public void unCheckedTest() {
        boxLabel.click();
        assertThat(boxLabel.isSelected(), is(false));
    }

    @Test(dependsOnMethods = "unCheckedTest")
    public void checkedTest1() {
        checkBox.click();
        assertThat(checkBox.isSelected(), is(true));
    }

    @Test(dependsOnMethods = "checkedTest1")
    public void unChecked1Test() {
        checkBox.click();
        assertThat(checkBox.isSelected(), is(false));
    }
}