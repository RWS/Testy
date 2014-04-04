package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.DownloadButton;
import com.extjs.selenium.button.UploadButton;
import com.extjs.selenium.panel.Panel;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class UploadButtonTest extends TestBase {
    private static final Logger logger = Logger.getLogger(UploadButtonTest.class);

    Panel simpleFormPanel = new Panel(null, "Simple Form");
    UploadButton uploadButton = new UploadButton(simpleFormPanel, "Browse");
    DownloadButton downloadFileButton = new DownloadButton(simpleFormPanel, "Download File");

    @Test
    public void uploadFile() {
        assertTrue(uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\upload.exe", InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx"));
    }

    @Test
    public void uploadFileWithSpaces() {
        assertTrue(uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\upload.exe", InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text t.docx"));
    }
}
