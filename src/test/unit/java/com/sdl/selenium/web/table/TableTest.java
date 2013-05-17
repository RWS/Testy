package com.sdl.selenium.web.table;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Table(),             "//table"},
                {new Table().setId("ID"), "//table[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Table table, String expectedXpath) {
        Assert.assertEquals(table.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {new Table().getGridRow(1),                                 "//table//tr[1]"},
                {new Table().getGridRow("SearchElement"),                   "//table//tr[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0]"},
                {new Table().getGridRow("SearchElement", "contains"),       "//table//tr[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0]"},
                {new Table().getGridRow("SearchElement", "starts"),         "//table//tr[starts-with(text(),'SearchElement') or count(.//*[starts-with(text(),'SearchElement')]) > 0]"},

                {new Table().getGridCell(1, 1),                             "//table//tr[1]//td[1]"},
                {new Table().getGridCell("SearchElement", "eq"),            "//table//tr//td[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0]"},
                {new Table().getGridCell("SearchElement", "equals"),        "//table//tr//td[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0]"},
                {new Table().getGridCell("SearchElement", "contains"),      "//table//tr//td[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0]"},
                {new Table().getGridCell("SearchElement", "starts"),        "//table//tr//td[starts-with(text(),'SearchElement') or count(.//*[starts-with(text(),'SearchElement')]) > 0]"},
                {new Table().getGridCell(1, 1, "SearchElement"),            "//table//tr[1]//td[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0][1]"},
                {new Table().getGridCell("SearchElement", 2, "text", "eq"), "//table//tr[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0]//td[text()='text' or count(.//*[text()='text']) > 0]"},
                {new Table().getGridCell("SearchElement", 2, "eq"),         "//table//tr[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0]//td[2]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(WebLocator webLocator, String expectedXpath) {
        Assert.assertEquals(webLocator.getPath(), expectedXpath);
    }
}
