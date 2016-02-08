package com.sdl.demo.extjs3.form;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.button.DownloadButton;
import com.sdl.selenium.extjs3.button.UploadButton;
import com.sdl.selenium.extjs3.form.Checkbox;
import com.sdl.selenium.extjs3.panel.Panel;

public class SimpleForm extends Panel {

    public SimpleForm(String title) {
        super(title);
    }

    public SimpleForm() {
        this("Simple Form");
    }

    public UploadButton uploadButton = new UploadButton(this, "Browse");

    public DownloadButton downloadButton = new DownloadButton(this, "Download");
    public DownloadButton downloadWithSpacesButton = new DownloadButton(this, "Download with spaces");
    public DownloadButton downloadFileButton = new DownloadButton(this, "Download File");

    public Checkbox rightCheckBox = new Checkbox(this).setLabel("CatRight").setLabelPosition("//preceding-sibling::");
    public Checkbox leftCheckBox = new Checkbox(this).setLabel("CatLeft:");

    public Button cancelButton = new Button(this, "Cancel");
}
