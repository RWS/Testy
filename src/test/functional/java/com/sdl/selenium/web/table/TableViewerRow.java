package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableViewerRow extends TableRow {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableViewerRow.class);

    public TableViewerRow() {
        setRenderMillis(200);
        setTag("div");
        setTemplate("table-row", "contains(@style, '%s')");
        setTemplateValue("table-row", "width: 976px; height: 27px; left: 0px;");
    }

    public TableViewerRow(WebLocator container) {
        this();
        setContainer(container);
    }

    public TableViewerRow(WebLocator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    public TableViewerRow(WebLocator table, String searchElement, SearchType searchType) {
        this(table);
        setText(searchElement);
        setSearchTextType(searchType);
    }

    public TableViewerRow(WebLocator table, Cell... cells) {
        this(table);
        setChildNotes(cells);
    }

    public TableViewerRow(WebLocator table, int indexRow, Cell... cells) {
        this(table, indexRow);
        setChildNotes(cells);
    }
}
