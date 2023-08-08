package com.sdl.selenium.materialui.dialog;

import com.sdl.selenium.materialui.Base;
import com.sdl.selenium.materialui.button.Button;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class DialogSteps extends Base {

    private final Button button = new Button(getContainer(), "Open alert dialog");

    @And("I verify if Dialog is present")
    public void IVerifyIfDialogIsPresent() {
        button.click();
        Dialog dialog = new Dialog("Use Google's location service?");
        assertThat(dialog.isPresent(), is(true));
    }

    @And("in MaterialUI I see Dialog with {string} title, {string} message and click on {string} button")
    public void inMaterialUIISeeDialogWithTitleMessageAndClickOnButton(String title, String message, String buttonName) {
        button.click();
        Dialog dialog = new Dialog(title, message);
        dialog.press(buttonName);
    }
}
