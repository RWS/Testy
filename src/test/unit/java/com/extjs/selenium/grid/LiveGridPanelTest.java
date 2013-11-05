package com.extjs.selenium.grid;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LiveGridPanelTest {
    private static ExtJsComponent container = new ExtJsComponent("container");
    private static LiveGridPanel grid = new LiveGridPanel();
    private static LiveGridPanel gridCls = new LiveGridPanel("GridPanelCls");
    private static LiveGridPanel gridContainer = new LiveGridPanel(container);
    private static LiveGridPanel gridClsAndColumn = new LiveGridPanel("GridPanelCls", "1");
    private static LiveGridPanel gridContainerAndColumn = new LiveGridPanel(container, "1");
    private static LiveGridPanel gridContainerAndClsAndColumn = new LiveGridPanel(container, "GridPanelCls", "1");

    private static String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";
    private static String LIVE_GRID_PATH = "//*[contains(concat(' ', @class, ' '), ' ext-ux-livegrid ') and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]";
    private static String LIVE_GRID_PANEL_CLS= "//*[contains(concat(' ', @class, ' '), ' ext-ux-livegrid ') and contains(concat(' ', @class, ' '), ' GridPanelCls ') and not(starts-with(@id, 'ext-gen')) and not(contains(@class, 'x-panel-tc')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {grid, LIVE_GRID_PATH},
                {gridCls, LIVE_GRID_PANEL_CLS},
                {gridContainer, CONTAINER_PATH + LIVE_GRID_PATH},
                {gridClsAndColumn, LIVE_GRID_PANEL_CLS },
                {gridContainerAndColumn, CONTAINER_PATH + LIVE_GRID_PATH},
                {gridContainerAndClsAndColumn, CONTAINER_PATH + LIVE_GRID_PANEL_CLS},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(LiveGridPanel liveGridPanel, String expectedXpath) {
        Assert.assertEquals(liveGridPanel.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {grid.getGridRow(), grid.getPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridCls.getGridRow(), gridCls.getPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainer.getGridRow(), gridContainer.getPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridClsAndColumn.getGridRow(), gridClsAndColumn.getPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndColumn.getGridRow(), gridContainerAndColumn.getPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndClsAndColumn.getGridRow(), gridContainerAndClsAndColumn.getPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},

                {grid.getGridRow(1), LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridCls.getGridRow(1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainer.getGridRow(1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridClsAndColumn.getGridRow(1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndColumn.getGridRow(1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndClsAndColumn.getGridRow(1), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},

        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(GridRow gridRow, String expectedXpath) {
        Assert.assertEquals(gridRow.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider2() {
        return new Object[][]{
                {grid.getGridCell(1), LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-0')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridCls.getGridCell(1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-0')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainer.getGridCell(1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-0')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridClsAndColumn.getGridCell(1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getGridCell(1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getGridCell(1), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(@class, 'x-grid3-td-1')]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getGridCell(1, 1), LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridCls.getGridCell(1, 1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainer.getGridCell(1, 1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridClsAndColumn.getGridCell(1, 1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getGridCell(1, 1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getGridCell(1, 1), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getGridCell("search", false), LIVE_GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[text()='search']"},
                {gridCls.getGridCell("search", false), LIVE_GRID_PANEL_CLS + "//*[contains(@class, 'x-grid3-td-0')]//*[text()='search']"},
                {gridContainer.getGridCell("search", false), CONTAINER_PATH + LIVE_GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[text()='search']"},
                {gridClsAndColumn.getGridCell("search", false), LIVE_GRID_PANEL_CLS + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},
                {gridContainerAndColumn.getGridCell("search", false), CONTAINER_PATH + LIVE_GRID_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},
                {gridContainerAndClsAndColumn.getGridCell("search", false), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//*[contains(@class, 'x-grid3-td-1')]//*[text()='search']"},

                {grid.getGridCell("search", true), LIVE_GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[starts-with(text(),'search')]"},
                {gridCls.getGridCell("search", true), LIVE_GRID_PANEL_CLS + "//*[contains(@class, 'x-grid3-td-0')]//*[starts-with(text(),'search')]"},
                {gridContainer.getGridCell("search", true), CONTAINER_PATH + LIVE_GRID_PATH + "//*[contains(@class, 'x-grid3-td-0')]//*[starts-with(text(),'search')]"},
                {gridClsAndColumn.getGridCell("search", true), LIVE_GRID_PANEL_CLS + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},
                {gridContainerAndColumn.getGridCell("search", true), CONTAINER_PATH + LIVE_GRID_PATH + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},
                {gridContainerAndClsAndColumn.getGridCell("search", true), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//*[contains(@class, 'x-grid3-td-1')]//*[starts-with(text(),'search')]"},

                {grid.getGridCell(1, 1, "text"), LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridCls.getGridCell(1, 1, "text"), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainer.getGridCell(1, 1, "text"), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridClsAndColumn.getGridCell(1, 1, "text"), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainerAndColumn.getGridCell(1, 1, "text"), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
                {gridContainerAndClsAndColumn.getGridCell(1, 1, "text"), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ') and contains(text(),'text')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider2")
    public void getPathSelectorCorrectlyFromConstructors2(GridCell gridCell, String expectedXpath) {
        Assert.assertEquals(gridCell.getPath(), expectedXpath);
    }
}
