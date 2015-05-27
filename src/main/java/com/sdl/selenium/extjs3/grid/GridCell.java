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

    public GridCell(int columnIndex) {
        this();
        setElPath("//td[" + columnIndex + "]//*[contains(@class, 'x-grid3-cell-inner')]");
        setInfoMessage("td[" + columnIndex + "]//x-grid3-cell-inner");
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

    public GridCell(int columnIndex, String columnText, SearchType... searchType) {
        this();
        setPosition(columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
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
