package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.DownloadButton;
import com.extjs.selenium.panel.Panel;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;

public class DownloadTest extends TestBase {
    private static final Logger logger = Logger.getLogger(DownloadTest.class);

    private Panel simpleFormPanel = new Panel(null, "Simple Form");
    private DownloadButton downloadButton = new DownloadButton(simpleFormPanel, "Download");
    private DownloadButton downloadWithSpacesButton = new DownloadButton(simpleFormPanel, "Download with spaces");
    private DownloadButton downloadFileButton = new DownloadButton(simpleFormPanel, "Download File");

   /* @Ignores(value = {CHROME}, reason = "Nu se seleacteaza in Chrome")
    @Test
    public void download() {
        assertTrue(downloadButton.download(InputData.DOWNLOAD_DIRECTORY + "text.docx"));
    }

    @Ignores(value = {CHROME}, reason = "Nu se seleacteaza in Chrome")
    @Test
    public void downloadFile() {
        assertTrue(downloadFileButton.download(InputData.DOWNLOAD_DIRECTORY + "text.docx"));
    }

    @Ignores(value = {CHROME}, reason = "Nu se seleacteaza in Chrome")
    @Test
    public void downloadFileWithSpaces() {
        assertTrue(downloadWithSpacesButton.download(InputData.DOWNLOAD_DIRECTORY + "text t.docx"));
    }*/
}
