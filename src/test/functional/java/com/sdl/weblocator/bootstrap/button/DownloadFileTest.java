package com.sdl.weblocator.bootstrap.button;

import com.sdl.bootstrap.button.DownloadFile;
import com.sdl.bootstrap.form.Form;
import com.sdl.selenium.web.utils.FileUtils;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DownloadFileTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadFileTest.class);

    Form form = new Form(null, "Form Title");
    DownloadFile downloadFile = new DownloadFile(form).setText("Download");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void assertDownload() {
        assertTrue(downloadFile.download("text.docx"));
        FileUtils.cleanDirectory();
    }

    @Test
    public void assertDownloadIsNotFile() {
        assertFalse(downloadFile.download(InputData.DOWNLOAD_DIRECTORY));
        FileUtils.cleanDirectory();
    }
}
