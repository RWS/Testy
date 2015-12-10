package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.CheckBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Table extends WebLocator implements ITable<Row, Cell> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Table.class);

    private int timeout = 30;

    public Table() {
        setClassName("Table");
        setTag("table");
    }

    public Table(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public boolean rowSelect(String searchText) {
        return rowSelect(searchText, SearchType.EQUALS);
    }

    @Override
    public boolean rowSelect(String searchText, SearchType... searchType) {
        ready(true);
        Cell cell = getCell(searchText, searchType);
        return doCellSelect(cell);
    }

    private boolean doCellSelect(Cell cell) {
        return doCellAction(cell, null);
    }

    private boolean doCellDoubleClickAt(Cell cell) {
        return doCellAction(cell, "doubleClickAt");
    }

    private boolean doCellAction(Cell cell, String action) {
        boolean selected;
        if ("doubleClickAt".equals(action)) {
            selected = cell.doubleClickAt();
        } else {
            selected = cell.click();
        }
        if (selected) {
            LOGGER.info("The element '" + cell + "' has been located.");
        } else {
            LOGGER.warn("The element '" + cell + "' is not present in the list.");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Path's element is: " + cell.getXPath());
                LOGGER.debug("Total Rows: " + getCount());
            }
        }
        return selected;
    }

    /**
     * returns if a table contains a certain element
     *
     * @param searchElement the searchElement of the table element on which the search is done
     * @return if a table contains a certain element
     */
    public boolean isRowPresent(String searchElement) {
        ready();
        Cell cell = getCell(searchElement);
        return cell.isElementPresent();
    }

    public Number getRowCount(String searchElement, SearchType searchType) {
        ready();
        Cell cell = getCell(searchElement, searchType);
        return cell.size();
    }

    public Number getRowCount(String searchElement) {
        return getRowCount(searchElement, SearchType.STARTS_WITH);
    }

    @Override
    public int getCount() {
        if (ready()) {
            WebLocator body = new WebLocator(this).setTag("tbody");
            return new Row(body).size();
        } else {
            LOGGER.warn("table is not ready to be used");
            // TODO could try to verify row count with mask on table or when is disabled also.
            return -1;
        }
    }

    /**
     * @deprecated use {@link #getRow(int)}
     */
    @Override
    public TableRow getRowLocator(int rowIndex) {
        return new TableRow(this, rowIndex).setInfoMessage("row - Table");
    }

    public Row getRow(int rowIndex) {
        return new Row(this, rowIndex).setInfoMessage("row - Table");
    }

    /**
     * @deprecated use {@link #getRow(String)}
     */
    public TableRow getTableRow(String searchElement) {
        return new TableRow(this, searchElement, SearchType.EQUALS);
    }

    public Row getRow(String searchElement) {
        return new Row(this, searchElement, SearchType.EQUALS);
    }

    /**
     * @deprecated use {@link #getRow(String, SearchType)}
     */
    public TableRow getTableRow(String searchElement, SearchType searchType) {
        return new TableRow(this, searchElement, searchType);
    }

    public Row getRow(String searchElement, SearchType searchType) {
        return new Row(this, searchElement, searchType);
    }

    @Override
    public TableCell getCell(int rowIndex, int columnIndex) {
        Row row = getRowLocator(rowIndex);
        return new TableCell(row, columnIndex).setInfoMessage("cell - Table");
    }

    @Override
    public TableCell getCell(String searchElement) {
        return getCell(searchElement, SearchType.EQUALS);
    }

    @Override
    public TableCell getCell(String searchElement, SearchType ...searchType) {
        Row row = new Row(this);
        return new TableCell(row).setText(searchElement, searchType);
    }

    /**
     * @deprecated use {@link #getCell(int, int, String)}
     */
    public TableCell getTableCell(int rowIndex, int columnIndex, String text) {
        Row row = getRowLocator(rowIndex);
        String selector = new Cell().setText(text, SearchType.EQUALS).getXPath();
        return new TableCell(row).setElPath(selector + "[" + columnIndex + "]");
    }

    public Cell getCell(int rowIndex, int columnIndex, String text) {
        Row row = getRowLocator(rowIndex);
        String selector = new Cell().setText(text, SearchType.EQUALS).getXPath();
        return new Cell(row).setElPath(selector + "[" + columnIndex + "]");
    }

    /**
     * @deprecated use {@link #getCell(String, String, SearchType)}
     */
    public TableCell getTableCell(String searchElement, String columnText, SearchType searchType) {
        Row row = getTableRow(searchElement, SearchType.CONTAINS);
        return new TableCell(row).setText(columnText, searchType);
    }

    public Cell getCell(String searchElement, String columnText, SearchType searchType) {
        Row row = getTableRow(searchElement, SearchType.CONTAINS);
        return new Cell(row).setText(columnText, searchType);
    }

    /**
     * @deprecated use {@link #getCell(String, int, SearchType)}
     */
    public TableCell getTableCell(String searchElement, int columnIndex, SearchType searchType) {
        return new TableCell(new Row(this, searchElement, searchType), columnIndex);
    }

    public Cell getCell(String searchElement, int columnIndex, SearchType searchType) {
        return new Cell(new Row(this, searchElement, searchType), columnIndex);
    }

    /**
     * @deprecated use {@link #getRow(Cell...)}
     */
    public TableRow getRow(TableCell... byCells) {
        return new TableRow(this, byCells).setInfoMessage("-TableRow");
    }

    @Override
    public Row getRow(Cell... byCells) {
        return new Row(this, byCells).setInfoMessage("-Row");
    }

    /**
     * @deprecated use {@link #getRow(int, Cell...)}
     */
    public TableRow getRow(int indexRow, TableCell... byCells) {
        return new TableRow(this, indexRow, byCells).setInfoMessage("-TableRow");
    }

    public Row getRow(int indexRow, Cell... byCells) {
        return new Row(this, indexRow, byCells).setInfoMessage("-Row");
    }

    /**
     * @deprecated use {@link #getCell(int, Cell...)}
     */
    public TableCell getCell(int columnIndex, TableCell... byCells) {
        return new TableCell(getRow(byCells), columnIndex);
    }

    @Override
    public Cell getCell(int columnIndex, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex);
    }

    /**
     * @deprecated use {@link #getCell(int, String, Cell...)}
     */
    public TableCell getCell(int columnIndex, String text, TableCell... byCells) {
        return new TableCell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    @Override
    public Cell getCell(int columnIndex, String text, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    /**
     * returns all text elements from a table
     *
     * @param searchText searchText
     * @return all text elements from a table
     * @deprecated //TODO fix it
     */

    public String[] getRowText(String searchText) {
        String[] rowElements = null;
        String text = getRow(searchText).getHtmlText();
        if (text != null) {
            rowElements = text.split("\n");
        }
        return rowElements;
    }

    /**
     * get all strings as array from specified columnIndex
     *
     * @param columnIndex columnIndex
     * @return all strings as array from specified columnIndex
     */
    public String[] getCollTexts(int columnIndex) {
        int count = getCount();
        if (count > 0) {
            String[] texts = new String[count];
            for (int i = 1; i <= count; i++) {
                texts[i - 1] = getText(i, columnIndex);
            }
            return texts;
        } else {
            return null;
        }
    }

    public List<List<String>> getCellsText() {
        WebLocator parentEl = new WebLocator(this).setTag("tbody");
        Row rowsEl = new Row(parentEl);
        Row rowEl = new Row(parentEl, 1);
        Cell columnsEl = new Cell(rowEl);
        int rows = rowsEl.size() + 1;
        int columns = columnsEl.size() + 1;

        if (rows > 0) {
            List<List<String>> listOfList = new ArrayList<>();
            for (int i = 1; i < rows; i++) {
                List<String> list = new ArrayList<>();
                for (int j = 1; j < columns; j++) {
                    list.add(getCell(i, j).getHtmlText());
                }
                listOfList.add(list);
            }
            return listOfList;
        } else {
            return null;
        }
    }

    public String getText(String searchText, int columnId) {
        String text = null;
        Row row = new Row(this, searchText, SearchType.EQUALS);
        if (row.ready()) {
            text = new Cell(row, columnId).getHtmlText();
        } else {
            LOGGER.warn("searchText was not found in table: " + searchText);
        }
        return text;
    }

    public String getText(int rowIndex, int columnIndex) {
        return getCell(rowIndex, columnIndex).getHtmlText();
    }

    /**
     * returns if a specific Table contains a certain element
     *
     * @param searchText  the element that is already part of the table
     * @param columnIndex the column index where the comparison is done (STARTS AT 0)
     * @param compareText the text to which the element found is compared to
     * @return if a specific Table contains a certain element
     */
    public boolean isTextPresent(String searchText, int columnIndex, String compareText) {
        String text = getText(searchText, columnIndex);
        return text != null && text.trim().equals(compareText);
    }

    public boolean checkboxColumnSelect(String searchText, int columnIndex) {
        return checkboxColumnSelect(searchText, columnIndex, SearchType.EQUALS);
    }

    public boolean checkboxColumnSelect(String searchText, int columnIndex, SearchType searchType) {
        boolean selected = false;
        if (ready(true)) {
            CheckBox checkBox = new CheckBox().setContainer(getTableCell(searchText, columnIndex, searchType));
            selected = checkBox.isSelected() || checkBox.click();
        }
        return selected;
    }

    public boolean checkboxColumnDeselect(String searchText, int columnIndex) {
        return checkboxColumnDeselect(searchText, columnIndex, SearchType.EQUALS);
    }

    public boolean checkboxColumnDeselect(String searchText, int columnIndex, SearchType searchType) {
        boolean selected = false;
        if (ready(true)) {
            CheckBox checkBox = new CheckBox().setContainer(getTableCell(searchText, columnIndex, searchType));
            selected = !checkBox.isSelected() || checkBox.click();
        }
        return selected;
    }

    public boolean waitToPopulate() {
        return waitToPopulate(timeout);
    }

    public boolean waitToPopulate(int seconds) {
        Row row = getRow(1).setVisibility(true).setInfoMessage("first Row");
        WebLocator body = new WebLocator(this).setTag("tbody"); // TODO see if must add for all rows
        row.setContainer(body);
        return row.waitToRender(seconds * 1000L);
    }

    public boolean ready(boolean waitRows) {
        return ready() && (!waitRows || waitToPopulate());
    }
}
