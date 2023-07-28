package com.sdl.selenium.materialui.button;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/Button.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/button.feature"
)
public class ButtonRunner {
}
