package com.sdl.weblocator.bootstrap;

import com.sdl.bootstrap.button.UploadFile;
import com.sdl.bootstrap.form.Form;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertTrue;

public class UploadFileTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileTest.class);

    Form form = new Form(null, "Form Title");
    UploadFile uploadFile = new UploadFile(form, "TPT Test:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void upload() {
        uploadFile.upload("Select file", new String[]{InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\upload.exe", InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\example.txt"});
        assertTrue(new File(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\example.txt").getName().equals(uploadFile.uploadedNameFile()));
    }

    @Test (dependsOnMethods = "upload")
    public void change() {
        uploadFile.change(new String[]{InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\upload.exe", InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\exampleM.txt"});
        assertTrue(new File(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\exampleM.txt").getName().equals(uploadFile.uploadedNameFile()));
    }
}
