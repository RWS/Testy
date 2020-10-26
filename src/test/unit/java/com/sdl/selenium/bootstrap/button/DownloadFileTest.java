package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DownloadFileTest {
    public static WebLocator container = new WebLocator().setId("ID");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DownloadFile(),                                "//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new DownloadFile(container),                       "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new DownloadFile(container, "ButtonLabel"),        "//*[@id='ID']//label[text()='ButtonLabel']//following-sibling::*//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new DownloadFile(container).setText("ButtonText"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and contains(text(),'ButtonText')]"},
                {new DownloadFile(container).setId("ID"),           "//*[@id='ID']//button[@id='ID' and contains(concat(' ', @class, ' '), ' btn ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DownloadFile downloadFile, String expectedXpath) {
        assertThat(downloadFile.getXPath(), equalTo(expectedXpath));
    }
}