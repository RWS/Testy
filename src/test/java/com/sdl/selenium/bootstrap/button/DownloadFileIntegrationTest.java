package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.web.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DownloadFileIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadFileIntegrationTest.class);

    private Form form = new Form(null, "Form Title");
    private DownloadFile downloadFile = new DownloadFile(form).setText("Download");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void assertDownload() {
        assertThat(downloadFile.download("text.docx"), is(true));
//        assertThat(downloadFile.download("LanguageCloudAddins.exe"));
        FileUtils.cleanDownloadDir();
    }

    @Test
    public void assertDownloadIsNotFile() {
        assertThat(downloadFile.download(InputData.DOWNLOAD_DIRECTORY), is(false));
        FileUtils.cleanDownloadDir();
    }
}
