package com.extjs.selenium.grid;

import com.sdl.selenium.extjs3.grid.GridPanel;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class GridRow extends com.sdl.selenium.extjs3.grid.GridRow {
    public GridRow() {
    }

    public GridRow(WebLocator container, String elPath) {
        super(container, elPath);
    }

    public GridRow(com.sdl.selenium.extjs3.grid.GridPanel gridPanel) {
        super(gridPanel);
    }

    public GridRow(GridPanel gridPanel, int rowIndex) {
        super(gridPanel, rowIndex);
    }

    public GridRow(GridPanel gridPanel, int searchColumnIndex, String searchElement, SearchType searchType) {
        super(gridPanel, searchColumnIndex, searchElement, searchType);
    }

    public GridRow(GridPanel gridPanel, String searchColumnId, String searchElement, SearchType searchType) {
        super(gridPanel, searchColumnId, searchElement, searchType);
    }

    public GridRow(GridPanel gridPanel, Cell... cells) {
        super(gridPanel, cells);
    }

    public GridRow(GridPanel gridPanel, int rowIndex, boolean isSelected) {
        super(gridPanel, rowIndex, isSelected);
    }
}
