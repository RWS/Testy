package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleCheckBox;
import org.apache.log4j.Logger;

public class Table extends Row {
    private static final Logger logger = Logger.getLogger(Table.class);

    private int timeout = 30;

    public Table() {
        setClassName("Table");
        setTag("table");
    }

    public Table(WebLocator container) {
        this();
        setContainer(container);
    }

    public boolean executeScrollScript(String info, String script) {
        Boolean scrolled;
        if (hasWebDriver()) {
            scrolled = (Boolean) executeScript(script);
        } else {
            scrolled = Boolean.parseBoolean((String) executeScript(script));
        }
        logger.info(this + " - " + info + " > " + scrolled);
        Utils.sleep(200); // because of Buffer view that can have scroll delay
        return scrolled;
    }

    /**
     * Scroll on the top in Table
     *
     * @return true if scrolled
     */
    @Deprecated
    public boolean scrollTop() {
        String id = getAttributeId();
        String script = "(function(g){var a=g.view.scroller;if(a.dom.scrollTop!=0){a.dom.scrollTop=0;return true}return false})(window.Ext.getCmp('" + id + "'))";
        if (hasWebDriver()) {
            script = "return " + script;
        }
        return executeScrollScript("scrollTop", script);
    }
    @Deprecated
    public boolean scrollBottom() {
        String id = getAttributeId();
        String script = "(function(g){var a=g.view.scroller;a.dom.scrollTop=g.view.mainBody.getHeight();return true})(window.Ext.getCmp('" + id + "'))";
        if (hasWebDriver()) {
            script = "return " + script;
        }
        return executeScrollScript("scrollButtom", script);
    }

    /**
     * Scroll Up one visible page in Table
     *
     * @return true if scrolled
     */
    @Deprecated
    public boolean scrollPageUp() {
        String id = getAttributeId();
        String script = "(function(c){var a=c.view,b=a.scroller;if(b.dom.scrollTop>0){b.dom.scrollTop-=b.getHeight()-10;return true}return false})(window.Ext.getCmp('" + id + "'))";
        if (hasWebDriver()) {
            script = "return " + script;
        }
        return executeScrollScript("scrollPageUp", script);
    }

    /**
     * Scroll Down one visible page in Table
     *
     * @return true if scrolled
     */
    @Deprecated
    public boolean scrollPageDown() {
        String id = getAttributeId();
        String script = "(function(c){var a=c.view,b=a.scroller;if(b.dom.scrollTop<(a.mainBody.getHeight()-b.getHeight())){b.dom.scrollTop+=b.getHeight()-10;return true}return false})(window.Ext.getCmp('" + id + "'))";
        if (hasWebDriver()) {
            return executeScrollScript("scrollPageDown", "return " + script);
        } else {
            return executeScrollScript("scrollPageDown", script);
        }
    }

    /**
     * selects (clicks) on a table which contains a certain element
     *
     * @param searchElement the searchElement of the table element on which the search is done
     * @return true if selected
     */
    public boolean rowSelect(String searchElement) {
        return rowSelect(searchElement, false);
    }

    /**
     * Use this method when really need to select some records not for verification if row is in table
     *
     * @param searchElement
     * @param startWith
     * @return
     */
    public boolean rowSelect(String searchElement, Boolean startWith) {
        ready();
        TableCell cell = getTableCell(searchElement, startWith);
        return doCellSelect(cell);
    }

    /**
     * @param searchElement
     * @param searchType    accepted values are: {"equals"/"eq", "starts-with", "contains"}
     * @return
     */
    public boolean rowSelect(String searchElement, SearchType searchType) {
        ready(true);
        TableCell cell = getTableCell(searchElement, searchType);
        return doCellSelect(cell);
    }

    /**
     * @param searchElement
     * @param columnId
     * @param searchType    accepted values are: {"equals", "starts-with", "contains"}
     * @return
     */

    public boolean rowSelect(String searchElement, int columnId, SearchType searchType) {
        ready();
        TableCell cell = new TableCell(this, columnId, searchElement, searchType);
        return doCellSelect(cell);
    }

    public boolean doCellSelect(TableCell tableCell) {
        return doCellAction(tableCell, null);
    }

    public boolean doCellDoubleClickAt(TableCell tableCell) {
        return doCellAction(tableCell, "doubleClickAt");
    }

    private boolean doCellAction(WebLocator cell, String action) {
        boolean selected;
        scrollTop(); // make sure always start from top then scroll down till the end of the page
        do {
            // if the row is not in visible (need to scroll down - errors when used BufferView in table)
            if ("doubleClickAt".equals(action)) {
                selected = cell.doubleClickAt();
            } else {
                selected = cell.click();
            }
        } while (!selected && scrollPageDown());

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

    // TODO need to scroll to searchElement inside getRowIndex
    public boolean clickInTable(String searchElement, int columnIndex) {
        ready(true);
        int rowIndex = getRowIndex(searchElement);
        return clickInTable(rowIndex, columnIndex);
    }

    public boolean clickInTable(int rowIndex, int columnIndex) {
        ready(true);
        if (rowIndex > 0) {
            TableCell tableCell = getTableCell(rowIndex, columnIndex);
            return tableCell.click();
        }
        return false;
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
//        scrollTop(); // make sure always start from top then scroll down till the end of the page
//        do {
        // if the row is not in visible (need to scroll down - errors when used BufferView in table)
        found = cell.isElementPresent();
//        } while(!found && scrollPageDown());

        return found;
    }

    public Number getRowCount(String searchElement, Boolean startWith) {
        ready();
        String rowPath = getTableCell(searchElement, startWith).getPath();
        return new WebLocator(null, rowPath).size();
    }

    public Number getRowCount(String searchElement) {
        return getRowCount(searchElement, true);
    }

    /**
     * @return row count
     */
    public int getRowCount() {
        ready();
        return new TableRow(this).size();
    }

    /**
     * @return row count. -1 if not table not ready to be used or not found
     */
    public int getCount() {
        if (ready()) {
            return this.size();
        } else {
            logger.warn("table is not ready to be used");
            // TODO could try to verify row count with mask on table or when is disabled also.
            return -1;
        }
    }

    /**
     * returns the index of the table that contains a certain element
     *
     * @param searchElement the name of the table element on which the search is done
     * @return
     */
    public int getRowIndex(String searchElement) {
        return getRowIndex(searchElement, 1);
    }

    // TODO find better solution not so slow that iterate throw all rows
    // this method is working only for normal tables (no buffer views), and first page if table has buffer view
    public int getRowIndex(String searchElement, int startRowIndex) {
        int index = -1;
        if (ready()) {
            String path = getTableRow(startRowIndex).getPath();
            WebLocator currentElement = new WebLocator(null, path);
            while (currentElement.isElementPresent()) {
                String option = currentElement.getHtmlText();
                //logger.debug("row[" + i + "]" + option);
                if (option != null && option.contains(searchElement)) {
                    logger.debug("The '" + searchElement + "' element index is " + startRowIndex);
                    index = startRowIndex;
                    break;
                }
                startRowIndex++;
                path = getTableRow(startRowIndex).getPath();
                currentElement.setElPath(path);
            }
            if (index == -1) {
                logger.warn("The element '" + searchElement + "' was not found.");
            }
        } else {
            logger.warn("getRowIndex : table is not ready for use: " + toString());
        }
        return index;
    }

    public TableRow getTableRow(int rowIndex) {
        return new TableRow(this).setElPath("//tr[" + rowIndex + "]").setInfoMessage("row - Table");
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

    public TableCell getTableCell(int rowIndex, int columnIndex) {
        TableRow tableRow = getTableRow(rowIndex);
        return new TableCell(tableRow).setElPath("//td[" + columnIndex + "]");
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
        TableRow tableRow = getTableRow(rowIndex);
        return new TableCell(tableRow).setElPath("//td[" + getSearchTypePath(text, SearchType.EQUALS) + "][" + columnIndex + "]");
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

    public TableRow findRow(TableCell... byCells) {
        return new TableRow(this, byCells);
    }

    public TableCell getTableCell(int columnIndex, TableCell... byCells) {
        return new TableCell(findRow(byCells), columnIndex);
    }

    public TableCell getTableCell(int columnIndex, String text, TableCell... byCells) {
        return new TableCell(findRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    public String[] getRow(int rowIndex) {
        String[] rowElements = null;
        if (rowIndex != -1) {
            String text = getTableRow(rowIndex).getHtmlText();
            if (text != null) {
                rowElements = text.split("\n");
            }
        }
        return rowElements;
    }

    /**
     * returns all text elements from a table
     *
     * @param searchText
     * @return
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
            text = new WebLocator(tableRow, "//td[" + columnId + "]").getHtmlText();
        } else {
            logger.warn("searchText was not found in table: " + searchText);
        }
        return text;
    }

    public String getText(int rowIndex, int columnIndex) {
        return getTableCell(rowIndex, columnIndex).getHtmlText();
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
        WebLocator firstRow = getTableRow(1).setInfoMessage("first row");
        return firstRow.waitToRender(seconds);
    }

    public boolean ready(boolean waitRows) {
        return ready() && (!waitRows || waitToPopulate());
    }
}
