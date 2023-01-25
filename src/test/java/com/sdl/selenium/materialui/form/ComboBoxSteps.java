package com.sdl.selenium.materialui.form;

import com.sdl.selenium.TestBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class ComboBoxSteps extends TestBase {

   @Given("I open Materialui app")
   public void iOpenMaterialuiApp() {
       driver.get("");
   }

   @And("I verify if combobox is present")
   public void IVerifyIfComboboxIsPresent() {
       ComboBox combobox = new ComboBox(null, "");
       assertThat(combobox.isPresent(), is(true));
   }
}
