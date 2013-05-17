package com.sdl.selenium.web.table;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableCellTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    public static Table table = new Table().setId("ID");
    public static TableRow tableRow = new TableRow(table, "Text", "eq");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TableCell(),             "//td"},
                {new TableCell(tableRow, 1), "//table[@id='ID']//tr[text()='Text' or count(.//*[text()='Text']) > 0]//td[1]"},
                {new TableCell(3, "Text", "contains"), "//td[3][contains(text(),'Text') or count(.//*[contains(text(),'Text')]) > 0]"},
                {new TableCell(3, "Text", "eq"), "//td[3][text()='Text' or count(.//*[text()='Text']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TableCell tableCell, String expectedXpath) {
        Assert.assertEquals(tableCell.getPath(), expectedXpath);
    }
}
