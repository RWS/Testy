package com.sdl.weblocator.bootstrap;

import com.sdl.bootstrap.button.DownloadFile;
import com.sdl.bootstrap.form.Form;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DownloadFileTest extends TestBase {
    private static final Logger logger = Logger.getLogger(DownloadFileTest.class);

    Form form = new Form(null, "Form Title");
    DownloadFile downloadFile = new DownloadFile(form, "Project Data:");
    DownloadFile downloadFileNegative = new DownloadFile(form, "DownloadNegative:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
     public void download() {
        assertTrue(downloadFile.download(new String[]{InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\downloadAndCancel.exe", "test.docx"}));
    }

    @Test
    public void downloadNegative() {
        assertFalse(downloadFileNegative.download(new String[]{InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\downloadAndCancel.exe", "test.docx"}));
    }
}
