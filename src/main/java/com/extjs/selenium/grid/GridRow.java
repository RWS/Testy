package com.extjs.selenium.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Row;
import org.apache.log4j.Logger;

public class GridRow extends Row {
    private static final Logger logger = Logger.getLogger(GridRow.class);

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
        setTag("div");
        setClasses("x-grid3-row");
        setExcludeClasses("x-grid3-row-checker");
    }

    public GridRow(GridPanel gridPanel, int rowIndex) {
        setContainer(gridPanel);
        setTag("div[" + rowIndex + "]");
        setClasses("x-grid3-row");
        setExcludeClasses("x-grid3-row-checker");
    }

    public GridRow(GridPanel gridPanel, int searchColumnIndex, String searchElement, SearchType searchType) {
        this(gridPanel, new GridCell(searchColumnIndex, searchElement, searchType));
    }

    public GridRow(GridPanel gridPanel, String searchColumnId, String searchElement, SearchType searchType) {
        this(gridPanel);
        setTag("*");
        WebLocator cellEl = new WebLocator().setText(searchElement, searchType);
        setElPath("//" + getTag() + "[" + getSearchPaths(searchColumnId, cellEl) + "]");
    }

    public GridRow(GridPanel gridPanel, Cell... cells) {
        this(gridPanel);
        setTag("*");
        setRowCells(cells);
    }

    private String getSearchPaths(String searchColumnId, WebLocator cellEl) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]" + cellEl.getPath() + ") > 0";
    }

    @Override
    protected String getSearchPath(int columnIndex, WebLocator cellEl, String tag) {
        if (tag == null || "".equals(tag) || "*".equals(tag)) {
            tag = "td";
        }
        return "count(*[contains(@class, 'x-grid3-row-table')]//" + tag + "[" + columnIndex + "]" + cellEl.getPath() + ") > 0";
    }

    private String getSearchPath(int columnIndex, GridCell cell) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//td[" + columnIndex + "]" + cell.getPath() + ") > 0";
    }

    public GridRow(GridPanel gridPanel, int rowIndex, boolean isSelected) {
        this(gridPanel, rowIndex);
        if (isSelected) {
            setTag("div[" + rowIndex + "]");
            setClasses("x-grid3-row-selected");
            setExcludeClasses("x-grid3-row-checker");
        }
    }
}
