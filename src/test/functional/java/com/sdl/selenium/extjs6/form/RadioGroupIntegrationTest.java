package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RadioGroupIntegrationTest extends TestBase {

    private Panel radioGroupPanel = new Panel(null, "Radio Group Example").setClasses("x-panel-default-framed");
    private RadioGroup radioGroup = new RadioGroup(radioGroupPanel, "Auto Layout:", SearchType.DEEP_CHILD_NODE_OR_SELF);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-radiogroup");
        driver.switchTo().frame("examples-iframe");
        radioGroup.ready(20);
    }

    @Test
    public void selectRadioGroup() {
        assertTrue(radioGroup.selectByLabel("Item 2"));
        assertTrue(radioGroup.isSelectedByLabel("Item 2"));
        assertTrue(radioGroup.selectByLabel("5", SearchType.CONTAINS));
        assertTrue(radioGroup.isSelectedByLabel("Item 5"));
        assertTrue(radioGroup.selectByLabel("Item 4"));
        assertTrue(radioGroup.isSelectedByLabel("Item 4"));
        assertTrue(radioGroup.selectByLabel("Item 1"));
        assertTrue(radioGroup.isSelectedByLabel("Item 1"));
    }

    @Test
    public void getLabelNameRadioGroup() {
        assertEquals(radioGroup.getLabelName("1"), "Item 1");
        assertEquals(radioGroup.getLabelName("1"), "Item 1");
    }
}
