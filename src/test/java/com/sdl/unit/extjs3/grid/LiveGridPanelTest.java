package com.sdl.unit.extjs3.grid;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.extjs3.grid.GridCell;
import com.sdl.selenium.extjs3.grid.GridRow;
import com.sdl.selenium.extjs3.grid.LiveGridPanel;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LiveGridPanelTest {
    private static ExtJsComponent container = new ExtJsComponent("container");
    private static LiveGridPanel grid = new LiveGridPanel();
    private static LiveGridPanel gridCls = new LiveGridPanel("GridPanelCls");
    private static LiveGridPanel gridContainer = new LiveGridPanel(container);
    private static LiveGridPanel gridClsAndColumn = new LiveGridPanel("GridPanelCls", "1");
    private static LiveGridPanel gridContainerAndColumn = new LiveGridPanel(container, "1");
    private static LiveGridPanel gridContainerAndClsAndColumn = new LiveGridPanel(container, "GridPanelCls", "1");

    private static String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";
    private static String LIVE_GRID_PATH = "//*[contains(concat(' ', @class, ' '), ' ext-ux-livegrid ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]";
    private static String LIVE_GRID_PANEL_CLS= "//*[contains(concat(' ', @class, ' '), ' ext-ux-livegrid ') and contains(concat(' ', @class, ' '), ' GridPanelCls ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]";

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
        assertThat(liveGridPanel.getXPath(), equalTo(expectedXpath));
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {grid.getGridRow(), grid.getXPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridCls.getGridRow(), gridCls.getXPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainer.getGridRow(), gridContainer.getXPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridClsAndColumn.getGridRow(), gridClsAndColumn.getXPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndColumn.getGridRow(), gridContainerAndColumn.getXPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndClsAndColumn.getGridRow(), gridContainerAndClsAndColumn.getXPath() + "//div[contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},

                {grid.getRow(1), LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridCls.getRow(1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainer.getRow(1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridClsAndColumn.getRow(1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndColumn.getRow(1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
                {gridContainerAndClsAndColumn.getRow(1), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(GridRow gridRow, String expectedXpath) {
        assertThat(gridRow.getXPath(), equalTo(expectedXpath));
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider2() {
        return new Object[][]{
                {grid.getGridCell(1), LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ')]"},
                {gridCls.getGridCell(1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ')]"},
                {gridContainer.getGridCell(1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ')]"},
                {gridClsAndColumn.getGridCell(1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ')]"},
                {gridContainerAndColumn.getGridCell(1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ')]"},
                {gridContainerAndClsAndColumn.getGridCell(1), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[contains(concat(' ', @class, ' '), ' x-grid3-cell-inner ')]"},

                {grid.getCell(1, 1), LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridCls.getCell(1, 1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainer.getCell(1, 1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridClsAndColumn.getCell(1, 1), LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndColumn.getCell(1, 1), CONTAINER_PATH + LIVE_GRID_PATH + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},
                {gridContainerAndClsAndColumn.getCell(1, 1), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//div[1][contains(concat(' ', @class, ' '), ' x-grid3-row ') and not(contains(@class, 'x-grid3-row-checker'))]//td[1]//*[contains(@class, 'x-grid3-cell-inner')]"},

                {grid.getCell("search", SearchType.EQUALS), LIVE_GRID_PATH + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[text()='search']"},
                {gridCls.getCell("search", SearchType.EQUALS), LIVE_GRID_PANEL_CLS + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[text()='search']"},
                {gridContainer.getCell("search", SearchType.EQUALS), CONTAINER_PATH + LIVE_GRID_PATH + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[text()='search']"},
                {gridClsAndColumn.getCell("search", SearchType.EQUALS), LIVE_GRID_PANEL_CLS + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[text()='search']"},
                {gridContainerAndColumn.getCell("search", SearchType.EQUALS), CONTAINER_PATH + LIVE_GRID_PATH + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[text()='search']"},
                {gridContainerAndClsAndColumn.getCell("search", SearchType.EQUALS), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[text()='search']"},

                {grid.getCell("search", SearchType.STARTS_WITH), LIVE_GRID_PATH + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[starts-with(text(),'search')]"},
                {gridCls.getCell("search", SearchType.STARTS_WITH), LIVE_GRID_PANEL_CLS + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[starts-with(text(),'search')]"},
                {gridContainer.getCell("search", SearchType.STARTS_WITH), CONTAINER_PATH + LIVE_GRID_PATH + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-0 ')]//*[starts-with(text(),'search')]"},
                {gridClsAndColumn.getCell("search", SearchType.STARTS_WITH), LIVE_GRID_PANEL_CLS + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[starts-with(text(),'search')]"},
                {gridContainerAndColumn.getCell("search", SearchType.STARTS_WITH), CONTAINER_PATH + LIVE_GRID_PATH + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[starts-with(text(),'search')]"},
                {gridContainerAndClsAndColumn.getCell("search", SearchType.STARTS_WITH), CONTAINER_PATH + LIVE_GRID_PANEL_CLS + "//*[contains(concat(' ', @class, ' '), ' x-grid3-td-1 ')]//*[starts-with(text(),'search')]"},

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
        assertThat(gridCell.getXPath(), equalTo(expectedXpath));
    }
}
