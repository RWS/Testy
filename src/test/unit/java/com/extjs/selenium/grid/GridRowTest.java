package com.extjs.selenium.grid;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GridRowTest {
    public static ExtJsComponent container = new ExtJsComponent("container");
    public static GridPanel grid = new GridPanel();
    public static GridCell gridCell1 = new GridCell(1, "CellText1", "eq");
    public static GridCell gridCell2 = new GridCell(2, "CellText1", "eq");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new GridRow(), "//*"},
                {new GridRow(grid), "//div[contains(@class, 'x-grid3-row') and (not (contains(@class,'x-grid3-row-checker')))]"},
                {new GridRow(grid, 1), "//div[1][contains(@class, 'x-grid3-row') and (not (contains(@class,'x-grid3-row-checker')))]"},
                {new GridRow(grid, "2", "SearchText", "eq"), "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-2')]//*[text()='SearchText']) > 0]"},
                {new GridRow(grid, gridCell1), "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='CellText1']) > 0]"},
                {new GridRow(grid, gridCell1, gridCell2), "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='CellText1']) > 0 and count(*[contains(@class, 'x-grid3-row-table')]//td[2]//*[text()='CellText1']) > 0]"},
                {grid.findGridRow(new GridCell(1, "text1", "eq"), new GridCell(2, "text2", "contains")), "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='text1']) > 0 and count(*[contains(@class, 'x-grid3-row-table')]//td[2]//*[contains(text(),'text2')]) > 0]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(GridRow gridRow, String expectedXpath) {
        gridRow.setContainer(null);
        Assert.assertEquals(gridRow.getPath(), expectedXpath);
    }
}
