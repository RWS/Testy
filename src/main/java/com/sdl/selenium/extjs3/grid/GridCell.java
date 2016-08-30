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
        withRenderMillis(200);
        withClassName("GridCell");
    }

    public GridCell(int columnIndex) {
        this();
        withElxPath("//td[" + columnIndex + "]//*[contains(@class, 'x-grid3-cell-inner')]");
        withInfoMessage("td[" + columnIndex + "]//x-grid3-cell-inner");
    }

    public GridCell(String text, WebLocator container) {
        this();
        withContainer(container);
        withText(text);
        withClasses("x-grid3-cell-inner");
    }

    public GridCell(String text, SearchType... searchTypes) {
        this();
        withText(text, searchTypes);
        withClasses("x-grid3-cell-inner");
    }

    public GridCell(WebLocator container, String text, SearchType... searchTypes) {
        this();
        withContainer(container);
        withText(text, searchTypes);
        withClasses("x-grid3-cell-inner");
    }

    public GridCell(int columnIndex, String columnText, SearchType... searchTypes) {
        this();
        withPosition(columnIndex);
        withText(columnText, searchTypes);
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
