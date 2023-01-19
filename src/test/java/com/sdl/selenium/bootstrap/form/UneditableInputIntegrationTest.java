package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UneditableInputIntegrationTest extends TestBase {

    private Form form = new Form(null, "Form Title");
    private UneditableInput uneditableInput = new UneditableInput(form, "Span:");
    private UneditableInput budgetUneditableInput = new UneditableInput(form, "Budget:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void getSpanText() {
        assertThat(uneditableInput.getText(), equalTo("test"));
    }

    @Test
    public void getBudgetText() {
        assertThat(budgetUneditableInput.getText(), equalTo("123"));
    }
}
