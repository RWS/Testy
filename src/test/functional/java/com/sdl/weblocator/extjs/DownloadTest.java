package com.sdl.weblocator.extjs;

import com.sdl.selenium.extjs3.button.DownloadButton;
import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

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
        FileUtils.cleanDirectory(new File(InputData.DOWNLOAD_DIRECTORY));
        assertTrue(downloadFileButton.download("text.docx"));
    }

    @Test
    public void downloadFileWithSpaces() {
        assertTrue(downloadWithSpacesButton.download("text t.docx"));
    }
}
