package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GridRowTest {
    private static GridPanel grid = new GridPanel();
    private static GridCell gridCell1 = new GridCell(1, "CellText1", SearchType.EQUALS);
    private static GridCell gridCell2 = new GridCell(2, "CellText1", SearchType.EQUALS);

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new GridRow(), "//*"},
                {new GridRow(grid), "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {new GridRow(grid, 1), "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {new GridRow(grid, "2", "SearchText", SearchType.EQUALS), "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-2')]//*[text()='SearchText']) > 0]"},
                {new GridRow(grid, gridCell1), "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[1]//*[text()='CellText1']) > 0]"},
                {new GridRow(grid, gridCell1).setVisibility(true), "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[1]//*[text()='CellText1']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new GridRow(grid, 1, "SearchText", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[1]//*[contains(.,'SearchText')]) > 0]"},
                {new GridRow(grid, gridCell1, gridCell2), "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[1]//*[text()='CellText1']) > 0 and count(.//td[2]//*[text()='CellText1']) > 0]"},
                {grid.getRow(new GridCell(1, "text1", SearchType.EQUALS), new GridCell(2, "text2", SearchType.CONTAINS)), "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[1]//*[text()='text1']) > 0 and count(.//td[2]//*[contains(.,'text2')]) > 0]"},
                {new GridRow(grid, 3, false), "//div[3][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {new GridRow(grid, 3, true), "//div[3][contains(concat(' ', @class, ' '), ' x-grid3-row-selected ') and not(contains(@class, 'x-grid3-row-checker'))]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(GridRow gridRow, String expectedXpath) {
        gridRow.setContainer(null);
        Assert.assertEquals(gridRow.getXPath(), expectedXpath);
    }
}