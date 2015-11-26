package com.sdl.selenium.extjs3.button;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.web.utils.FileUtils;
import com.sdl.selenium.TestBase;

public class DownloadTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadTest.class);

    private Panel simpleFormPanel = new Panel(null, "Simple Form");
    private DownloadButton downloadButton = new DownloadButton(simpleFormPanel, "Download");
    private DownloadButton downloadWithSpacesButton = new DownloadButton(simpleFormPanel, "Download with spaces");
    private DownloadButton downloadFileButton = new DownloadButton(simpleFormPanel, "Download File");

    @Test
    public void download() {
        assertTrue(downloadButton.download("text.docx"));
    }

    @Test
    public void downloadFile() throws IOException {
        FileUtils.cleanDownloadDir();
        assertTrue(downloadFileButton.download("text.docx"));
    }

    @Test
    public void downloadFileWithSpaces() {
        assertTrue(downloadWithSpacesButton.download("text t.docx"));
    }
}
