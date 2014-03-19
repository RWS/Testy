package com.extjs.selenium.grid;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GridPanelTest {
    private static ExtJsComponent container = new ExtJsComponent("container");
    private static GridPanel grid = new GridPanel();
    private static GridPanel gridCls = new GridPanel("GridPanelCls");
    private static GridPanel gridContainer = new GridPanel(container);
    private static GridPanel gridClsAndColumn = new GridPanel("GridPanelCls", "1");
    private static GridPanel gridContainerAndColumn = new GridPanel(container, "1");
    private static GridPanel gridContainerAndClsAndColumn = new GridPanel(container, "GridPanelCls", "1");
    private static GridPanel gridClsPos = new GridPanel("GridPanelCls").setPosition(2);

    private static final String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";
    private static final String GRID_PATH = "//*[contains(concat(' ', @class, ' '), ' x-grid-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]";
    private static final String GRID_CLS_PATH = "//*[contains(concat(' ', @class, ' '), ' x-grid-panel ') and contains(concat(' ', @class, ' '), ' GridPanelCls ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {grid, GRID_PATH},
                {gridCls, GRID_CLS_PATH},
                {gridContainer, CONTAINER_PATH + GRID_PATH},
                {gridClsAndColumn, GRID_CLS_PATH},
                {gridContainerAndColumn, CONTAINER_PATH + GRID_PATH},
                {gridContainerAndClsAndColumn, CONTAINER_PATH + GRID_CLS_PATH},
                {gridClsPos, GRID_CLS_PATH + "[position() = 2]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(GridPanel gridPanel, String expectedXpath) {
        Assert.assertEquals(gridPanel.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {grid.getGridRow(), GRID_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridCls.getGridRow(), GRID_CLS_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainer.getGridRow(), CONTAINER_PATH + GRID_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridClsAndColumn.getGridRow(), GRID_CLS_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndColumn.getGridRow(), CONTAINER_PATH + GRID_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndClsAndColumn.getGridRow(), CONTAINER_PATH + GRID_CLS_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},

                {grid.getRow(1), GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridCls.getRow(1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainer.getRow(1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridClsAndColumn.getRow(1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndColumn.getRow(1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndClsAndColumn.getRow(1), CONTAINER_PATH + GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"}
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(GridRow gridRow, String expectedXpath) {
        Assert.assertEquals(gridRow.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider2() {
        return new Object[][]{
                {grid.getGridCell(1), GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-0')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridCls.getGridCell(1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-0')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainer.getGridCell(1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-0')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridClsAndColumn.getGridCell(1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getGridCell(1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getGridCell(1), CONTAINER_PATH + GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getCell(1, 1), GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridCls.getCell(1, 1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainer.getCell(1, 1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridClsAndColumn.getCell(1, 1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getCell(1, 1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getCell(1, 1), CONTAINER_PATH + GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getGridCell("search", false), GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[text()='search']"},
                {gridCls.getGridCell("search", false), GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[text()='search']"},
                {gridContainer.getGridCell("search", false), CONTAINER_PATH + GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[text()='search']"},
                {gridClsAndColumn.getGridCell("search", false), GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},
                {gridContainerAndColumn.getGridCell("search", false), CONTAINER_PATH + GRID_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},
                {gridContainerAndClsAndColumn.getGridCell("search", false), CONTAINER_PATH + GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},

                {grid.getGridCell("search", true), GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[starts-with(text(),'search')]"},
                {gridCls.getGridCell("search", true), GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[starts-with(text(),'search')]"},
                {gridContainer.getGridCell("search", true), CONTAINER_PATH + GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[starts-with(text(),'search')]"},
                {gridClsAndColumn.getGridCell("search", true), GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},
                {gridContainerAndColumn.getGridCell("search", true), CONTAINER_PATH + GRID_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},
                {gridContainerAndClsAndColumn.getGridCell("search", true), CONTAINER_PATH + GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},

                {grid.getGridCell("search", 2), GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridCls.getGridCell("search", 2), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainer.getGridCell("search", 2), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridClsAndColumn.getGridCell("search", 2), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getGridCell("search", 2), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getGridCell("search", 2), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getGridCell("search", "2", 2), GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-2')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridCls.getGridCell("search", "2", 2), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-2')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainer.getGridCell("search", "2", 2), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-2')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridClsAndColumn.getGridCell("search", "2", 2), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-2')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getGridCell("search", "2", 2), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-2')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getGridCell("search", "2", 2), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-2')]//*[contains(text(),'search')]) > 0]//td[2]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getGridCell(1, 1, "text"), GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridCls.getGridCell(1, 1, "text"), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainer.getGridCell(1, 1, "text"), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridClsAndColumn.getGridCell(1, 1, "text"), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainerAndColumn.getGridCell(1, 1, "text"), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainerAndClsAndColumn.getGridCell(1, 1, "text"), CONTAINER_PATH + GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},

                {grid.getGridCell("SearchText", 1, "text"), GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridCls.getGridCell("SearchText", 1, "text"), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainer.getGridCell("SearchText", 1, "text"), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridClsAndColumn.getGridCell("SearchText", 1, "text"), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainerAndColumn.getGridCell("SearchText", 1, "text"), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainerAndClsAndColumn.getGridCell("SearchText", 1, "text"), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},

                {grid.getGridCell("SearchText", 1, "text", SearchType.EQUALS), GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {gridCls.getGridCell("SearchText", 1, "text", SearchType.EQUALS), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {gridContainer.getGridCell("SearchText", 1, "text", SearchType.EQUALS), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-0')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {gridClsAndColumn.getGridCell("SearchText", 1, "text", SearchType.EQUALS), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {gridContainerAndColumn.getGridCell("SearchText", 1, "text", SearchType.EQUALS), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},
                {gridContainerAndClsAndColumn.getGridCell("SearchText", 1, "text", SearchType.EQUALS), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and text()='text']"},

                {gridContainerAndClsAndColumn.getGridCell("SearchText", 1, "/text/exe", SearchType.CONTAINS_ALL), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text') and contains(text(),'exe')]"},
                {gridContainerAndClsAndColumn.getGridCell("SearchText", 1, "|text|exe", SearchType.CONTAINS_ANY), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-1')]//*[contains(text(),'SearchText')]) > 0]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text') or contains(text(),'exe')]"},

                {grid.getGridCell(1, new GridCell(1, "text", SearchType.EQUALS)), GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='text']) > 0]//td[1]//*"},
                {gridCls.getGridCell(1, new GridCell(1, "text", SearchType.EQUALS)), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='text']) > 0]//td[1]//*"},
                {gridContainer.getGridCell(1, new GridCell(1, "text", SearchType.EQUALS)), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='text']) > 0]//td[1]//*"},
                {gridClsAndColumn.getGridCell(1, new GridCell(1, "text", SearchType.EQUALS)), GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='text']) > 0]//td[1]//*"},
                {gridContainerAndColumn.getGridCell(1, new GridCell(1, "text", SearchType.EQUALS)), CONTAINER_PATH + GRID_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='text']) > 0]//td[1]//*"},
                {gridContainerAndClsAndColumn.getGridCell(1, new GridCell(1, "text", SearchType.EQUALS)), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[text()='text']) > 0]//td[1]//*"},

                {gridContainerAndClsAndColumn.getGridCell(1, new GridCell(1, "/text/exe", SearchType.CONTAINS_ALL)), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[contains(text(),'text') and contains(text(),'exe')]) > 0]//td[1]//*"},
                {gridContainerAndClsAndColumn.getGridCell(1, new GridCell(1, "|text|exe", SearchType.CONTAINS_ANY)), CONTAINER_PATH + GRID_CLS_PATH + "//*[count(*[contains(@class, 'x-grid3-row-table')]//td[1]//*[contains(text(),'text') or contains(text(),'exe')]) > 0]//td[1]//*"},

        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider2")
    public void getPathSelectorCorrectlyFromConstructors2(GridCell gridCell, String expectedXpath) {
        Assert.assertEquals(gridCell.getPath(), expectedXpath);
    }
}