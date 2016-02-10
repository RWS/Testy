package com.sdl.selenium.extjs3.button;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertTrue;
import java.io.IOException;

import com.sdl.demo.extjs3.form.SimpleForm;
import org.testng.annotations.Test;

import com.sdl.selenium.web.utils.FileUtils;
import com.sdl.selenium.TestBase;

public class DownloadIntegrationTest extends TestBase {

    private SimpleForm simpleForm = new SimpleForm();

    @Test
    public void download() {
        assertTrue(simpleForm.downloadButton.download("text.docx"));
    }

    @Test
    public void downloadFile() throws IOException {
        FileUtils.cleanDownloadDir();
        assertThat(simpleForm.downloadFileButton.download("text.docx"), is(true));
    }

    @Test
    public void downloadFileWithSpaces() {
        assertThat(simpleForm.downloadWithSpacesButton.download("text t.docx"), is(true));
    }
}
