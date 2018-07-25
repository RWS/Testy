package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid extends Table implements Scrollable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Grid.class);

    public Grid() {
        setClassName("Grid");
        setBaseCls("x-grid");
        setTag("*");
        WebLocator header = new WebLocator().setClasses("x-title").setRoot("//");
        setTemplateTitle(new WebLocator(header));
    }

    public Grid(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     * <pre>{@code
     * Grid grid = new Grid().setHeaders("Company", "Price", "Change");
     * }</pre>
     *
     * @param headers grid's headers in order
     * @param <T>     element which extended the Grid
     * @return this Grid
     */
    public <T extends Table> T setHeaders(final String... headers) {
        List<WebLocator> list = new ArrayList<>();
        for (int i = 0; i < headers.length; i++) {
            WebLocator headerEL = new WebLocator(this).setTag("*[" + (i + 1) + "]").setClasses("x-column-header").
                    setText(headers[i], SearchType.DEEP_CHILD_NODE_OR_SELF, SearchType.EQUALS);
            list.add(headerEL);
        }
        setChildNodes(list.toArray(new WebLocator[list.size()]));
        return (T) this;
    }

    @Override
    public Row getRow(int rowIndex) {
        return new Row(this, rowIndex).setInfoMessage("-Row");
    }

    public Group getGroup(String groupName) {
        return new Group(this, groupName).setInfoMessage("-Group");
    }

    public Group getGroup(int rowIndex) {
        return new Group(this, rowIndex).setInfoMessage("-Group");
    }

    @Override
    public Row getRow(String searchElement) {
        return new Row(this, searchElement, SearchType.EQUALS).setInfoMessage("-Row");
    }

    @Override
    public Row getRow(String searchElement, SearchType... searchTypes) {
        return new Row(this, searchElement, searchTypes).setInfoMessage("-Row");
    }

    public Row getRow(Cell... byCells) {
        return new Row(this, byCells).setInfoMessage("-Row");
    }

    public Row getRow(int indexRow, Cell... byCells) {
        return new Row(this, indexRow, byCells).setInfoMessage("-Row");
    }

    @Override
    public Cell getCell(int rowIndex, int columnIndex) {
        Row row = getRow(rowIndex);
        return new Cell(row, columnIndex).setInfoMessage("cell - Table");
    }

    @Override
    public Cell getCell(String searchElement, SearchType... searchTypes) {
        Row row = new Row(this);
        return new Cell(row).setText(searchElement, searchTypes);
    }

    public Cell getCell(int rowIndex, int columnIndex, String text) {
        Row row = getRow(rowIndex);
        return new Cell(row, columnIndex, text, SearchType.EQUALS);
    }

    public Cell getCell(String searchElement, String columnText, SearchType... searchTypes) {
        Row row = getRow(searchElement, SearchType.CONTAINS);
        return new Cell(row).setText(columnText, searchTypes);
    }

    @Override
    public Cell getCell(String searchElement, int columnIndex, SearchType... searchTypes) {
        return new Cell(new Row(this, searchElement, searchTypes), columnIndex);
    }

    public Cell getCell(int columnIndex, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex);
    }

    public Cell getCell(int columnIndex, String text, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    public boolean waitToActivate(int seconds) {
        String info = toString();
        int count = 0;
        boolean hasMask;
        while ((hasMask = hasMask()) && (count < seconds)) {
            count++;
            LOGGER.info("waitToActivate:" + (seconds - count) + " seconds; " + info);
            Utils.sleep(900);
        }
        return !hasMask;
    }

    private boolean hasMask() {
        WebLocator mask = new WebLocator(this).setClasses("x-mask").setElPathSuffix("style", "not(contains(@style, 'display: none'))").setAttribute("aria-hidden", "false").setInfoMessage("Mask");
        return mask.waitToRender(500, false);
    }

    @Override
    public boolean waitToPopulate(int seconds) {
        Row row = getRow(1).setVisibility(true).setRoot("//..//").setInfoMessage("first Row");
        WebLocator body = new WebLocator(this).setClasses("x-grid-header-ct"); // TODO see if must add for all rows
        row.setContainer(body);
        return row.waitToRender(seconds * 1000L);
    }

    public List<String> getHeaders() {
        List<String> headers = new ArrayList<>();
        WebLocator header = new WebLocator(this).setClasses("x-grid-header-ct");
        String headerText = RetryUtils.retryWithSuccess(4, header::getText);
        Collections.addAll(headers, headerText.trim().split("\n"));
        return headers;
    }

    @Override
    public List<List<String>> getCellsText(int... excludedColumns) {
        Row rowsEl = new Row(this);
        Row rowEl = new Row(this, 1);
        Cell columnsEl = new Cell(rowEl);
        int rows = rowsEl.size();
        int columns = columnsEl.size();
        List<Integer> columnsList = getColumns(columns, excludedColumns);

        if (rows <= 0) {
            return null;
        } else {
            List<List<String>> listOfList = new ArrayList<>();
            boolean canRead = true;
            String id = "";
            int timeout = 0;
            do {
                for (int i = 1; i <= rows; ++i) {
                    if (canRead) {
                        List<String> list = new ArrayList<>();
                        for (int j : columnsList) {
                            list.add(this.getCell(i, j).getText(true));
                        }
                        listOfList.add(list);
                    } else {
                        String currentId = new Row(this, i).getAttributeId();
                        if (!"".equals(id) && id.equals(currentId)) {
                            canRead = true;
                        }
                    }
                }
                if (isScrollBottom()) {
                    break;
                }
                id = new Row(this, rows).getAttributeId();
                scrollPageDownInTree();
                canRead = false;
                timeout++;
            } while (timeout < 30);
            return listOfList;
        }
    }

    @Override
    public int getCount() {
        if (ready(true)) {
            return new Row(this).size();
        } else {
            return -1;
        }
    }
}
