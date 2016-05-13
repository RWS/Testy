package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.web.button.Button;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ModalWindowIntegrationTest extends TestBase {

    private Form form = new Form("Page Object And Page Factory");
    private Button button = new Button().withContainer(form).withText("Launch demo modal");

    private ModalWindow window = new ModalWindow();

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void openAndInteractWithModalWindow() {
        button.click();

        window.setValues("selenium@example.com", "Testy");

        //window.save();
    }
}
