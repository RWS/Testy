package com.sdl.selenium.extjs6.button;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/SegmentedButton.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/extjs6/button/segmentedButton.feature"
)
public class SegmentedButtonRunner {
}
