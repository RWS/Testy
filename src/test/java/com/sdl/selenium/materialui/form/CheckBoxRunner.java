package com.sdl.selenium.materialui.form;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/Checkbox.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/form/checkbox.feature"
)
public class CheckBoxRunner {
}
