package com.sdl.selenium.generate;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/Generate.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/generate.feature"
)
public class GenerateRunner {
}
