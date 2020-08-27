package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RadioIntegrationTest extends TestBase {

    private Panel radioGroupPanel = new Panel(null, "Radio Group Example").setClasses("x-panel-default-framed");
    private Radio redRadio = new Radio(radioGroupPanel, "Red");
    private Radio blueRadio = new Radio(radioGroupPanel, "Blue");
    private Radio greenRadio = new Radio(radioGroupPanel, "Green");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-radiogroup");
        driver.switchTo().frame("examples-iframe");
        redRadio.setVersion(version);
        redRadio.ready(Duration.ofSeconds(20));
    }

    @Test
    public void selectRadioGroup() {
        assertThat(redRadio.isSelected(), is(true));
        assertThat(blueRadio.check(), is(true));
        assertThat(redRadio.isSelected(), is(false));
        assertThat(blueRadio.isSelected(), is(true));
        assertThat(greenRadio.check(), is(true));
        assertThat(blueRadio.isSelected(), is(false));
    }
}
