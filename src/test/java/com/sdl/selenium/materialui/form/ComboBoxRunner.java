package com.sdl.selenium.materialui.form;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "json:target/ComboBox.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/combobox.feature"
)
public class ComboBoxRunner {
}
