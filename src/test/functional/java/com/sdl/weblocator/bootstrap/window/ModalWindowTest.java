package com.sdl.weblocator.bootstrap.window;

import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.web.button.Button;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ModalWindowTest extends TestBase {

    private Form form = new Form("Page Object And Page Factory");
    private Button button = new Button().setContainer(form).setText("Launch demo modal");

    private ModalWindow window = new ModalWindow();

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void openAndInteractWithModalWindow() {
        button.assertClick();

        window.setValues("nick@example.com", "Matei");

        //window.save();
    }
}
