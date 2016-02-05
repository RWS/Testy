package com.sdl.selenium.extjs3.form;

import com.sdl.demo.extjs3.form.SimpleForm;
import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CheckBoxIntegrationTest extends TestBase {
    private SimpleForm form = new SimpleForm();

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_URL);
    }

    @Test
    public void rightCheckBox() {
        form.rightCheckBox.click();
        assertTrue(form.rightCheckBox.isSelected());
    }

    @Test
    public void leftCheckBox() {
        form.leftCheckBox.click();
        assertTrue(form.leftCheckBox.isSelected());
    }

    /*public static void main(String[] args) {
        CheckBoxIntegrationTest test = new CheckBoxIntegrationTest();
        WebLocatorUtils.getXPathScript(test.form);
    }*/

}
