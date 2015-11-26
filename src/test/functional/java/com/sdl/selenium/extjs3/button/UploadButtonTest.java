package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UploadButtonTest extends TestBase {

    private Panel simpleFormPanel = new Panel(null, "Simple Form");
    private UploadButton uploadButton = new UploadButton(simpleFormPanel, "Browse");

    @Test
    public void uploadFile() {
        assertThat(uploadButton.upload(InputData.UPLOAD_EXE_PATH, InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx"), is(true));
    }

    @Test
    public void uploadFileWithSpaces() {
        assertThat(uploadButton.upload(InputData.UPLOAD_EXE_PATH, InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text t.docx"), is(true));
    }
}
