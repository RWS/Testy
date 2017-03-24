package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Row extends AbstractRow {
    private static final Logger LOGGER = LoggerFactory.getLogger(Row.class);

    public Row() {
        setRenderMillis(200);
        setClassName("Row");
        setTag("tr");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Row(WebLocator container) {
        this();
        setContainer(container);
    }

    public Row(WebLocator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    public Row(WebLocator table, String searchElement, SearchType... searchTypes) {
        this(table);
        setText(searchElement, searchTypes);
    }

    public Row(WebLocator table, AbstractCell... cells) {
        this(table);
        setChildNodes(cells);
    }

    public Row(WebLocator table, int indexRow, AbstractCell... cells) {
        this(table, indexRow);
        setChildNodes(cells);
    }

    public Cell getCell(int columnIndex) {
        return new Cell(this, columnIndex);
    }

    public List<String> getCellsText() {
        WebLocator columnsEl = new WebLocator(this).setTag("td");
        int columns = columnsEl.size() + 1;

        List<String> list = new ArrayList<>();
        for (int j = 1; j < columns; j++) {
            Cell cell = new Cell(this, j);
            list.add(cell.getText());
        }
        return list;
    }
}
