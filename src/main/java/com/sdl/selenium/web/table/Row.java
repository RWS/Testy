package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Row extends AbstractRow {

    public Row() {
        setRender(Duration.ofMillis(200));
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
        setChildNodes(Stream.of(cells).filter(Objects::nonNull).toArray(AbstractCell[]::new));
    }

    public Row(WebLocator table, int indexRow, AbstractCell... cells) {
        this(table, cells);
        setPosition(indexRow);
    }

    public Cell getCell(int columnIndex) {
        return new Cell(this, columnIndex);
    }

    public List<String> getCellsText(int... excludedColumns) {
        WebLocator columnsEl = new WebLocator(this).setTag("td");
        List<Integer> columns = getColumns(columnsEl.size(), excludedColumns);
        List<String> list = new ArrayList<>();
        for (int j : columns) {
            Cell cell = new Cell(this, j);
            list.add(cell.getText().trim());
        }
        return list;
    }
}
