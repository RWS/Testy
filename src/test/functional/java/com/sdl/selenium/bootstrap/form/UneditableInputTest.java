package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class UneditableInputTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UneditableInputTest.class);

    Form form = new Form(null, "Form Title");
    UneditableInput uneditableInput = new UneditableInput(form, "Span:");
    UneditableInput budgetUneditableInput = new UneditableInput(form, "Budget:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void getSpanText() {
        assertTrue("test".equals(uneditableInput.getHtmlText()));
    }

    @Test
    public void getBudgetText() {
        assertTrue("123".equals(budgetUneditableInput.getHtmlText()));
    }
}
