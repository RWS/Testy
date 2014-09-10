package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.UploadButton;
import com.extjs.selenium.panel.Panel;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class UploadButtonTest extends TestBase {

    private Panel simpleFormPanel = new Panel(null, "Simple Form");
    private UploadButton uploadButton = new UploadButton(simpleFormPanel, "Browse");

    @Test
    public void uploadFile() {
        assertTrue(uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\upload.exe", InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx"));
    }

    @Test
    public void uploadFileWithSpaces() {
        assertTrue(uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\upload.exe", InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text t.docx"));
    }
}
