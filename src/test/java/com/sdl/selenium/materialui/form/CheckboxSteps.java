package com.sdl.selenium.materialui.form;

import com.sdl.selenium.materialui.Base;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class CheckboxSteps extends Base {

    @And("I verify if checkbox is present")
    public void IVerifyIfCheckboxIsPresent() {
        CheckBox end = new CheckBox(getContainer(), "End");
        boolean present = end.ready(Duration.ofSeconds(1));
        assertThat(present, is(true));
        CheckBox start = new CheckBox(getContainer(), "Start");
        present = start.ready(Duration.ofSeconds(1));
        assertThat(present, is(true));
    }

    @And("I verify if {string} checkbox is {check}")
    public void iVerifyIfCheckboxIsChecked(String checkboxName, Boolean check) {
        CheckBox end = new CheckBox(getContainer(), checkboxName);
        boolean checked = end.isChecked();
        assertThat(checked, is(check));
    }

    @And("I {check} {string} checkbox")
    public void iCheckCheckbox(Boolean check, String checkboxName) {
        CheckBox end = new CheckBox(getContainer(), checkboxName);
        end.check(check);
    }
}
