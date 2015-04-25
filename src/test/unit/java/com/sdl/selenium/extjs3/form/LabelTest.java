package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LabelTest {
    public static ExtJsComponent container = new ExtJsComponent(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Label("LabelText"), "//label[contains(text(),'LabelText')]"},
                {new Label(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//label"},
                {new Label(container).setLabel("Label", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'Label')]//following-sibling::*//label"},
                {new Label(container).setLabel("Label", SearchType.CONTAINS, SearchType.HTML_NODE), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))='Label' or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))='Label')]//following-sibling::*//label"},
                {new Label(container, "LabelText"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'LabelText')]"},

                {new Label(By.container(container)), "//*[contains(concat(' ', @class, ' '), ' container ')]//label"},
                {new Label(By.container(container), By.label("Label", SearchType.CONTAINS)), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'Label')]//following-sibling::*//label"},
                {new Label(By.container(container), By.label("Label", SearchType.CONTAINS, SearchType.HTML_NODE)), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(normalize-space(concat(./*[1]//text(), ' ', text()[1], ' ', ./*[2]//text(), ' ', text()[2], ' ', ./*[3]//text(), ' ', text()[3], ' ', ./*[4]//text(), ' ', text()[4], ' ', ./*[5]//text(), ' ', text()[5]))='Label' or normalize-space(concat(text()[1], ' ', ./*[1]//text(), ' ', text()[2], ' ', ./*[2]//text(), ' ', text()[3], ' ', ./*[3]//text(), ' ', text()[4], ' ', ./*[4]//text(), ' ', text()[5], ' ', ./*[5]//text()))='Label')]//following-sibling::*//label"},
                {new Label(By.container(container), By.text("LabelText")), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'LabelText')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Label label, String expectedXpath) {
        Assert.assertEquals(label.getPath(), expectedXpath);
    }
}
