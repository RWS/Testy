package com.extjs.selenium.grid;

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

    public GridCell(WebLocator container) {
        this();
        setContainer(container);
    }

    public GridCell(WebLocator container, String elPath) {
        this(container);
        setElPath(elPath);
    }

    public GridCell(WebLocator container, int columnIndex) {
        this(container);
        setElPath("//td[" + columnIndex + "]//*[contains(@class, 'x-grid3-cell-inner')]");
        setInfoMessage("td[" + columnIndex + "]//x-grid3-cell-inner");
    }

    public GridCell(String text, WebLocator container) {
        this(container);
        setText(text);
        setClasses("x-grid3-cell-inner");
    }

    public GridCell(String text, SearchType searchType) {
        this();
        setText(text, searchType);
        setClasses("x-grid3-cell-inner");
    }

    public GridCell(WebLocator container, String text, SearchType searchType) {
        this(text, container);
        setSearchTextType(searchType);
    }

    public GridCell(int columnIndex, String columnText, SearchType searchType) {
        this();
        setPosition(columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    public GridCell(WebLocator container, int columnIndex, String columnText, SearchType searchType) {
        this(columnIndex, columnText, searchType);
        setContainer(container);
    }

    @Override
    protected String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            itemPath = "//td[" + getPosition() + "]" + itemPath;
        }
        return itemPath;
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
