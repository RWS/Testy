package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableViewerCell extends TableCell {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableViewerCell.class);

    public TableViewerCell() {
        setRenderMillis(200);
        setTag("div");
        defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public TableViewerCell(WebLocator container) {
        this();
        setContainer(container);
    }

    public TableViewerCell(WebLocator container, int columnIndex) {
        this(container);
        setPosition(columnIndex);
    }

    public TableViewerCell(int columnIndex, String columnText, SearchType... searchType) {
        this();
        setPosition(columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    public TableViewerCell(WebLocator container, int columnIndex, String columnText, SearchType... searchType) {
        this(container, columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    @Override
    protected String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            int beginIndex = 2 + getTag().length();
            itemPath = "//" + getTag() + "[" + getPosition() + "]" + itemPath.substring(beginIndex);
        }
        return itemPath;
    }
}
