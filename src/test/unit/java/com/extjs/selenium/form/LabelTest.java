package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LabelTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Label("LabelText"),            "//label[contains(text(),'LabelText')]"},
                {new Label(container),              "//*[contains(@class, 'container')]//label"},
                {new Label(container, "LabelText"), "//*[contains(@class, 'container')]//label[contains(text(),'LabelText')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Label label, String expectedXpath) {
        Assert.assertEquals(label.getPath(), expectedXpath);
    }
}
