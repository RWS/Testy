package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.Form;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class UploadFileIntegrationTest extends TestBase {

    private Form form = new Form(null, "Form Title");
    private UploadFile uploadFile = new UploadFile(form, "TPT Test:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void upload() {
        uploadFile.upload("Select file", InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\example.txt");
        assertThat(new File(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\example.txt").getName(), equalTo(uploadFile.uploadedNameFile()));
    }

    @Test
    public void uploadSimple() {
        uploadFile.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\example.txt");
        assertThat(new File(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\example.txt").getName(), equalTo(uploadFile.uploadedNameFile()));
    }

    @Test(dependsOnMethods = "upload")
    public void change() {
        uploadFile.change(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\exampleM.txt");
        assertThat(new File(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\exampleM.txt").getName(), equalTo(uploadFile.uploadedNameFile()));
    }
}
