package com.extjs.selenium.grid;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Row;
import org.apache.log4j.Logger;

public class GridRow extends Row {
    private static final Logger logger = Logger.getLogger(GridRow.class);

    private static final String GRID_ROW_PATH = "[contains(@class, 'x-grid3-row') and (not (contains(@class,'x-grid3-row-checker')))]";
    private static final String GRID_ROW_SELECTED_PATH = "[contains(@class,'x-grid3-row-selected') and (not (contains(@class,'x-grid3-row-checker')))]";

    public GridRow() {
        setRenderMillis(200);
        setClassName("GridRow");
        setTag("*");
    }

    public GridRow(WebLocator container, String elPath) {
        this();
        setContainer(container);
        setElPath(elPath);
    }

    public GridRow(GridPanel gridPanel) {
        this();
        setContainer(gridPanel);
        setElPath("//div" + GRID_ROW_PATH);
    }

    public GridRow(GridPanel gridPanel, int rowIndex) {
        this(gridPanel);
        setElPath("//div[" + rowIndex + "]" + GRID_ROW_PATH);
    }

    public GridRow(GridPanel gridPanel, int searchColumnIndex, String searchElement, SearchType searchType) {
        this(gridPanel, new GridCell(searchColumnIndex, searchElement, searchType));
    }

    public GridRow(GridPanel gridPanel, String searchColumnId, String searchElement, SearchType searchType) {
        this(gridPanel);
        GridCell cell = new GridCell(null, searchElement, searchType);
        setElPath("//" + getTag() + "[" + getSearchPath(searchColumnId, Utils.fixPathSelector(cell.getItemPathText())) + "]");
    }

    public GridRow(GridPanel gridPanel, Cell... cells) {
        this(gridPanel);
        setRowCells(cells);
    }

    private String getSearchPath(String searchColumnId, String textCondition) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]//*[" + textCondition + "]) > 0";
    }

    protected String getSearchPath(int columnIndex, String textCondition) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//td[" + columnIndex + "]//*[" + textCondition + "]) > 0";
    }

    private String getSearchPath(int columnIndex, GridCell cell) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//td[" + columnIndex + "]" + cell.getPath() + ") > 0";
    }

    public GridRow(GridPanel gridPanel, int rowIndex, boolean isSelected) {
        this(gridPanel, rowIndex);
        if (isSelected) {
            setElPath("//div[" + rowIndex + "]" + GRID_ROW_SELECTED_PATH);
        }
    }
}
