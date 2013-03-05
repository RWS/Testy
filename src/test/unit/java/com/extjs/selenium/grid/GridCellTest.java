package com.extjs.selenium.grid;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GridCellTest {
    public static GridPanel grid = new GridPanel();

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new GridCell(), "//*"},
                {new GridCell(grid),                        grid.getPath() + "//*"},
                {new GridCell(grid, 1),                     grid.getPath() + "//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {new GridCell("text", grid),                grid.getPath() + "//*[contains(@class, 'x-grid3-cell-inner') and contains(text(),'text')]"},
                {new GridCell(grid, "text", "eq"),          grid.getPath() + "//*[contains(@class, 'x-grid3-cell-inner') and text()='text']"},
                {new GridCell(grid, 2, "text", "eq"),       grid.getPath() + "//td[2]//*[text()='text']"},
                {new GridCell(grid, "text", "contains"),    grid.getPath() + "//*[contains(@class, 'x-grid3-cell-inner') and contains(text(),'text')]"},
                {new GridCell(grid, "text", "starts-with"), grid.getPath() + "//*[contains(@class, 'x-grid3-cell-inner') and starts-with(text(),'text')]"},
                {new GridCell(grid.findGridRow(new GridCell(2, "text", "eq"))).setPosition(8).setText("Continue"), grid.getPath() + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[2]//*[text()='text']) > 0]//td[8]//*[contains(text(),'Continue')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(GridCell gridCell, String expectedXpath) {
        Assert.assertEquals(gridCell.getPath(), expectedXpath);
    }
}
