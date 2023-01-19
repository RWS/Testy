package com.sdl.selenium.extjs3;

import com.sdl.demo.extjs3.form.SimpleForm;
import com.sdl.selenium.TestBase;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FireEventWithJSIntegrationTest extends TestBase {

    private SimpleForm simpleForm = new SimpleForm();

    @Test
    public void fireEvent() {
        simpleForm.cancelButton.focus();
        assertThat(simpleForm.cancelButton.blur(), is(true));
        assertThat(simpleForm.cancelButton.mouseOver(), is(true));
        assertThat(simpleForm.cancelButton.doubleClickAt(), is(true));
    }
}
