package com.sdl.demo.extjs3.form;

import com.sdl.selenium.extjs3.form.Checkbox;
import com.sdl.selenium.extjs3.panel.Panel;

public class SimpleForm extends Panel {

    public SimpleForm(String title) {
        super(title);
    }

    public SimpleForm() {
        this("Simple Form");
    }

    public Checkbox rightCheckBox = new Checkbox(this).setLabel("CatRight").setLabelPosition("//preceding-sibling::");
    public Checkbox leftCheckBox = new Checkbox(this).setLabel("CatLeft:");
}
