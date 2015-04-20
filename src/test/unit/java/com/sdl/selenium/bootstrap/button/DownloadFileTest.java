package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DownloadFileTest {
    private static WebLocator container = new WebLocator(By.id("ID"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new DownloadFile(),                                "//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new DownloadFile(container),                       "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new DownloadFile(container, "ButtonLabel"),        "//*[@id='ID']//label[text()='ButtonLabel']//following-sibling::*//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new DownloadFile(container).setText("ButtonText"), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and contains(text(),'ButtonText')]"},
                {new DownloadFile(container).setId("ID"),           "//*[@id='ID']//button[@id='ID' and contains(concat(' ', @class, ' '), ' btn ')]"},

                {new DownloadFile(By.container(container)),                       "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new DownloadFile(By.container(container), By.label("ButtonLabel")),        "//*[@id='ID']//label[text()='ButtonLabel']//following-sibling::*//button[contains(concat(' ', @class, ' '), ' btn ')]"},
                {new DownloadFile(By.container(container), By.text("ButtonText")), "//*[@id='ID']//button[contains(concat(' ', @class, ' '), ' btn ') and contains(text(),'ButtonText')]"},
                {new DownloadFile(By.container(container), By.id("ID")),           "//*[@id='ID']//button[@id='ID' and contains(concat(' ', @class, ' '), ' btn ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(DownloadFile downloadFile, String expectedXpath) {
        Assert.assertEquals(downloadFile.getPath(), expectedXpath);
    }
}