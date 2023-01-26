package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ComboBoxIntegrationTest extends TestBase {

    private Window comboBoxWindow = new Window("ComboBoxWindow");
    private ComboBox comboBox = new ComboBox("comboBox", comboBoxWindow);

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_URL);
        showComponent("ComboBox");
    }

    @AfterClass
    public void endTests() {
        comboBoxWindow.close();
    }

    @Test
    public void testEditorType() {
        assertThat(comboBox.select("Romanian"), is(true));
        assertThat(comboBox.getValue(), equalTo("Romanian"));
    }

    @Test
    public void searchTypeSelect() {
        assertThat(comboBox.select("Bulgar", SearchType.STARTS_WITH), is(true));
        assertThat(comboBox.getValue(), equalTo("Bulgarian"));

        assertThat(comboBox.select("United States", SearchType.CONTAINS), is(true));
        assertThat(comboBox.getValue(), equalTo("English(United States)"));
    }
}
