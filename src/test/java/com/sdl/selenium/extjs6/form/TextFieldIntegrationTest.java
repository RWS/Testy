package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextFieldIntegrationTest extends TestBase {

    private final Panel form = new Panel(null, "Form Fields");
    private final TextField field = new TextField(form, "Text field:");

    @BeforeClass
    public void startTest() {
        openEXTJSUrl("#form-fieldtypes", field);
    }

    @Test
    public void textFieldTest() {
        field.setValue("New York");
        assertThat(field.getValue(), equalTo("New York"));
    }
}
