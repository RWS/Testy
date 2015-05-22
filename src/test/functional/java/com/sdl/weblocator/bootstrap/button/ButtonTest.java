package com.sdl.weblocator.bootstrap.button;

import com.sdl.selenium.bootstrap.button.Button;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.bootstrap.form.SelectPicker;
import com.sdl.selenium.bootstrap.form.UneditableInput;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ButtonTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonTest.class);

    private Form form = new Form(null, "Form Title");
    private Button disableBtn = new Button(form, "DisableBtn");
    private Button disableBtnCls = new Button(form, "DisableBtnCls");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void verifyIsDisabled() {
        assertTrue(disableBtn.isDisabled());
        assertTrue(disableBtnCls.isDisabled());
    }

    @Test
    public void shouldFindElementWithChildNode(){
        UneditableInput input = new UneditableInput().setLabel("Execute");

        Form form = new Form().setTitle("Page Object And Page Factory").setChildNodes(input);

        assertTrue(form.ready());
    }

    @Test
    public void shouldFindElementWithChildNodes(){
        UneditableInput input = new UneditableInput().setLabel("Execute");
        SelectPicker picker = new SelectPicker().setLabel("Execute");

        Form form = new Form().setTitle("Page Object And Page Factory").setChildNodes(input, picker);

        assertTrue(form.ready());
    }

    @Test
    public void shouldFindElementWithChildNodeThatHasContainerSelf(){
        UneditableInput input = new UneditableInput().setLabel("Execute");

        Form form = new Form().setTitle("Page Object And Page Factory").setChildNodes(input);
        input.setContainer(form);

        assertTrue(form.ready());
    }
}
