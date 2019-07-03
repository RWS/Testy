package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextAriaIntegrationTest extends TestBase {

    private Panel form = new Panel(null, "Form Fields").setClasses("x-panel-default-framed");
    private TextArea area = new TextArea(form, "TextArea:");
    private ComboBox time = new ComboBox(form, "Time Field:");

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-fieldtypes");
        driver.switchTo().frame("examples-iframe");
        area.ready(20);
    }

    @Test
    public void textAreaTest() {
        area.setValue("New York");
        assertThat(area.getValue(), equalTo("New York"));
    }

    @Test
    public void comboBoxTest() {
        time.select("2:45 AM");
        assertThat(time.getValue(), equalTo("2:45 AM"));
    }

}
