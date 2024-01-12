package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CheckBoxIntegrationTest extends TestBase {

    private final Panel panel = new Panel(null, "Form Fields");
    private final CheckBox checkBox = new CheckBox(panel, "Checkbox:");
    private final CheckBox boxLabel = new CheckBox("box label", panel);

    @BeforeClass
    public void startTest() {
        checkBox.setVersion(version);
        boxLabel.setVersion(version);
        openEXTJSUrl("#form-fieldtypes", checkBox);
        checkBox.getValue();
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