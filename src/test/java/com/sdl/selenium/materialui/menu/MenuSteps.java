package com.sdl.selenium.materialui.menu;

import com.sdl.selenium.materialui.Base;
import com.sdl.selenium.materialui.button.Button;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuSteps extends Base {

    @And("in MaterialUI I click on {string} option")
    public void inMaterialUIIClickOnOption(String option) {
        Button dashboardButton = new Button(getContainer(), "Dashboard");
        dashboardButton.clickOnMenu(option);
    }
}
