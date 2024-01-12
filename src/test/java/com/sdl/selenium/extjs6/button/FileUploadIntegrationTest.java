package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FileUploadIntegrationTest extends TestBase {

    private final Panel form = new Panel(null, "Form Fields").setClasses("x-panel-default-framed");
    private final FileUpload fileUpload = new FileUpload(form, "File upload:");

    @BeforeClass
    public void startTests() {
        openEXTJSUrl("#form-fieldtypes", fileUpload);
    }

    @Test
    void fileUploadTest() {
        boolean selected = fileUpload.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx");
        assertThat(selected, is(true));
    }
}
