package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.DownloadButton;
import com.extjs.selenium.panel.Panel;
import com.sdl.weblocator.Ignores;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.sdl.weblocator.Ignores.Driver.CHROME;
import static org.testng.Assert.assertTrue;

public class DownloadTest extends TestBase {
    private static final Logger logger = Logger.getLogger(DownloadTest.class);

    private Panel simpleFormPanel = new Panel(null, "Simple Form");
    private DownloadButton downloadButton = new DownloadButton(simpleFormPanel, "Download");
    private DownloadButton downloadWithSpacesButton = new DownloadButton(simpleFormPanel, "Download with spaces");
    private DownloadButton downloadFileButton = new DownloadButton(simpleFormPanel, "Download File");
    private String downloadAndCancel = InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\downloadAndCancel.exe";

    @Ignores(value = {CHROME}, reason = "Nu se seleacteaza in Chrome")
    @Test
    public void download() {
        assertTrue(downloadButton.download(downloadAndCancel, "text.docx"));
    }

    @Ignores(value = {CHROME}, reason = "Nu se seleacteaza in Chrome")
    @Test
    public void downloadFile() {
        assertTrue(downloadFileButton.download(downloadAndCancel, "text.docx"));
    }

    @Ignores(value = {CHROME}, reason = "Nu se seleacteaza in Chrome")
    @Test
    public void downloadFileWithSpaces() {
        assertTrue(downloadWithSpacesButton.download(downloadAndCancel, "text t.docx"));
    }
}
