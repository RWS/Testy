package com.sdl.selenium.materialui.checkbox;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.WebLocator;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class CheckboxSteps extends TestBase {
    private final WebLocator container = new WebLocator().setId("main-content");

    @And("I verify if checkbox is present")
    public void IVerifyIfCheckboxIsPresent() {
        Checkbox end = new Checkbox(container, "End");
        boolean present = end.ready(Duration.ofSeconds(1));
        assertThat(present, is(true));
        Checkbox start = new Checkbox(container, "Start");
        present = start.ready(Duration.ofSeconds(1));
        assertThat(present, is(true));
    }

    @And("I verify if {string} checkbox is {check}")
    public void iVerifyIfCheckboxIsChecked(String checkboxName, Boolean check) {
        Checkbox end = new Checkbox(container, checkboxName);
        boolean checked = end.isChecked();
        assertThat(checked, is(check));
    }

    @And("I {check} {string} checkbox")
    public void iCheckCheckbox(Boolean check, String checkboxName) {
        Checkbox end = new Checkbox(container, checkboxName);
        end.check(check);
    }
}
