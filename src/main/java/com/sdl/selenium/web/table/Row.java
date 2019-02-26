package com.sdl.selenium.web.table;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Row extends AbstractRow {

    public Row() {
        setRenderMillis(200);
        setClassName("Row");
        setTag("tr");
        getXPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Row(Locator container) {
        this();
        setContainer(container);
    }

    public Row(Locator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    public Row(Locator table, String searchElement, SearchType... searchTypes) {
        this(table);
        setText(searchElement, searchTypes);
    }

    public Row(Locator table, AbstractCell... cells) {
        this(table);
        setChildNodes(Stream.of(cells).filter(t -> t.getXPathBuilder().getText() != null).toArray(AbstractCell[]::new));
    }

    public Row(Locator table, int indexRow, AbstractCell... cells) {
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

    protected List<Integer> getColumns(int columns, int[] excludedColumns) {
        List<Integer> excluded = new ArrayList<>();
        for (int excludedColumn : excludedColumns) {
            excluded.add(excludedColumn);
        }

        List<Integer> columnsList = new ArrayList<>();
        for (int i = 1; i <= columns; i++) {
            if (!excluded.contains(i)) {
                columnsList.add(i);
            }
        }
        return columnsList;
    }

    @Override
    public String getText() {
        return executor().getText(this);
    }
}
