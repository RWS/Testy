package com.sdl.selenium.materialui.form;

import com.sdl.selenium.materialui.Base;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class TextAreaSteps extends Base {

    private final TextArea textarea = new TextArea(getContainer(), "Multiline");

    @And("in MaterialUI I verify if TextArea is present")
    public void InMaterialUIIVerifyIfTextAreaIsPresent() {
        assertThat(textarea.ready(), is(true));
    }

    @And("in MaterialUI I set Multiline {string}")
    public void inMaterialUIISetName(String name) {
        textarea.setValue(name);
    }

    @And("in MaterialUI I verify if Multiline has value {string}")
    public void inMaterialUIIVerifyIfNameHasValue(String value) {
        String actual = textarea.getValue();
        assertThat("Actual value", actual, equalTo(value));
    }
}
