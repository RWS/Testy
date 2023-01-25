package com.sdl.selenium.materialui.form;

import com.sdl.selenium.TestBase;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComboBoxSteps extends TestBase {

   @Given("I open Materialui app")
   public void iOpenMaterialuiApp() {
       driver.get("");
   }
}
