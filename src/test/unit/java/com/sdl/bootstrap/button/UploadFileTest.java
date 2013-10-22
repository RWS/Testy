package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UploadFileTest {
    public static WebLocator container = new WebLocator().setId("ID");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new UploadFile(),                          "//div[contains(concat(' ', @class, ' '), ' fileupload ')]"},
                {new UploadFile(container),                 "//*[@id='ID']//div[contains(concat(' ', @class, ' '), ' fileupload ')]"},
                {new UploadFile(container, "ButtonLabel"),  "//*[@id='ID']//label[text()='ButtonLabel']//following-sibling::*//div[contains(concat(' ', @class, ' '), ' fileupload ')]"},
                {new UploadFile(container).setId("ID"),     "//*[@id='ID']//div[@id='ID' and contains(concat(' ', @class, ' '), ' fileupload ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(UploadFile uploadFile, String expectedXpath) {
        Assert.assertEquals(uploadFile.getPath(), expectedXpath);
    }
}