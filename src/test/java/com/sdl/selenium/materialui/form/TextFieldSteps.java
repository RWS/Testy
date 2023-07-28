package com.sdl.selenium.materialui.form;

import com.sdl.selenium.StepDetails;
import com.sdl.selenium.materialui.Base;
import com.sdl.selenium.web.form.Field;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class TextFieldSteps extends Base {
    private final TextField textField = new TextField(getContainer(), "Required");

    @And("I verify if textfield is present")
    public void IVerifyIfTextfieldIsPresent() {
        assertThat(textField.isPresent(), is(true));
    }

    @And("in MaterialUI I set Name {string}")
    public void inMaterialUIISetName(String name) {
        textField.setValue(name);
    }

    @And("in MaterialUI I verify if (Name)(Age) has value {string}")
    public void inMaterialUIIVerifyIfNameHasValue(String value) {
        String stepName = StepDetails.stepName;
        Field field = null;
        if (stepName.contains("Name")) {
            field = textField;
        } else if (stepName.contains("Age")) {
            field = new ComboBox(getContainer(), "Age");
        }
        String actual = field.getValue();
        assertThat("Actual value", actual, equalTo(value));
    }
}
