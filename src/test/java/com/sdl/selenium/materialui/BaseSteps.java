package com.sdl.selenium.materialui;

import io.cucumber.java.en.Given;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BaseSteps extends Base {

    @Given("I open MaterialUI and add {string} path")
    public void iOpenMaterialUIApp(String path) {
        driver.get("https://mui.com/material-ui/" + path);
    }
}
