package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.utils.RetryUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextAriaIntegrationTest extends TestBase {

    private final Panel form = new Panel(null, "Form Fields").setClasses("x-panel-default-framed");
    private final TextArea area = new TextArea(form, "TextArea:");
    private final ComboBox time = new ComboBox(form, "Time Field:");

    @BeforeClass
    public void startTest() {
        openEXTJSUrl("#form-fieldtypes", area);
    }

    @Test
    public void textAreaTest() {
        area.setValue("New York");
        assertThat(area.getValue(), equalTo("New York"));
    }

    @Test
    public void comboBoxTest() {
        RetryUtils.retry(2, () -> time.select("2:45 AM"));
        assertThat(time.getValue(), equalTo("2:45 AM"));
    }
}
