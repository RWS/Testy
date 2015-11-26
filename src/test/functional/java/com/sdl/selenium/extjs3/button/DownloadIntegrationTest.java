package com.sdl.selenium.extjs3.button;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import org.testng.annotations.Test;

import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.web.utils.FileUtils;
import com.sdl.selenium.TestBase;

public class DownloadIntegrationTest extends TestBase {

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
        assertThat(downloadFileButton.download("text.docx"), is(true));
    }

    @Test
    public void downloadFileWithSpaces() {
        assertThat(downloadWithSpacesButton.download("text t.docx"), is(true));
    }
}
