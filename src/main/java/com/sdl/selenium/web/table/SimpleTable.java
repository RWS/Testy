package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleCheckBox;
import org.apache.log4j.Logger;

public class SimpleTable extends WebLocator implements ITable <TableRow, TableCell>{
    private static final Logger logger = Logger.getLogger(SimpleTable.class);

    private int timeout = 30;

    public SimpleTable() {
        setClassName("SimpleTable");
        setTag("table");
    }

    public SimpleTable(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public boolean rowSelect(String searchText) {
        return rowSelect(searchText, SearchType.EQUALS);
    }

    @Override
    public boolean rowSelect(String searchText, SearchType searchType) {
        ready(true);
        TableCell cell = getTableCell(searchText, searchType);
        return doCellSelect(cell);
    }

    @Deprecated
    private boolean doCellSelect(TableCell tableCell) {
        return doCellAction(tableCell, null);
    }

    @Deprecated
    private boolean doCellDoubleClickAt(TableCell tableCell) {
        return doCellAction(tableCell, "doubleClickAt");
    }

    @Deprecated
    private boolean doCellAction(WebLocator cell, String action) {
        boolean selected;
        if ("doubleClickAt".equals(action)) {
            selected = cell.doubleClickAt();
        } else {
            selected = cell.click();
        }
        if (selected) {
            logger.info("The element '" + cell + "' has been located.");
        } else {
            logger.warn("The element '" + cell + "' is not present in the list.");
            if (logger.isDebugEnabled()) {
                logger.debug("Path's element is: " + cell.getPath());
                logger.debug("Total Rows: " + getCount());
            }
        }
        return selected;
    }

    /**
     * returns if a table contains a certain element
     *
     * @param searchElement the searchElement of the table element on which the search is done
     * @return
     * @throws Exception
     */
    public boolean isRowPresent(String searchElement) {
        ready();
        boolean found;
        WebLocator cell = getTableCell(searchElement, SearchType.EQUALS);
        found = cell.isElementPresent();
        return found;
    }

    public Number getRowCount(String searchElement, SearchType searchType) {
        ready();
        String rowPath = getTableCell(searchElement, searchType).getPath();
        return new WebLocator(null, rowPath).size();
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
            logger.warn("table is not ready to be used");
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

    private String getSearchTypePath(String searchElement, SearchType searchType) {
        String selector = "";
        if (SearchType.EQUALS.equals(searchType)) {
            selector += "text()='" + searchElement + "'";
        } else if (SearchType.CONTAINS.equals(searchType)) {
            selector += "contains(text(),'" + searchElement + "')";
        } else if (SearchType.STARTS_WITH.equals(searchType)) {
            selector += "starts-with(text(),'" + searchElement + "')";
        } else {
            logger.warn("searchType did not math to any accepted values");
            selector = "";
        }
        selector = selector + " or count(.//*[" + selector + "]) > 0";
        return selector;
    }

    @Override
    public TableCell getCell(int rowIndex, int columnIndex) {
        Row row = getRowLocator(rowIndex);
        return new TableCell(row, columnIndex).setInfoMessage("cell - Table");
    }

    /**
     * TODO improve *reuse searchType from WebLocator
     *
     * @param searchElement
     * @param searchType    accepted values are: {"equals", "starts-with", "contains"}
     * @return
     */
    public TableCell getTableCell(String searchElement, SearchType searchType) {
        String selector = getSearchTypePath(searchElement, searchType);
        return new TableCell(this).setElPath("//tr//td[" + selector + "]");
    }

    /**
     * @deprecated use getTableCell(String searchElement, SearchType searchType)
     * @param searchElement
     * @param startWidth
     * @return
     */
    public TableCell getTableCell(String searchElement, Boolean startWidth) {
        return getTableCell(searchElement, startWidth ? SearchType.STARTS_WITH : SearchType.EQUALS);
    }

    public TableCell getTableCell(int rowIndex, int columnIndex, String text) {
        Row row = getRowLocator(rowIndex);
        return new TableCell(row).setElPath("//td[" + getSearchTypePath(text, SearchType.EQUALS) + "][" + columnIndex + "]");
    }

    public TableCell getTableCell(String searchElement, String columnText, SearchType searchType) {
        TableRow tableRow = getTableRow(searchElement, SearchType.CONTAINS);
        return getTableCellWithText(tableRow, columnText, searchType);
    }

    public TableCell getTableCell(String searchElement, int columnIndex, SearchType searchType) {
        return new TableCell(new TableRow(this, searchElement, searchType), columnIndex);
    }

    private TableCell getTableCellWithText(TableRow tableRow, String columnText, SearchType searchType) {
        return new TableCell(tableRow).setElPath("//td[" + getSearchTypePath(columnText, searchType) + "]");
    }

    @Override
    public TableRow getRow(TableCell... byCells) {
        return new TableRow(this, byCells).setInfoMessage("-TableRow");
    }

    public TableCell getTableCell(int columnIndex, TableCell... byCells) {
        return new TableCell(getRow(byCells), columnIndex);
    }

    public TableCell getTableCell(int columnIndex, String text, TableCell... byCells) {
        return new TableCell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    /**
     * returns all text elements from a table
     *
     * @param searchText
     * @return
     */
    @Deprecated //TODO fix it
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
     * @param columnIndex
     * @return
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

    public String getText(String searchText, int columnId) {
        String text = null;
        TableRow tableRow = new TableRow(this, searchText, SearchType.EQUALS);
        if (tableRow.ready()) {
            text = new TableCell(tableRow, columnId).getHtmlText();
        } else {
            logger.warn("searchText was not found in table: " + searchText);
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
     * @return
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
            SimpleCheckBox simpleCheckBox = new SimpleCheckBox().setContainer(getTableCell(searchText, columnIndex, searchType));
            selected = simpleCheckBox.isSelected() || simpleCheckBox.click();
        }
        return selected;
    }

    public boolean checkboxColumnDeselect(String searchText, int columnIndex) {
        return checkboxColumnDeselect(searchText, columnIndex, SearchType.EQUALS);
    }

    public boolean checkboxColumnDeselect(String searchText, int columnIndex, SearchType searchType) {
        boolean selected = false;
        if (ready(true)) {
            SimpleCheckBox simpleCheckBox = new SimpleCheckBox().setContainer(getTableCell(searchText, columnIndex, searchType));
            selected = !simpleCheckBox.isSelected() || simpleCheckBox.click();
        }
        return selected;
    }

    public boolean waitToPopulate() {
        return waitToPopulate(timeout);
    }

    public boolean waitToPopulate(int seconds) {
        Row row = getRowLocator(1).setInfoMessage("first row");
        WebLocator body = new WebLocator(this).setTag("tbody"); // TODO see if must add for all rows
        row.setContainer(body);
        return row.waitToRender(seconds);
    }

    public boolean ready(boolean waitRows) {
        return ready() && (!waitRows || waitToPopulate());
    }
}
