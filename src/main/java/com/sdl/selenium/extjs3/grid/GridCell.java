package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridCell extends Cell {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridCell.class);

    public GridCell() {
        setRenderMillis(200);
        setClassName("GridCell");
    }

    /**
     * @deprecated use other constructors
     */
    public GridCell(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     * @deprecated use other constructors
     */
    public GridCell(WebLocator container, String elPath) {
        this();
        setContainer(container);
        setElPath(elPath);
    }

    public GridCell(int columnIndex) {
        this();
        setElPath("//td[" + columnIndex + "]//*[contains(@class, 'x-grid3-cell-inner')]");
        setInfoMessage("td[" + columnIndex + "]//x-grid3-cell-inner");
    }

    /**
     * @deprecated use other constructors
     */
    public GridCell(WebLocator container, int columnIndex) {
        this(columnIndex);
        setContainer(container);
    }

    public GridCell(String text, WebLocator container) {
        this();
        setContainer(container);
        setText(text);
        setClasses("x-grid3-cell-inner");
    }

    public GridCell(String text, SearchType searchType) {
        this();
        setText(text, searchType);
        setClasses("x-grid3-cell-inner");
    }

    public GridCell(WebLocator container, String text, SearchType searchType) {
        this();
        setContainer(container);
        setText(text);
        setClasses("x-grid3-cell-inner");
        setSearchTextType(searchType);
    }

    public GridCell(int columnIndex, String columnText, SearchType searchType) {
        this();
        getPathBuilder().setTagIndex(columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    /**
     * @deprecated use other constructors
     */
    public GridCell(WebLocator container, int columnIndex, String columnText, SearchType searchType) {
        this(columnIndex, columnText, searchType);
        setContainer(container);
    }

    public boolean select() {
        try {
            return click();
        } catch (Exception e) {
            LOGGER.error("GridCell select ", e);
            return false;
        }
    }
}
