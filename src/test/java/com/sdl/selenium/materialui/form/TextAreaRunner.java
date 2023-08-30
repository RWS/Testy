package com.sdl.selenium.materialui.form;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/Textarea.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/form/textarea.feature"
)
public class TextAreaRunner {
}
