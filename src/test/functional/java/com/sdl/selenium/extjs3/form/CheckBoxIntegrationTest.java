package com.sdl.selenium.extjs3.form;

import com.sdl.demo.extjs3.form.SimpleForm;
import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.WebLocatorUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CheckBoxIntegrationTest extends TestBase {
    private SimpleForm simpleForm = new SimpleForm();

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_URL);
    }

    @Test
    public void rightCheckBox() {
        simpleForm.rightCheckBox.click();
        assertThat(simpleForm.rightCheckBox.isChecked(), is(true));
    }

    @Test
    public void leftCheckBox() {
        simpleForm.leftCheckBox.click();
        assertThat(simpleForm.leftCheckBox.isChecked(), is(true));
    }

    public static void main(String[] args) {
        WebLocatorUtils.getXPathScript(new SimpleForm());
    }

}
