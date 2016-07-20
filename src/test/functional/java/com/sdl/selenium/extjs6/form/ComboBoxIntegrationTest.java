package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ComboBoxIntegrationTest extends TestBase {

    private ComboBox comboBox = new ComboBox(null, "Select State:");

    @BeforeClass
    public void startTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#simple-combo");
        comboBox.ready(20);
        Utils.sleep(2000);
    }

    @Test
    public void comboBoxTest() {
        assertTrue(comboBox.select("New York"));
        assertEquals(comboBox.getValue(), "New York");
    }

}
