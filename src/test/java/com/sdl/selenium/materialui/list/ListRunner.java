package com.sdl.selenium.materialui.list;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/List.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/list/list.feature"
)
public class ListRunner {
}
