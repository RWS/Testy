package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleCheckBox;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

import java.util.*;

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
    public boolean scrollTop() {
        String id = getAttributeId();
        String script = "(function(g){var a=g.view.scroller;if(a.dom.scrollTop!=0){a.dom.scrollTop=0;return true}return false})(window.Ext.getCmp('" + id + "'))";
        if (hasWebDriver()) {
            script = "return " + script;
        }
        return executeScrollScript("scrollTop", script);
    }

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

    public WebLocator getSelectAllChecker(String columnId) {
        waitToRender();
        return new WebLocator(this, "//*[contains(@class, 'x-grid3-hd-" + columnId + "')]/div/div");
    }

    private WebLocator getHeader(String columnId) {
        waitToRender();
        WebLocator headerEl = new WebLocator(this, "//*[contains(@class, 'x-grid3-hd-" + columnId + "') and count(parent::td[not(contains(@style ,'display: none;'))]) > 0]");
        headerEl.setInfoMessage(itemToString() + " Header[" + columnId + "]");
        return headerEl;
    }

    /**
     * @param columnId - "x-grid3-hd-" + columnId
     *                 example: x-grid3-hd-userName in this case "userName" is the columnId
     * @return
     */
    public boolean clickOnHeader(String columnId) {
        return getHeader(columnId).click();
    }

    public boolean assertClickOnHeader(String columnId) {
        return getHeader(columnId).assertClick();
    }

    public boolean doubleClickOnHeader(String columnId) {
        return clickOnHeader(columnId) && clickOnHeader(columnId);
    }

    public boolean assertCheckSelectAll(String columnId) {
        logger.debug("Select-all checker path: " + getSelectAllChecker(columnId));
        if (hasWebDriver()) {
            return getSelectAllChecker(columnId).assertClick();
        } else {
            return getSelectAllChecker(columnId).assertClickAt();
        }
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

    public int getSelectedRowIndex() {
        if (ready()) {
            int rowCount = getCount();
            int rowIndex = 1;
            int index = -1;

            //TODO Try better search mecanism
            while (rowIndex <= rowCount) {
                WebLocator row = getTableRow(rowIndex);
                String cls = row.getAttributeClass();
                if (cls != null && cls.contains("x-grid3-row-selected")) {
                    index = rowIndex;
                    break;
                }
                rowIndex++;
            }
            if (index == -1) {
                logger.warn("no selected row was not found.");
            }
            return index;
        } else {
            logger.warn("getSelectedRowIndex : table is not ready for use: " + toString());
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

    public boolean isRowDisable(String searchText) {
        ready(true);
        String cls = getTableRow(searchText).getAttributeClass();
        return cls.contains("x-item-disabled");
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

    public boolean checkboxSMSelectRow(int rowIndex) {
        if (ready(true)) {
            String path;
            if (rowIndex != -1) {
                TableRow tableRow = getTableRow(rowIndex);
                String cls = tableRow.getAttributeClass();
                boolean isSelected = cls != null && cls.contains("x-grid3-row-selected");
                path = "//*[contains(@class, 'x-grid3-row-checker')]";
                WebLocator element = new WebLocator(tableRow, path);
                element.setInfoMessage("row-checker");
                if (element.exists()) {
                    // TODO (verify if is working) to scroll to this element (if element is not visible)
                    new WebLocator(this, "//*[contains(@class,'x-grid3-focus')]").sendKeys(Keys.TAB); //TODO work with selenium????
                    element.click();
                    logger.info("Clicking on checkbox corresponding to line index: " + rowIndex);
                } else {
                    logger.warn("Could not click on checkbox corresponding to line index: " + rowIndex + "; path = " + path);
                    return false;
                }
                cls = tableRow.getAttributeClass();
                return (cls != null && cls.contains("x-grid3-row-selected")) != isSelected;
            }
            return false;
        } else {
            logger.warn("checkboxSMSelectRow : table is not ready for use: " + toString());
            return false;
        }
    }

    public int checkboxSMSelectRow(List<String> searchTexts) {
        int selected = 0;
        List<TableCell> cells = new ArrayList<TableCell>();
        for (String searchText : searchTexts) {
            cells.add(getCheckerCell(searchText));
        }
        if (ready(true)) {
            scrollTop();
            do {
                for (Iterator<TableCell> it = cells.iterator(); it.hasNext(); ) {
                    if (it.next().clickAt()) {
                        selected++;
                        it.remove(); // remove to not try to select it more times
                    }
                }
            } while (selected < searchTexts.size() && scrollPageDown());
        } else {
            logger.warn("checkboxSMSelectRow : table is not ready for use: " + toString());
        }
        return selected;
    }

    public int checkboxSMSelectRow(HashSet<String> searchTexts) {
        return checkboxSMSelectRow(new ArrayList<String>(searchTexts));
    }

    public int checkboxSMSelectRow(String[] searchTexts) {
        return checkboxSMSelectRow(Arrays.asList(searchTexts));
    }

    public boolean checkboxSMSelectRow(String searchText) {
        return checkboxSMSelectRow(searchText, false);
    }

    public boolean checkboxSMSelectRow(String searchText, boolean containsText) {
        if (ready(true)) {
            boolean selected;
            scrollTop();
            TableCell cell = getCheckerCell(searchText, containsText);
            do {
                selected = cell.clickAt();
            } while (!selected && scrollPageDown());
            return selected;
        } else {
            logger.warn("checkboxSMSelectRow : table is not ready for use: " + toString());
            return false;
        }
    }

    private TableCell getCheckerCell(final String searchText) {
        return getCheckerCell(searchText, false);
    }

    private TableCell getCheckerCell(final String searchText, boolean containsText) {
        String cellPath = getTableRowSearchPath(searchText, containsText);
        cellPath += "//*[contains(@class, 'x-grid3-row-checker')]";
        return new TableCell(getTableRow(searchText)).setElPath(cellPath).setInfoMessage("row-checker (" + searchText + ")");
    }

    private TableCell getCheckboxCell(final String searchText, int columnIndex) {
        return getCheckboxCell(searchText, columnIndex, false);
    }

    public TableCell getCheckboxCell(final String searchText, int columnIndex, boolean containsText) {
        String cellPath = getTableRowSearchPath(searchText, containsText);
        cellPath += "//td[" + columnIndex + "]//*[contains(@class, 'x-grid3-check-col')]";
        return new TableCell(getTableRow(searchText)).setElPath(cellPath).setInfoMessage("row-checker (" + searchText + ")");
    }

    private String getTableRowSearchPath(String searchText, boolean containsText) {
        String text = containsText ? ("contains(text(),'" + searchText + "')") : ("text()='" + searchText + "'");
        String searchCondition = "[count(//*[" + text + "]) > 0]";
        return "//tr" + searchCondition;
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

    /**
     * clicks in the checkbox found at the beginning of the table which contains a specific element
     *
     * @param searchText
     * @return
     */
    public boolean checkboxColumnSelect(String searchText) {
        boolean selected = false;
        if (ready(true)) {
            String tablePath = getPath();
            String path;
            int rowIndex = getRowIndex(searchText);
            if (rowIndex != -1) {
//                path = .getPath() + "//div[contains(@class,'x-grid3-check-col')]";

//                if (element.ready()) {
//                    selected = isCheckBoxColumnSelected(searchText) ? true : element.click();
//                } else {
//                    logger.warn("Could not click on checkboxColumnSelect corresponding to line : " + searchText );
//                    return false;
//                }
            }
        } else {
            logger.warn("checkboxColumnSelect: table is not ready for use: " + toString());
            selected = false;
        }
        return selected;
    }

    public boolean isCheckBoxColumnSelected(String searchText) {
        boolean isSelected = false;
        if (ready(true)) {
            int rowIndex = getRowIndex(searchText);
            if (rowIndex != -1) {
                String path = getTableRow(rowIndex).getPath() + "//div[contains(@class,'x-grid3-check-col-on')]";
                isSelected = new WebLocator(null, path).exists();
            }
        }
        return isSelected;
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
