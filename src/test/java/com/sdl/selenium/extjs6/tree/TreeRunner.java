package com.sdl.selenium.extjs6.tree;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/Tree.json"},
        glue = {"com.sdl.selenium"},
        features = "classpath:features/extjs6/tree/tree.feature"
)
public class TreeRunner {
}
