package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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
        AbstractCell[] childNodes = Stream.of(cells).filter(t -> t != null && (t.getPathBuilder().getText() != null || (t.getPathBuilder().getChildNodes() != null && !t.getPathBuilder().getChildNodes().isEmpty()))).toArray(AbstractCell[]::new);
        setChildNodes(childNodes);
    }

    public Row(WebLocator table, int indexRow, AbstractCell... cells) {
        this(table, cells);
        setPosition(indexRow);
    }

    public Cell getCell(int columnIndex) {
        return new Cell(this, columnIndex);
    }

    @Override
    public int getCells() {
        return new Cell(this).size();
    }

    public List<String> getCellsText(int... excludedColumns) {
        List<Integer> columns = getColumns(excludedColumns);
        List<String> list = new ArrayList<>();
        for (int j : columns) {
            Cell cell = new Cell(this, j);
            list.add(cell.getText().trim());
        }
        return list;
    }

    /**
     * add in V class this: @JsonInclude(JsonInclude.Include.NON_NULL)
     */
    public <V> V getCellsValues(V type, int... excludedColumns) {
        List<String> actualList = getCellsText(excludedColumns);
        return transformToObject(type, actualList);
    }

    @Override
    public int getHeadersCount() {
        WebLocator columnsEl = new WebLocator(this).setTag("td");
        return columnsEl.size();
    }
}
