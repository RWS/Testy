package com.sdl.selenium.materialui.menu;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/Menu.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/menu/menu.feature"
)
public class MenuRunner {
}
