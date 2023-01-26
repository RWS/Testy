package com.sdl.selenium.materialui.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.WebLocator;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@Slf4j
public class ComboBoxSteps extends TestBase {

    private final WebLocator container = new WebLocator().setId("main-content");

    @And("I verify if combobox is present")
    public void IVerifyIfComboBoxIsPresent() {
        ComboBox combobox = new ComboBox(container, "Age");
        boolean present = combobox.ready(Duration.ofSeconds(1));
        assertThat(present, is(true));
    }

    @And("I select {string} in {string} combobox")
    public void iSelectInComboBox(String value, String label) {
        ComboBox combobox = new ComboBox(container, label);
        combobox.select(value);
        String currentValue = combobox.getValue();
        assertThat(currentValue, equalTo(currentValue));
    }

    @And("I verify if {string} combobox have values: {list}")
    public void iVerifyIfComboBoxHaveValues(String label, List<String> values) {
        ComboBox combobox = new ComboBox(container, label);
        List<String> actualValues = combobox.getAllValues();
        assertThatList("Actual values: ", actualValues, containsInAnyOrder(values.toArray()));
    }
}
