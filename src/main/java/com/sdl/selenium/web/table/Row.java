package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<AbstractCell> collect = Stream.of(cells).filter(t -> t.getPathBuilder().getText() != null).collect((Collectors.toList()));
        setChildNodes(collect.toArray(new AbstractCell[collect.size()]));
    }

    public Row(WebLocator table, int indexRow, AbstractCell... cells) {
        this(table, cells);
        setPosition(indexRow);
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
            list.add(cell.getText().trim());
        }
        return list;
    }
}
