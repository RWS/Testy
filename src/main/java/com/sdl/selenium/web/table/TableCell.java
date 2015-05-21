package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableCell extends Cell {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableCell.class);

    public TableCell() {
        setRenderMillis(200);
        setClassName("TableCell");
        setTag("td");
        defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public TableCell(WebLocator container) {
        this();
        setContainer(container);
    }

    public TableCell(WebLocator container, int columnIndex) {
        this(container);
        setPosition(columnIndex);
    }

    public TableCell(int columnIndex, String columnText, SearchType... searchType) {
        this();
        setPosition(columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    public TableCell(WebLocator container, int columnIndex, String columnText, SearchType... searchType) {
        this(container, columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    @Override
    protected String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            int beginIndex = 2 + getTag().length();
            itemPath = getRoot() + getTag() + "[" + getPosition() + "]" + itemPath.substring(beginIndex);
        }
        return itemPath;
    }
}
