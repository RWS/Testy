package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GridCellTest {
    private static GridPanel grid = new GridPanel();

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new GridCell(), "//*"},
                {new GridCell(1).setContainer(grid),                     grid.getXPath() + "//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {new GridCell("text", grid),                grid.getXPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(.,'text')]"},
                {new GridCell("text", SearchType.EQUALS),          "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {new GridCell(2, "text", SearchType.EQUALS),       "//td[2]//*[text()='text']"},
                {new GridCell(2, "/text/exe", SearchType.CONTAINS_ALL),       "//td[2]//*[contains(.,'text') and contains(.,'exe')]"},
                {new GridCell(2, "|text|exe", SearchType.CONTAINS_ANY),       "//td[2]//*[(contains(.,'text') or contains(.,'exe'))]"},
                {new GridCell(grid, "text", SearchType.CONTAINS),    grid.getXPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(.,'text')]"},
                {new GridCell(grid, "text", SearchType.STARTS_WITH), grid.getXPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and starts-with(text(),'text')]"},
                {new GridCell().setContainer(grid.getRow(new GridCell(2, "text", SearchType.EQUALS))).setPosition(8).setText("Continue"), grid.getXPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[2]//*[text()='text']) > 0]//td[8]//*[contains(.,'Continue')]"},
                {new GridCell().setContainer(grid.getRow(new GridCell(2, "text", SearchType.EQUALS))).setPosition(8).setText("Continue").setVisibility(true), grid.getXPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[2]//*[text()='text']) > 0]//td[8]//*[contains(.,'Continue') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(GridCell gridCell, String expectedXpath) {
        Assert.assertEquals(gridCell.getXPath(), expectedXpath);
    }
}
