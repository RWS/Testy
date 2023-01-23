package com.sdl.selenium.materialui.button;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class ButtonSteps extends TestBase {

    @Given("I open Materialui app and add {string} path")
    public void iOpenMaterialuiApp(String path) {
        driver.get("https://mui.com/material-ui/react-button/" + path);
        Utils.sleep(1);
    }

   @And("I verify if button is present")
   public void IVerifyIfButtonIsPresent() {
       Button button = new Button(null, "");
       assertThat(button.isPresent(), is(true));
   }
}
