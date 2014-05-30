package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.panel.Panel;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class FireEventWithJSTest extends TestBase {
    private static final Logger logger = Logger.getLogger(FireEventWithJSTest.class);
    private Panel simpleForm = new Panel("Simple Form");
    private Button cancelButton = new Button(simpleForm, "Cancel");


    @Test
    public void fireEvent() {
        cancelButton.focus();
        assertTrue(cancelButton.blur());
        assertTrue(cancelButton.mouseOver());
        assertTrue(cancelButton.doubleClickAt());
    }
}
