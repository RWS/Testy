package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.DownloadButton;
import com.extjs.selenium.panel.Panel;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DownloadTest extends TestBase {
    private static final Logger logger = Logger.getLogger(DownloadTest.class);

    Panel simpleFormPanel = new Panel(null, "Simple Form");
    DownloadButton downloadButton = new DownloadButton(simpleFormPanel, "Download");
    DownloadButton downloadFileButton = new DownloadButton(simpleFormPanel, "Download File");

    @Test
    public void download() {
        assertTrue(downloadButton.download(new String[]{InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\downloadAndCancel.exe", "text.docx"}));
    }

    @Test
    public void downloadFile() {
        assertTrue(downloadFileButton.download(new String[]{InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\downloadAndCancel.exe", "text.docx"}));
    }
}
