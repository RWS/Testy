package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FileUploadIntegrationTest extends TestBase {

    private Panel form = new Panel(null, "Form Fields").setClasses("x-panel-default-framed");
    private FileUpload fileUpload = new FileUpload(form, "File upload:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-fieldtypes");
        driver.switchTo().frame("examples-iframe");
        fileUpload.ready(Duration.ofSeconds(20));
        Utils.sleep(2000);
    }

    @Test
    void fileUploadTest() {
        boolean selected = fileUpload.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx");
        assertThat(selected, is(true));
    }
}
