package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class InputAppendIntegrationTest extends TestBase {

    private Form form = new Form(null, "Form Title");
    private InputAppend inputAppend = new InputAppend(form, "LPID for Merge:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void setValueInputAppend() {
        assertThat(inputAppend.setValue("1234"), is(true));
        assertThat(inputAppend.getValue(), equalTo("1234"));
    }

    @Test
    public void clickInputAppend() {
        assertThat(inputAppend.append(), is(true));
    }
}
