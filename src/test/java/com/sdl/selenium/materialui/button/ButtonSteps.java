package com.sdl.selenium.materialui.button;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class ButtonSteps extends TestBase {

    private final WebLocator container = new WebLocator().setId("main-content");

    @Given("I open Materialui app and add {string} path")
    public void iOpenMaterialuiApp(String path) {
        driver.get("https://mui.com/material-ui/" + path);
        Utils.sleep(1);
    }

    @And("I verify if button is present")
    public void IVerifyIfButtonIsPresent() {
        Button button = new Button(container, "Contained");
        boolean present = button.ready(Duration.ofSeconds(1));
        assertThat(present, is(true));
    }

    @And("I verify if {string} button is {status}")
    public void iVerifyIfButtonIsDisabled(String buttonName, Boolean status) {
        Button button = new Button(container, buttonName).setResultIdx(1);
        boolean enabled = button.isEnabled();
        assertThat(enabled, is(status));
    }
}
