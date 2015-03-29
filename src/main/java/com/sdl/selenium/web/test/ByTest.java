package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ByTest {
    private static WebLocator container = new WebLocator(By.classes("container"));
    private static String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";
    private static String BASE_CLS_PATH = "//*[contains(concat(' ', @class, ' '), ' BaseCls ')]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
//                {new WebLocator(), "//*"},
//                {new WebLocator(By.classes("cls")), "//*[contains(concat(' ', @class, ' '), ' cls ')]"},
//                {new WebLocator(container), CONTAINER_PATH + "//*"},
//                {new WebLocator(container, By.id("Id")), CONTAINER_PATH + "//*[@id='Id']"},

//                {new TextField(By.id("Id"), By.text("text")).icon("icon"), "//*[@id='Id' and contains(text(),'text') and count(.//*[contains(@class, 'icon')]) > 0]"},
//                {new TextField().setText("text").setId("Id"), "//*[@id='Id' and contains(text(),'text') and count(.//*[contains(@class, 'icon')]) > 0]"},

//                {new TextField(By.id("Id")), "//*[@id='Id']"},
//                {new TextField(container), CONTAINER_PATH + "//*"},
                {new TextField(), BASE_CLS_PATH},
//                {new TextField(By.container(container)), CONTAINER_PATH + "//*"},
//                {new TextField(By.container(container), By.id("Id")), CONTAINER_PATH + "//*[@id='Id']"},
//                {new TextField(container, By.id("Id"), By.xpath("")), CONTAINER_PATH + "//*[@id='Id']"},
//                {new TextField(container, By.id("Id"), By.text("ttt")), CONTAINER_PATH + "//*[@id='Id' and contains(text(),'ttt')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(WebLocator el, String expectedXpath) {
        Assert.assertEquals(el.getPathBuilder().getPath(), expectedXpath);
    }

    @Test
    public void getPathSelectorCorrectlyFromConstructor() {
        WebLocator el = new WebLocator(By.text("text"));
        el.getPathBuilder().init(By.text("test"));
        Assert.assertEquals(el.getPathBuilder().getPath(), "//*[contains(text(),'test')]");
    }
}