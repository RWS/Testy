package com.sdl.selenium.materialui.alert;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/Alert.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/alert/alert.feature"
)
public class AlertRunner {
}
