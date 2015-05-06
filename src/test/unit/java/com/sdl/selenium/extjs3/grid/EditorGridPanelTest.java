package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EditorGridPanelTest {
    private static ExtJsComponent container = new ExtJsComponent("container");
    private static EditorGridPanel grid = new EditorGridPanel();
    private static EditorGridPanel gridCls = new EditorGridPanel("GridPanelCls");
    private static EditorGridPanel gridClsAndColumn = new EditorGridPanel("GridPanelCls", "1");
    private static EditorGridPanel gridContainerAndColumn = new EditorGridPanel(container, "1");
    private static EditorGridPanel gridContainerAndClsAndColumn = new EditorGridPanel(container, "1", 1);

    private static final String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";
    private static final String GRID_PATH = "//*[contains(concat(' ', @class, ' '), ' x-grid-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]";
    private static final String GRID_CLS_PATH = "//*[contains(concat(' ', @class, ' '), ' x-grid-panel ') and contains(concat(' ', @class, ' '), ' GridPanelCls ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {grid, GRID_PATH},
                {gridCls, GRID_CLS_PATH},
                {gridClsAndColumn, GRID_CLS_PATH},
                {gridContainerAndColumn, CONTAINER_PATH + GRID_PATH},
                {gridContainerAndClsAndColumn, CONTAINER_PATH + GRID_PATH},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(EditorGridPanel gridPanel, String expectedXpath) {
        Assert.assertEquals(gridPanel.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {grid.getGridRow(), GRID_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridCls.getGridRow(), GRID_CLS_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridClsAndColumn.getGridRow(), GRID_CLS_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndColumn.getGridRow(), CONTAINER_PATH + GRID_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndClsAndColumn.getGridRow(), CONTAINER_PATH + GRID_PATH + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},

                {grid.getRowLocator(1), GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridCls.getRowLocator(1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridClsAndColumn.getRowLocator(1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndColumn.getRowLocator(1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndClsAndColumn.getRowLocator(1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},

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
                {gridClsAndColumn.getGridCell(1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getGridCell(1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getGridCell(1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getCell(1, 1), GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridCls.getCell(1, 1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridClsAndColumn.getCell(1, 1), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getCell(1, 1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getCell(1, 1), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getCell("search", SearchType.EQUALS), GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[text()='search']"},
                {gridCls.getCell("search", SearchType.EQUALS), GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[text()='search']"},
                {gridClsAndColumn.getCell("search", SearchType.EQUALS), GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},
                {gridContainerAndColumn.getCell("search", SearchType.EQUALS), CONTAINER_PATH + GRID_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},
                {gridContainerAndClsAndColumn.getCell("search", SearchType.EQUALS), CONTAINER_PATH + GRID_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},

                {grid.getCell("search", SearchType.STARTS_WITH), GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[starts-with(text(),'search')]"},
                {gridCls.getCell("search", SearchType.STARTS_WITH), GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[starts-with(text(),'search')]"},
                {gridClsAndColumn.getCell("search", SearchType.STARTS_WITH), GRID_CLS_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},
                {gridContainerAndColumn.getCell("search", SearchType.STARTS_WITH), CONTAINER_PATH + GRID_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},
                {gridContainerAndClsAndColumn.getCell("search", SearchType.STARTS_WITH), CONTAINER_PATH + GRID_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},

                {grid.getGridCell(1, 1, "text"), GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridCls.getGridCell(1, 1, "text"), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridClsAndColumn.getGridCell(1, 1, "text"), GRID_CLS_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainerAndColumn.getGridCell(1, 1, "text"), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainerAndClsAndColumn.getGridCell(1, 1, "text"), CONTAINER_PATH + GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider2")
    public void getPathSelectorCorrectlyFromConstructors2(GridCell gridCell, String expectedXpath) {
        Assert.assertEquals(gridCell.getPath(), expectedXpath);
    }
}
