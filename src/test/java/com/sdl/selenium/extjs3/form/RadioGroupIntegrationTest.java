package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RadioGroupIntegrationTest extends TestBase {

    private final Window radioGroupWindow = new Window("RadioGroupsWindow");
    private final RadioGroup enabledRadioGroup = new RadioGroup(radioGroupWindow, "enabledRadio");
    private final RadioGroup disabledRadioGroup = new RadioGroup(radioGroupWindow, "disabledRadio");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_URL);
        Button radioGroupsButton = new Button(null, "RadioGroups");
        radioGroupsButton.click();
    }

    @AfterClass
    public void endTests() {
        radioGroupWindow.close();
    }

    @Test
    public void isDisabledRadioGroup() {
        assertThat(enabledRadioGroup.isEnabled(), is(true));
        assertThat(disabledRadioGroup.isEnabled(), is(false));
    }

    @Test
    public void selectRadioGroup() {
        assertThat(enabledRadioGroup.selectByLabel("Item 2"), is(true));
        assertThat(enabledRadioGroup.selectByLabel("5", SearchType.CONTAINS), is(true));
        assertThat(enabledRadioGroup.selectByValue("3"), is(true));
        assertThat(disabledRadioGroup.selectByLabel("Item 1") , is(false));
        assertThat(disabledRadioGroup.selectByValue("4"), is(false));
    }

    @Test
    public void getLabelNameRadioGroup() {
        assertThat(enabledRadioGroup.getLabelName("1"), equalTo("Item 1"));
        assertThat(disabledRadioGroup.getLabelName("1"), equalTo("Item 1"));
    }
}
