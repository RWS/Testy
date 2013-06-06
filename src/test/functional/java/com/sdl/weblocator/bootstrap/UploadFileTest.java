package com.sdl.weblocator.bootstrap;

import com.sdl.bootstrap.button.UploadFile;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertTrue;

public class UploadFileTest extends TestBase {
    private static final Logger logger = Logger.getLogger(UploadFileTest.class);

    UploadFile uploadFile = new UploadFile(null, "TPT Test:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void upload() {
        uploadFile.upload("Select file", new String[]{InputData.UPLOAD_DIRECTORY + "\\upload\\upload.exe", InputData.UPLOAD_DIRECTORY + "\\upload\\example.txt"});
        assertTrue(new File(InputData.UPLOAD_DIRECTORY + "\\upload\\example.txt").getName().equals(uploadFile.uploadedNameFile()));
    }

    @Test (dependsOnMethods = "upload")
    public void change() {
        uploadFile.change(new String[]{InputData.UPLOAD_DIRECTORY + "\\upload\\upload.exe", InputData.UPLOAD_DIRECTORY + "\\upload\\exampleM.txt"});
        assertTrue(new File(InputData.UPLOAD_DIRECTORY + "\\upload\\exampleM.txt").getName().equals(uploadFile.uploadedNameFile()));
    }
}
