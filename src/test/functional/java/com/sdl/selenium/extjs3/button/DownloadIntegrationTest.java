package com.sdl.selenium.extjs3.button;

import com.sdl.demo.extjs3.form.SimpleForm;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.FileUtils;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DownloadIntegrationTest extends TestBase {

    private SimpleForm simpleForm = new SimpleForm();

    @Test
    public void download() {
        assertThat(simpleForm.downloadButton.download("text.docx"), is(true));
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
