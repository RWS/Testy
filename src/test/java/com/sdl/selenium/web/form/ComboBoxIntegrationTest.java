package com.sdl.selenium.web.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ComboBoxIntegrationTest extends TestBase {

    private WebLocator comboBoxDiv = new WebLocator().setClasses("combobox");
    private ComboBox comboBox = new ComboBox(comboBoxDiv);

    @BeforeClass
    public void startTest() {
        driver.get(InputData.WEB_LOCATOR_URL);
    }

    @Test
    public void testEditorType() {
        assertThat(comboBox.select("Opel"), is(true));
        assertThat(comboBox.getValue(), equalTo("Opel"));
    }
}
