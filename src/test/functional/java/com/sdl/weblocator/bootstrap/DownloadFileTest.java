package com.sdl.weblocator.bootstrap;

import com.sdl.bootstrap.button.DownloadFile;
import com.sdl.bootstrap.form.Form;
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
    DownloadFile downloadFile = new DownloadFile(form, "Project Data:");
    DownloadFile downloadFile1 = new DownloadFile(form).setText("Download");
    DownloadFile downloadFileNegative = new DownloadFile(form, "DownloadNegative:");
    DownloadFile downloadApex = new DownloadFile(form, "DownloadApex:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    /*@Ignores(value = {CHROME}, reason = "Nu se downloadeaza cu Chrome")
    @Test
     public void download() {
        assertTrue(downloadFile.download(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\downloadAndCancel.exe"));
    }

    @Ignores(value = {CHROME}, reason = "Nu se downloadeaza cu Chrome")
    @Test
    public void download1() {
        assertTrue(downloadFile1.download(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\downloadAndCancel.exe"));
    }

    @Ignores(value = {CHROME}, reason = "Nu se downloadeaza cu Chrome")
    @Test
    public void downloadAndSave() {
        assertTrue(downloadFile1.download(InputData.RESOURCES_DIRECTORY_PATH + "\\drivers\\downloadAndSave.exe"));

    }*/

    @Test
    public void assertDownload() {
        assertTrue(downloadFile1.download("text.docx"));
    }

    @Test
    public void assertDownloadIsNotFile() {
        assertFalse(downloadFile1.download(InputData.DOWNLOAD_DIRECTORY));
    }

    /*@Ignores(value = {CHROME}, reason = "Nu se downloadeaza cu Chrome")
    @Test
    public void assertDownloadApexFile() {
        assertTrue(downloadApex.download(InputData.DOWNLOAD_DIRECTORY + "create_training.apex"));
    }*/

    /*@Ignores(value = {CHROME}, reason = "Nu se downloadeaza cu Chrome")
    @Test
    public void downloadNegative() {
        assertFalse(downloadFileNegative.download(InputData.DOWNLOAD_DIRECTORY + "text.docx"));
    }*/
}
