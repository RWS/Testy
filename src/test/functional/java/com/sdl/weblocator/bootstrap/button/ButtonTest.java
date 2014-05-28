package com.sdl.weblocator.bootstrap.button;

import com.sdl.bootstrap.button.Button;
import com.sdl.bootstrap.form.Form;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ButtonTest extends TestBase {
    private static final Logger logger = Logger.getLogger(ButtonTest.class);

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
}
