package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DisplayFieldIntegrationTest extends TestBase {

    private final Panel form = new Panel(null, "Form Fields");
    private final DisplayField field = new DisplayField(form, "Display field:");

    @BeforeClass
    public void startTest() {
        openEXTJSUrl("#form-fieldtypes", field);
    }

    @Test
    public void displayFieldTest() {
        assertThat(field.getText(), equalTo("Display field value"));
    }
}
