package com.sdl.selenium.extjs.form;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.form.RadioGroup;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RadioGroupTest extends TestBase {

    private Window radioGroupWindow = new Window("RadioGroupsWindow");
    private RadioGroup enabledRadioGroup = new RadioGroup(radioGroupWindow, "enabledRadio");
    private RadioGroup disabledRadioGroup = new RadioGroup(radioGroupWindow, "disabledRadio");

    @BeforeClass
    public void startTests() {
        Button radioGroupsButton = new Button(null, "RadioGroups");
        radioGroupsButton.click();
    }

    @AfterClass
    public void endTests() {
        radioGroupWindow.close();
    }

    @Test
    public void isDisabledRadioGroup() {
        assertFalse(enabledRadioGroup.isDisabled());
        assertTrue(disabledRadioGroup.isDisabled());
    }

    @Test
    public void selectRadioGroup() {
        assertTrue(enabledRadioGroup.selectByLabel("Item 2"));
        assertTrue(enabledRadioGroup.selectByLabel("5", SearchType.CONTAINS));
        assertTrue(enabledRadioGroup.selectByValue("3"));
        assertFalse(disabledRadioGroup.selectByLabel("Item 1"));
        assertFalse(disabledRadioGroup.selectByValue("4"));
    }

    @Test
    public void getLabelNameRadioGroup() {
        assertEquals(enabledRadioGroup.getLabelName("1"), "Item 1");
        assertEquals(disabledRadioGroup.getLabelName("1"), "Item 1");
    }
}
