package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.button.Button;
import com.sdl.selenium.web.form.TextField;

public class ModalWindow extends Window {

    private TextField emailField = new TextField(this).setLabel("Email:");
    private TextField nameField = new TextField(this).setLabel("User Name:");
    private Button saveButton = new Button(this, "Save");
    private Button closeButton = new Button(this, "Close");

    public ModalWindow() {
        this("Modal title");
    }

    public ModalWindow(String title) {
        super(title);
    }

    public void setValues(String email, String name) {
        emailField.setValue(email);
        nameField.setValue(name);
    }

    public void save() {
        saveButton.click();
    }


    public static void main(String[] args) {
        WebLocatorUtils.getXPathScript(new ModalWindow());
    }
}
