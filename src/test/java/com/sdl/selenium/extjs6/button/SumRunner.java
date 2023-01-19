package com.sdl.selenium.extjs6.button;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber-report.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features"
)
public class SumRunner {
}
