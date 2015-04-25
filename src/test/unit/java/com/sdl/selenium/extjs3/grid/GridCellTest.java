package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.web.By;
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
                {new GridCell(1).setContainer(grid), grid.getPath() + "//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {new GridCell("text", grid), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {new GridCell("text", SearchType.EQUALS), "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {new GridCell(grid, "text", SearchType.EQUALS), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {new GridCell(grid, 2, "text", SearchType.EQUALS), grid.getPath() + "//td[2]//*[text()='text']"},
                {new GridCell(grid, 2, "/text/exe", SearchType.CONTAINS_ALL), grid.getPath() + "//td[2]//*[contains(text(),'text') and contains(text(),'exe')]"},
                {new GridCell(grid, 2, "|text|exe", SearchType.CONTAINS_ANY), grid.getPath() + "//td[2]//*[(contains(text(),'text') or contains(text(),'exe'))]"},
                {new GridCell(grid, "text", SearchType.CONTAINS), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {new GridCell(grid, "text", SearchType.STARTS_WITH), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and starts-with(text(),'text')]"},
                {new GridCell(grid.getRow(new GridCell(2, "text", SearchType.EQUALS))).setTag("td[8]//*").setText("Continue"), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[2]//*[text()='text']) > 0]//td[8]//*[contains(text(),'Continue')]"},
                {new GridCell(grid.getRow(new GridCell(2, "text", SearchType.EQUALS))).setTag("td[8]//*").setText("Continue").setVisibility(true), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[2]//*[text()='text']) > 0]//td[8]//*[contains(text(),'Continue') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},

                {new GridCell(By.container(grid), By.tag("td[1]//*"), By.classes("x-grid3-cell-inner")), grid.getPath() + "//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ')]"},
                {new GridCell(By.container(grid), By.text("text"), By.classes("x-grid3-cell-inner")), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {new GridCell(By.text("text", SearchType.EQUALS), By.classes("x-grid3-cell-inner")), "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {new GridCell(By.container(grid), By.text("text", SearchType.EQUALS), By.classes("x-grid3-cell-inner")), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {new GridCell(By.container(grid), By.tag("td[2]//*"), By.text("text", SearchType.EQUALS)), grid.getPath() + "//td[2]//*[text()='text']"},
                {new GridCell(By.container(grid), By.tag("td[2]//*"), By.text("/text/exe", SearchType.CONTAINS_ALL)), grid.getPath() + "//td[2]//*[contains(text(),'text') and contains(text(),'exe')]"},
                {new GridCell(By.container(grid), By.tag("td[2]//*"), By.text("|text|exe", SearchType.CONTAINS_ANY)), grid.getPath() + "//td[2]//*[(contains(text(),'text') or contains(text(),'exe'))]"},
                {new GridCell(By.container(grid), By.text("text", SearchType.CONTAINS), By.classes("x-grid3-cell-inner")), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {new GridCell(By.container(grid), By.text("text", SearchType.STARTS_WITH), By.classes("x-grid3-cell-inner")), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and starts-with(text(),'text')]"},
                {new GridCell(By.container(grid.getRow(new GridCell(2, "text", SearchType.EQUALS))), By.tag("td[8]//*"), By.text("Continue")), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[2]//*[text()='text']) > 0]//td[8]//*[contains(text(),'Continue')]"},
                {new GridCell(By.container(grid.getRow(new GridCell(2, "text", SearchType.EQUALS))), By.tag("td[8]//*"), By.text("Continue"), By.visibility(true)), grid.getPath() + "//*[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker')) and count(.//td[2]//*[text()='text']) > 0]//td[8]//*[contains(text(),'Continue') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(GridCell gridCell, String expectedXpath) {
        Assert.assertEquals(gridCell.getPath(), expectedXpath);
    }
}
