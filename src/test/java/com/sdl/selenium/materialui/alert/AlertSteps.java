package com.sdl.selenium.materialui.alert;

import com.sdl.selenium.materialui.Base;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class AlertSteps extends Base {


   @And("in MaterialUI I verify if Alert with {string} message is present")
   public void InMaterialUIIVerifyIfAlertIsPresent(String message) {
       Alert alert = new Alert(message);
       assertThat(alert.isPresent(), is(true));
   }
}
