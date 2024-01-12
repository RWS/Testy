package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FieldSetIntegrationTest extends TestBase {

    private final Panel form = new Panel(null, "Employee Information");
    private final FieldSet field = new FieldSet(form, "Details");
    private final FieldContainer fieldContainer = new FieldContainer(form, "Availability:");
    private final DateField dateField1 = new DateField(fieldContainer).setResultIdx(1);
    private final DateField dateField2 = new DateField(fieldContainer).setResultIdx(2);

    @BeforeClass
    public void startTest() {
        openEXTJSUrl("#form-fieldcontainer", field);
    }

    @Test
    public void fieldSetTest() {
        assertThat(field.isCollapsed(), is(false));
        assertThat(field.collapse(), is(true));
        assertThat(field.isCollapsed(), is(true));
        assertThat(field.expand(), is(true));
        assertThat(field.isCollapsed(), is(false));
    }

    @Test
    public void fieldContainerTest() {
        assertThat(dateField1.isPresent(), is(true));
        assertThat(dateField2.isPresent(), is(true));
    }
}
