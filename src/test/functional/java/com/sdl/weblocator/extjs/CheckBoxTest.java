package com.sdl.weblocator.extjs;

import com.extjs.selenium.form.Checkbox;
import com.extjs.selenium.panel.Panel;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CheckBoxTest extends TestBase {
    private static final Logger logger = Logger.getLogger(CheckBoxTest.class);

    Panel simpleFormPanel = new Panel(null, "Simple Form");
    Checkbox rightCheckBox = new Checkbox("CatRight", simpleFormPanel);
    Checkbox leftCheckBox = new Checkbox("CatLeft:", simpleFormPanel);

    @Test
    public void rightCheckBox() {
        rightCheckBox.click();
        assertTrue(rightCheckBox.isSelected());
    }

    @Test
    public void leftCheckBox() {
        leftCheckBox.click();
        assertTrue(leftCheckBox.isSelected());
    }
}
