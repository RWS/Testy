package com.sdl.selenium.web.button;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FlashUploadButtonTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new FlashUploadButton(),                   "//object"},
                {new FlashUploadButton(container, "Class"), "//*[contains(concat(' ', @class, ' '), ' container ')]//object[contains(concat(' ', @class, ' '), ' Class ')]"},
                {new FlashUploadButton(null, "Class"),      "//object[contains(concat(' ', @class, ' '), ' Class ')]"},
                {new FlashUploadButton(container),          "//*[contains(concat(' ', @class, ' '), ' container ')]//object[contains(concat(' ', @class, ' '), ' swfupload ')]"},
                {new FlashUploadButton("label", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='label']//following-sibling::*//*//object[contains(concat(' ', @class, ' '), ' swfupload ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(FlashUploadButton simpleTextField, String expectedXpath) {
        assertThat(simpleTextField.getButtonElement().getXPath(), equalTo(expectedXpath));
    }

}
