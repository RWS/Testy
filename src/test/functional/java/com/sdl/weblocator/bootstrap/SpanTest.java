package com.sdl.weblocator.bootstrap;

import com.sdl.bootstrap.form.Form;
import com.sdl.bootstrap.form.Span;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class SpanTest extends TestBase {
    private static final Logger logger = Logger.getLogger(SpanTest.class);

    Form form = new Form(null, "Form Title");
    Span span = new Span(form, "Span:");
    Span budgetSpan = new Span(form, "Budget:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void getSpanText() {
        assertTrue("test".equals(span.getHtmlText()));
    }

    @Test
    public void getBudgetText() {
        assertTrue("123".equals(budgetSpan.getHtmlText()));
    }
}
