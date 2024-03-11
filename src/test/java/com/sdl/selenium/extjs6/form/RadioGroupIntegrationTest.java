package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RadioGroupIntegrationTest extends TestBase {

    private final Panel radioGroupPanel = new Panel(null, "Radio Group Example").setClasses("x-panel-default-framed");
    private final RadioGroup radioGroup = new RadioGroup(radioGroupPanel, "Auto Layout:", SearchType.DEEP_CHILD_NODE_OR_SELF);

    @BeforeClass
    public void startTests() {
        radioGroup.setVersion(version);
        openEXTJSUrl("#form-radiogroup", radioGroup);
    }

    @Test
    public void selectRadioGroup() {
        assertThat(radioGroup.isSelectedByLabel("Item 1"), is(false));
        assertThat(radioGroup.selectByLabel("Item 2"), is(true));
        assertThat(radioGroup.isSelectedByLabel("Item 2"), is(true));
        assertThat(radioGroup.selectByLabel("5", SearchType.CONTAINS), is(true));
        assertThat(radioGroup.isSelectedByLabel("Item 5"), is(true));
        assertThat(radioGroup.selectByLabel("Item 4"), is(true));
        assertThat(radioGroup.isSelectedByLabel("Item 4"), is(true));
        assertThat(radioGroup.selectByLabel("Item 1"), is(true));
        assertThat(radioGroup.isSelectedByLabel("Item 1"), is(true));
    }

    @Test
    public void getLabelNameRadioGroup() {
        assertThat(radioGroup.getLabelName("1"), equalTo("Item 1"));
        assertThat(radioGroup.getLabelName("2"), equalTo("Item 2"));
    }
}
