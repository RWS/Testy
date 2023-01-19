package com.sdl.selenium.extjs3.button;

import com.sdl.demo.extjs3.form.SimpleForm;
import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UploadButtonIntegrationTest extends TestBase {

    private SimpleForm simpleForm = new SimpleForm();

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_URL);
    }

    @Test
    public void uploadFile() {
        assertThat(simpleForm.uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx"), is(true));
    }

    @Test
    public void uploadFileWithSpaces() {
        assertThat(simpleForm.uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text t.docx"), is(true));
    }
}
