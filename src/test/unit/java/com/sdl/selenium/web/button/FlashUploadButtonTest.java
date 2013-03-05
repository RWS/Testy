package com.sdl.selenium.web.button;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FlashUploadButtonTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new FlashUploadButton(),                   "//object"},
                {new FlashUploadButton(container, "Class"), "//*[contains(@class, 'container')]//object[contains(@class, 'Class')]"},
                {new FlashUploadButton(null, "Class"),      "//object[contains(@class, 'Class')]"},
                {new FlashUploadButton(container),          "//*[contains(@class, 'container')]//object[contains(@class, 'swfupload')]"},
                {new FlashUploadButton("label", container), "//*[contains(@class, 'container')]//label[text()='label']//following-sibling::*//*//object[contains(@class, 'swfupload')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(FlashUploadButton simpleTextField, String expectedXpath) {
        Assert.assertEquals(simpleTextField.getButtonElement().getPath(), expectedXpath);
    }

}
