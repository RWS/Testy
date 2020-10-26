package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.bootstrap.form.SelectPicker;
import com.sdl.selenium.bootstrap.form.UneditableInput;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ButtonIntegrationTest extends TestBase {

    private Form form = new Form(null, "Form Title");
    private Button disableBtn = new Button(form, "DisableBtn");
    private Button disableBtnCls = new Button(form, "DisableBtnCls");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void verifyIsDisabled() {
        assertThat(disableBtn.isDisabled(), is(true));
        assertThat(disableBtnCls.isDisabled(), is(true));
        assertThat(disableBtnCls.currentElement.getTagName(), equalTo("button"));
        assertThat(disableBtnCls.currentElement.getLocation(), is(new Point(166, 519)));
        assertThat(disableBtnCls.currentElement.getSize(), is(new Dimension(114, 30)));
    }

    @Test
    public void shouldFindElementWithChildNode() {
        UneditableInput input = new UneditableInput().setLabel("Execute");

        Form form = new Form().setTitle("Page Object And Page Factory").setChildNodes(input);

        assertThat(form.ready(), is(true));
    }

    @Test
    public void shouldFindElementWithChildNodes() {
        UneditableInput input = new UneditableInput().setLabel("Execute");
        SelectPicker picker = new SelectPicker().setLabel("Execute");

        Form form = new Form().setTitle("Page Object And Page Factory").setChildNodes(input, picker);

        assertThat(form.ready(), is(true));
    }

    @Test
    public void shouldFindElementWithChildNodeThatHasContainerSelf() {
        UneditableInput input = new UneditableInput().setLabel("Execute");

        Form form = new Form().setTitle("Page Object And Page Factory").setChildNodes(input);
        input.setContainer(form);

        assertThat(form.ready(), is(true));
    }
}