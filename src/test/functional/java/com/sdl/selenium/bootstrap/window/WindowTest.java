package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.bootstrap.button.Button;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WindowTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowTest.class);

    private Form form = new Form(null, "Page Object And Page Factory");
    private Button button = new Button(form, "Launch demo modal");
    private Window window = new Window("Modal title");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void openNewTab() {
        button.assertClick();

    }
}
