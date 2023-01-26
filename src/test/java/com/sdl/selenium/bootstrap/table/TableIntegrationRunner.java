package com.sdl.selenium.bootstrap.table;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "json:target/TableIntegration.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/bootstrap/table.feature"
)
public class TableIntegrationRunner {
}
