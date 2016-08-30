package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import com.sdl.selenium.web.table.AbstractRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridRow extends AbstractRow {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridRow.class);

    public GridRow() {
        withRenderMillis(200);
        withClassName("GridRow");
        withTag("*");
    }

    //TODO generated standard xpath  independent of constructors
    public GridRow(GridPanel gridPanel) {
        this();
        withContainer(gridPanel);
        withTag("div");
        withClasses("x-grid3-row");
        withExcludeClasses("x-grid3-row-checker");
    }

    public GridRow(GridPanel gridPanel, int rowIndex) {
        withContainer(gridPanel);
        withTag("div[" + rowIndex + "]");
        withClasses("x-grid3-row");
        withExcludeClasses("x-grid3-row-checker");
    }

    public GridRow(GridPanel gridPanel, int searchColumnIndex, String searchElement, SearchType... searchTypes) {
        this(gridPanel, new GridCell(searchColumnIndex, searchElement, searchTypes));
    }

    public GridRow(GridPanel gridPanel, String searchColumnId, String searchElement, SearchType... searchTypes) {
        this(gridPanel);
        withTag("*");
        WebLocator cellEl = new WebLocator().withText(searchElement, searchTypes);
        withElxPath("//" + getPathBuilder().getTag() + "[" + getSearchPaths(searchColumnId, cellEl) + "]");
    }

    public GridRow(GridPanel gridPanel, AbstractCell... cells) {
        this(gridPanel);
        withTag("*");
        withChildNodes(cells);
    }

    private String getSearchPaths(String searchColumnId, WebLocator cellEl) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]" + cellEl.getXPath() + ") > 0";
    }

    public GridRow(GridPanel gridPanel, int rowIndex, boolean isSelected) {
        this(gridPanel, rowIndex);
        if (isSelected) {
            withTag("div[" + rowIndex + "]");
            withClasses("x-grid3-row-selected");
            withExcludeClasses("x-grid3-row-checker");
        }
    }
}
