package com.sdl.selenium.extjs6.grid;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/GridIntegration.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/extjs6/grid.feature"
)
public class GridIntegrationRunner {
}
