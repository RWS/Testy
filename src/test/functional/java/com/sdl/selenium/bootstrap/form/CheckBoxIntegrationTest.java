package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CheckBoxIntegrationTest extends TestBase {

    private Form form = new Form(null, "Form Title");
    private CheckBox checkBox = new CheckBox(form);
    private CheckBox withEnterWebLocator = new CheckBox(form).setLabel("Label with Enter.", SearchType.CHILD_NODE).setLabelPosition("//");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void check() {
        assertThat(checkBox.click(), is(true));
        assertThat(checkBox.isSelected(), is(true));
    }

    @Test
    public void clickWith() {
        assertThat(withEnterWebLocator.click(), is(true));
    }
}
