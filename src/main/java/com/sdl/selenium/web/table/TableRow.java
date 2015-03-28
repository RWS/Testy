package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TableRow extends Row {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableRow.class);

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
        setChildNotes(cells);
    }

    public TableRow(WebLocator table, int indexRow, Cell... cells) {
        this(table, indexRow);
        setChildNotes(cells);
    }

    public List<String> getCellsText() {
        WebLocator columnsEl = new WebLocator(this).setTag("td");
        int columns = columnsEl.size() + 1;

        List<String> list = new ArrayList<String>();
        for (int j = 1; j < columns; j++) {
            TableCell cell = new TableCell(this, j);
            list.add(cell.getHtmlText());
        }
        return list;
    }
}
