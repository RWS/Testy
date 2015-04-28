package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridRow extends Row {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridRow.class);

    public GridRow(By... bys) {
        getPathBuilder().defaults(By.tag("*")).init(bys);
        setRenderMillis(200);
    }

    @Deprecated
    public GridRow(WebLocator container, String elPath) {
        this(By.container(container), By.xpath(elPath));
    }

    public GridRow(GridPanel gridPanel) {
        this(By.container(gridPanel), By.tag("div"), By.classes("x-grid3-row"), By.excludeClasses("x-grid3-row-checker"));
    }

    public GridRow(GridPanel gridPanel, int rowIndex) {
        this(By.container(gridPanel), By.tag("div"), By.tagIndex(rowIndex), By.classes("x-grid3-row"), By.excludeClasses("x-grid3-row-checker"));
    }

    public GridRow(GridPanel gridPanel, int searchColumnIndex, String searchElement, SearchType searchType) {
        this(gridPanel, new GridCell(searchColumnIndex, searchElement, searchType));
    }

    public GridRow(GridPanel gridPanel, String searchColumnId, String searchElement, SearchType searchType) {
        this(gridPanel);
        setTag("*");
        WebLocator cellEl = new WebLocator(By.text(searchElement, searchType));
        setElPath("//" + getTag() + "[" + getSearchPaths(searchColumnId, cellEl) + "]");
    }

    public GridRow(GridPanel gridPanel, Cell... cells) {
        this(By.container(gridPanel), By.classes("x-grid3-row"), By.excludeClasses("x-grid3-row-checker"), By.tag("*"), By.childNodes(cells));
    }

    private String getSearchPaths(String searchColumnId, WebLocator cellEl) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]" + cellEl.getPath() + ") > 0";
    }

    public GridRow(GridPanel gridPanel, int rowIndex, boolean isSelected) {
        this(By.container(gridPanel), By.tag("div"), By.tagIndex(rowIndex),
                (isSelected ? By.classes("x-grid3-row-selected") : By.classes("x-grid3-row")),
                By.excludeClasses("x-grid3-row-checker"));
    }
}
