package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ComboBoxTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new ComboBox(),                                       "//input[not(@type='hidden')]"},
                {new ComboBox().setClasses("ComboBoxClass"),                        "//input[contains(concat(' ', @class, ' '), ' ComboBoxClass ') and not(@type='hidden')]"},
                {new ComboBox(container),                              "//*[contains(concat(' ', @class, ' '), ' container ')]//input[not(@type='hidden')]"},
                {new ComboBox(container).setElPath("//table//tr[1]"),  "//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new ComboBox(container, "ComboBoxText"),              "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='ComboBoxText']//following-sibling::*//input[not(@type='hidden')]"},
                {new ComboBox("name", container),                      "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and not(@type='hidden')]"},
                {new ComboBox(container, "ComboBoxCls", "name", true), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and contains(concat(' ', @class, ' '), ' ComboBoxCls ') and not(@type='hidden')]"},
                {new ComboBox(container, "ComboBoxCls", "name", false),"//*[contains(concat(' ', @class, ' '), ' container ')]//input[@name='name' and contains(concat(' ', @class, ' '), ' ComboBoxCls ') and not(@type='hidden')]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(ComboBox combo, String expectedXpath) {
        Assert.assertEquals(combo.getPath(), expectedXpath);
    }
}
