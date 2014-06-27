package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TableRow extends Row {
    private static final Logger logger = Logger.getLogger(TableRow.class);

    public TableRow() {
        setRenderMillis(200);
        setClassName("TableRow");
        setTag("tr");
        defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public TableRow(WebLocator container) {
        this();
        setContainer(container);
    }

    public TableRow(WebLocator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    public TableRow(WebLocator table, String searchElement, SearchType searchType) {
        this(table);
        setText(searchElement);
        setSearchTextType(searchType);
    }

    public TableRow(WebLocator table, Cell... cells) {
        this(table);
        setRowCells(cells);
    }

    public TableRow(WebLocator table, int indexRow, Cell... cells) {
        this(table, indexRow);
        setRowCells(cells);
    }
}
