package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ComboBoxTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new ComboBox(),                                       "//input[not (@type='hidden') ]"},
                {new ComboBox("ComboBoxClass"),                        "//input[contains(@class, 'ComboBoxClass') and not (@type='hidden') ]"},
                {new ComboBox(container),                              "//*[contains(@class, 'container')]//input[not (@type='hidden') ]"},
                {new ComboBox(container).setElPath("//table//tr[1]"),  "//*[contains(@class, 'container')]//table//tr[1]"},
                {new ComboBox(container, "ComboBoxText"),              "//*[contains(@class, 'container')]//label[text()='ComboBoxText']//following-sibling::*//input[not (@type='hidden') ]"},
                {new ComboBox("name", container),                      "//*[contains(@class, 'container')]//input[contains(@name,'name') and not (@type='hidden') ]"},
                {new ComboBox(container, "ComboBoxCls", "name", true), "//*[contains(@class, 'container')]//input[contains(@class, 'ComboBoxCls') and contains(@name,'name') and not (@type='hidden') ]"},
                {new ComboBox(container, "ComboBoxCls", "name", false),"//*[contains(@class, 'container')]//input[contains(@class, 'ComboBoxCls') and contains(@name,'name') and not (@type='hidden') ]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(ComboBox combo, String expectedXpath) {
        Assert.assertEquals(combo.getPath(), expectedXpath);
    }
}
