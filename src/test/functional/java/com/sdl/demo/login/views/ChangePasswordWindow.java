package com.sdl.demo.login.views;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.bootstrap.window.Window;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.Button;
import com.sdl.selenium.web.form.TextField;
import com.sdl.selenium.web.utils.Utils;

public class ChangePasswordWindow extends Window {

    public ChangePasswordWindow() {
        this("Change Password");
    }

    public ChangePasswordWindow(String title) {
        super(title);
    }

    private TextField currentPassField = new TextField(this).withLabel("Current Password");
    private TextField newPassField = new TextField(this).withLabel("New Password");
    private TextField confirmPassField = new TextField(this).withLabel("Repeat Password");

    private Button saveButton = new Button(this).withText("Save");
    private Button closeButton = new Button(this).withText("Close");

    private WebLocator statusMsgElement = new WebLocator(this).withClasses("status-msg");

    public void changePassword(String currentPass, String newPass, String confirmPass) {
        currentPassField.setValue(currentPass);
        newPassField.setValue(newPass);
        confirmPassField.setValue(confirmPass);
        saveButton.assertClick();
    }

    public void close() {
        closeButton.assertClick();
    }

    public String getStatusMessage() {
        Utils.sleep(200);
        return statusMsgElement.getText();
    }

    public static void main(String[] args) {
        WebLocatorUtils.getXPathScript(new ChangePasswordWindow());
    }
}
