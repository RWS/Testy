package com.sdl.selenium.materialui.checkbox;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "json:target/Checkbox.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/checkbox.feature"
)
public class CheckboxRunner {
}
