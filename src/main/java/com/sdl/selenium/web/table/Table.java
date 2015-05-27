package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.CheckBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Table extends WebLocator implements ITable<TableRow, TableCell> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Table.class);

    private int timeout = 30;

    public Table() {
        setClassName("SimpleTable");
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
        TableCell cell = getCell(searchText, searchType);
        return doCellSelect(cell);
    }

    private boolean doCellSelect(TableCell tableCell) {
        return doCellAction(tableCell, null);
    }

    private boolean doCellDoubleClickAt(TableCell tableCell) {
        return doCellAction(tableCell, "doubleClickAt");
    }

    private boolean doCellAction(WebLocator cell, String action) {
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
                LOGGER.debug("Path's element is: " + cell.getPath());
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
        boolean found;
        WebLocator cell = getCell(searchElement);
        found = cell.isElementPresent();
        return found;
    }

    public Number getRowCount(String searchElement, SearchType searchType) {
        ready();
        String rowPath = getCell(searchElement, searchType).getPath();
        WebLocator locator = new WebLocator().setElPath(rowPath);
        return locator.size();
    }

    public Number getRowCount(String searchElement) {
        return getRowCount(searchElement, SearchType.STARTS_WITH);
    }

    @Override
    public int getCount() {
        if (ready()) {
            WebLocator body = new WebLocator(this).setTag("tbody");
            return new TableRow(body).size();
        } else {
            LOGGER.warn("table is not ready to be used");
            // TODO could try to verify row count with mask on table or when is disabled also.
            return -1;
        }
    }

    @Override
    public TableRow getRowLocator(int rowIndex) {
        return new TableRow(this, rowIndex).setInfoMessage("row - Table");
    }

    public TableRow getTableRow(String searchElement) {
        return new TableRow(this, searchElement, SearchType.EQUALS);
    }

    public TableRow getTableRow(String searchElement, SearchType searchType) {
        return new TableRow(this, searchElement, searchType);
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
        WebLocator row = new WebLocator(this).setTag("tr");
        return new TableCell(row).setText(searchElement, searchType);
    }

    public TableCell getTableCell(int rowIndex, int columnIndex, String text) {
        Row row = getRowLocator(rowIndex);
        String selector = new WebLocator().setText(text, SearchType.EQUALS, SearchType.DEEP_CHILD_NODE_OR_SELF).setTag("td").getPath();
        return new TableCell(row).setElPath(selector + "[" + columnIndex + "]");
    }

    public TableCell getTableCell(String searchElement, String columnText, SearchType searchType) {
        TableRow tableRow = getTableRow(searchElement, SearchType.CONTAINS);
        return new TableCell(tableRow).setText(columnText, searchType);
    }

    public TableCell getTableCell(String searchElement, int columnIndex, SearchType searchType) {
        return new TableCell(new TableRow(this, searchElement, searchType), columnIndex);
    }

    @Override
    public TableRow getRow(TableCell... byCells) {
        return new TableRow(this, byCells).setInfoMessage("-TableRow");
    }

    public TableRow getRow(int indexRow, TableCell... byCells) {
        return new TableRow(this, indexRow, byCells).setInfoMessage("-TableRow");
    }

    public TableCell getCell(int columnIndex, TableCell... byCells) {
        return new TableCell(getRow(byCells), columnIndex);
    }

    /**
     * @deprecated use getCell(int columnIndex, TableCell... byCells)
     */
    public TableCell getTableCell(int columnIndex, TableCell... byCells) {
        return getCell(columnIndex, byCells);
    }

    public TableCell getCell(int columnIndex, String text, TableCell... byCells) {
        return new TableCell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    /**
     * @deprecated use getCell(int columnIndex, String text, TableCell... byCells)
     */
    public TableCell getTableCell(int columnIndex, String text, TableCell... byCells) {
        return getCell(columnIndex, text, byCells);
    }

    /**
     * returns all text elements from a table
     *
     * @param searchText searchText
     * @return all text elements from a table
     * @deprecated //TODO fix it
     */

    public String[] getRow(String searchText) {
        String[] rowElements = null;
        String text = getTableRow(searchText).getHtmlText();
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
        WebLocator rowsEl = new WebLocator(parentEl).setTag("tr");
        WebLocator rowEl = new WebLocator(parentEl).setTag("tr").setPosition(1);
        WebLocator columnsEl = new WebLocator(rowEl).setTag("td");
        int rows = rowsEl.size() + 1;
        int columns = columnsEl.size() + 1;

        if (rows > 0) {
            List<List<String>> listOfList = new ArrayList<List<String>>();
            for (int i = 1; i < rows; i++) {
                List<String> list = new ArrayList<String>();
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
        TableRow tableRow = new TableRow(this, searchText, SearchType.EQUALS);
        if (tableRow.ready()) {
            text = new TableCell(tableRow, columnId).getHtmlText();
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
        Row row = getRowLocator(1).setVisibility(true).setInfoMessage("first row");
        WebLocator body = new WebLocator(this).setTag("tbody"); // TODO see if must add for all rows
        row.setContainer(body);
        return row.waitToRender(seconds * 1000L);
    }

    public boolean ready(boolean waitRows) {
        return ready() && (!waitRows || waitToPopulate());
    }
}
