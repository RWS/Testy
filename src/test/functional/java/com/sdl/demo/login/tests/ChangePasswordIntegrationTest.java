package com.sdl.demo.login.tests;

import com.sdl.demo.login.views.ChangePasswordWindow;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.button.Button;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ChangePasswordIntegrationTest extends TestBase {

    private Button preferencesButton = new Button().setText("Preferences");

    private ChangePasswordWindow changePasswordWindow = new ChangePasswordWindow();

//    @BeforeClass
//    public void startTests() {
//        driver.get(InputData.BOOTSTRAP_URL);
//    }
//
//    @Test
    public void whenPreviewPasswordIsWrongIGetCorrectErrorMessage() {
        preferencesButton.click();

        changePasswordWindow.changePassword("pass1", "pass2", "pass2");

        String message = changePasswordWindow.getStatusMessage();

        changePasswordWindow.close();

        assertThat(message, is("Your preview password is incorrect!"));
        //assertThat(message, is("Your password has been successfully changed."));
    }

}
