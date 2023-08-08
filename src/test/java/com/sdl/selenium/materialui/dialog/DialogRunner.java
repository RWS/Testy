package com.sdl.selenium.materialui.dialog;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/Dialog.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/materialui/dialog/dialog.feature"
)
public class DialogRunner {
}
