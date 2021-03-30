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

    private final TextField currentPassField = new TextField(this).setLabel("Current Password");
    private final TextField newPassField = new TextField(this).setLabel("New Password");
    private final TextField confirmPassField = new TextField(this).setLabel("Repeat Password");

    private final Button saveButton = new Button(this).setText("Save");
    private final Button closeButton = new Button(this).setText("Close");

    private final WebLocator statusMsgElement = new WebLocator(this).setClasses("status-msg");

    public void changePassword(String currentPass, String newPass, String confirmPass) {
        currentPassField.setValue(currentPass);
        newPassField.setValue(newPass);
        confirmPassField.setValue(confirmPass);
        saveButton.click();
    }

    public void close() {
        closeButton.click();
    }

    public String getStatusMessage() {
        Utils.sleep(200);
        return statusMsgElement.getText();
    }

    public static void main(String[] args) {
        WebLocatorUtils.getXPathScript(new ChangePasswordWindow());
    }
}
