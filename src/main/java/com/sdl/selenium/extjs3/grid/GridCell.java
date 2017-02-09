package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import com.sdl.selenium.web.table.AbstractCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridCell extends AbstractCell {
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

    public GridCell(String text, SearchType... searchTypes) {
        this();
        setText(text, searchTypes);
        setClasses("x-grid3-cell-inner");
    }

    public GridCell(WebLocator container, String text, SearchType... searchTypes) {
        this();
        setContainer(container);
        setText(text, searchTypes);
        setClasses("x-grid3-cell-inner");
    }

    public GridCell(int columnIndex, String columnText, SearchType... searchTypes) {
        this();
        setPosition(columnIndex);
        setText(columnText, searchTypes);
    }

    protected XPathBuilder createXPathBuilder() {
        return new XPathBuilder() {
            @Override
            protected String addPositionToPath(String itemPath) {
                if (hasPosition()) {
                    itemPath = "//td[" + getPosition() + "]" + itemPath;
                }
                return itemPath;
            }
        };
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
