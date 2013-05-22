package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import com.extjs.selenium.grid.GridCell;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleCheckBox;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

import java.util.*;

public class Table extends Cell {
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
        //logger.info(this + " - " + info);
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
     * Scroll on the top in Grid
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
     * Scroll Up one visible page in Grid
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
     * Scroll Down one visible page in Grid
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
     * selects (clicks) on a grid which contains a certain element
     *
     * @param searchElement the searchElement of the grid element on which the search is done
     * @return true if selected
     */
    public boolean rowSelect(String searchElement) {
        return rowSelect(searchElement, false);
    }

    /**
     * Use this method when really need to select some records not for verification if row is in grid
     *
     * @param searchElement
     * @param startWith
     * @return
     */
    public boolean rowSelect(String searchElement, Boolean startWith) {
        ready();
        WebLocator cell = getGridCell(searchElement, startWith);
        return doCellSelect(cell);
    }

    /**
     * @param searchElement
     * @param searchType    accepted values are: {"equals"/"eq", "starts-with", "contains"}
     * @return
     */
    public boolean rowSelect(String searchElement, String searchType) {
        ready(true);
        WebLocator cell = getGridCell(searchElement, searchType);
        return doCellSelect(cell);
    }

    /**
     * @param searchElement
     * @param columnId
     * @param searchType    accepted values are: {"equals", "starts-with", "contains"}
     * @return
     */

    public boolean rowSelect(String searchElement, int columnId, String searchType) {
        ready();
        GridCell cell = new GridCell(this, columnId, searchElement, searchType);
        return doCellSelect(cell);
    }

    public boolean doCellSelect(WebLocator cell) {
        return doCellAction(cell, null);
    }

    public boolean doCellDoubleClickAt(GridCell cell) {
        return doCellAction(cell, "doubleClickAt");
    }

    private boolean doCellAction(WebLocator cell, String action) {
        boolean selected;
        scrollTop(); // make sure always start from top then scroll down till the end of the page
        do {
            // if the row is not in visible (need to scroll down - errors when used BufferView in grid)
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
    public boolean clickInGrid(String searchElement, int columnIndex) {
        ready(true);
        int rowIndex = getRowIndex(searchElement);
        return clickInGrid(rowIndex, columnIndex);
    }

    public boolean clickInGrid(int rowIndex, int columnIndex) {
        ready(true);
        if (rowIndex > 0) {
            WebLocator cell = getGridCell(rowIndex, columnIndex);
            return cell.click();
        }
        return false;
    }

    public WebLocator getSelectAllChecker(String columnId) {
        waitToRender();
        WebLocator checkerEl = new WebLocator(this, "//*[contains(@class, 'x-grid3-hd-" + columnId + "')]/div/div");
        return checkerEl;
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
     * returns if a grid contains a certain element
     *
     * @param searchElement the searchElement of the grid element on which the search is done
     * @return
     * @throws Exception
     */
    public boolean isRowPresent(String searchElement) {
        ready();
        boolean found;
        WebLocator cell = getGridCell(searchElement, false);
//        scrollTop(); // make sure always start from top then scroll down till the end of the page
//        do {
        // if the row is not in visible (need to scroll down - errors when used BufferView in grid)
        found = cell.isElementPresent();
//        } while(!found && scrollPageDown());

        return found;
    }

    public Number getRowCount(String searchElement, Boolean startWith) {
        ready();
        String rowPath = getGridCell(searchElement, startWith).getPath();
        return new WebLocator(null, rowPath).size();
    }

    public Number getRowCount(String searchElement) {
        return getRowCount(searchElement, true);
    }

    /**
     * @return row count. -1 if not grid not ready to be used or not found
     */
    public int getCount() {
        if (ready()) {
            return this.size();
        } else {
            logger.warn("grid is not ready to be used");
            // TODO could try to verify row count with mask on grid or when is disabled also.
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
                WebLocator row = getGridRow(rowIndex);
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
            logger.warn("getSelectedRowIndex : grid is not ready for use: " + toString());
            return -1;
        }
    }

    /**
     * returns the index of the grid that contains a certain element
     *
     * @param searchElement the name of the grid element on which the search is done
     * @return
     */
    public int getRowIndex(String searchElement) {
        return getRowIndex(searchElement, 1);
    }

    // TODO find better solution not so slow that iterate throw all rows
    // this method is working only for normal grids (no buffer views), and first page if grid has buffer view
    public int getRowIndex(String searchElement, int startRowIndex) {
        int index = -1;
        if (ready()) {
            String path = getGridRow(startRowIndex).getPath();
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
                path = getGridRow(startRowIndex).getPath();
                currentElement.setElPath(path);
            }
            if (index == -1) {
                logger.warn("The element '" + searchElement + "' was not found.");
            }
        } else {
            logger.warn("getRowIndex : grid is not ready for use: " + toString());
        }
        return index;
    }

//    public GridRow getGridRow() {
//        return new GridRow(this);
//    }

    public WebLocator getGridRow(int rowIndex) {
        return new WebLocator(this, "//tr[" + rowIndex + "]");
    }

    public WebLocator getGridRow(String searchElement) {
        return new TableRow(this, searchElement, "eq");
    }

    public WebLocator getGridRow(String searchElement, String searchType) {
        return new TableRow(this, searchElement, searchType);
    }

    private String getSearchTypePath(String searchElement, String searchType) {
        String selector = "";
        if ("equals".equals(searchType) || "eq".equals(searchType)) {
            selector += "text()='" + searchElement + "'";
        } else if ("contains".equals(searchType)) {
            selector += "contains(text(),'" + searchElement + "')";
        } else if ("starts-with".equals(searchType) || "starts".equals(searchType)) {
            selector += "starts-with(text(),'" + searchElement + "')";
        } else {
            logger.warn("searchType did not math to any accepted values");
            selector = "";
        }
        selector = selector + " or count(.//*[" + selector + "]) > 0";
        return selector;
    }

//    public GridCell getGridCell(int rowIndex) {  //TODO nu are sens
//        String rowPath = "//*[contains(@class, 'x-grid3-cell-inner')]";
//        GridCell cell = new GridCell(getGridRow(rowIndex), rowPath);
//        return cell;
//    }

    public WebLocator getGridCell(int rowIndex, int columnIndex) {
        WebLocator gridRow = getGridRow(rowIndex);
        WebLocator cell = new WebLocator(gridRow, "//td[" + columnIndex + "]");
        return cell;
    }

    /**
     * TODO improve *reuse searchType from WebLocator
     *
     * @param searchElement
     * @param searchType    accepted values are: {"equals", "starts-with", "contains"}
     * @return
     */
    public WebLocator getGridCell(String searchElement, String searchType) {
        String selector = getSearchTypePath(searchElement, searchType);
        return new WebLocator(this, "//tr//td[" + selector + "]");
    }

    public WebLocator getGridCell(String searchElement, Boolean startWidth) {
        return getGridCell(searchElement, startWidth ? "starts-with" : "eq");
    }

    public WebLocator getGridCell(int rowIndex, int columnIndex, String text) {
        WebLocator gridCell = getGridRow(rowIndex);
        return new WebLocator(gridCell, "//td[" + getSearchTypePath(text, "eq") + "][" + columnIndex + "]");
    }

//    public WebLocator getGridCell(String searchElement, int columnIndex, String columnText) {
//        return getGridCell(searchElement, columnIndex, columnText, "contains");
//    }

    public WebLocator getGridCell(String searchElement, int columnIndex, String columnText, String searchType) {
        WebLocator gridRow = getGridRow(searchElement, "contains");
        return getGridCellWithText(gridRow, columnIndex, columnText, searchType);
    }

    public WebLocator getGridCell(String searchElement, int columnIndex, String searchType) {
        return new TableCell(new TableRow(this, searchElement, searchType), columnIndex);
    }

    private WebLocator getGridCellWithText(WebLocator gridRow, int columnIndex, String columnText, String searchType) {
        return new WebLocator(gridRow, "//td[" + getSearchTypePath(columnText, searchType) + "]");
    }

//    public WebLocator getGridCell(String searchElement, int columnIndex) {
//        WebLocator gridRow = getGridRow(searchElement, "contains");// TODO verify tests and use "eq", because "eq" must be default
//        return new WebLocator(this, "//tr//td[" + getSearchTypePath(searchElement, "contains") + "]");
//    }

//    public GridCell getGridCell(String searchElement, String searchColumnId, int columnIndex) {
//        GridRow gridRow = new GridRow(this, searchColumnId, searchElement, "contains");
//        return new GridCell(gridRow, columnIndex);
//    }

    public Row findRow(TableCell... byCells) {
        return new TableRow(this, byCells);
    }

    public TableCell getGridCell(int position, TableCell... byCells) {
        return new TableCell(findRow(byCells), position);
    }

    public TableCell getGridCell(int position, String text, TableCell... byCells) {
        return new TableCell(findRow(byCells), position, text, "eq");
    }

    public String[] getRow(int rowIndex) {
        String[] rowElements = null;
        if (rowIndex != -1) {
            String text = getGridRow(rowIndex).getHtmlText();
            if (text != null) {
                rowElements = text.split("\n");
            }
        }
        return rowElements;
    }

    /**
     * returns all text elements from a grid
     *
     * @param searchText
     * @return
     */
    public String[] getRow(String searchText) {
        String[] rowElements = null;
        String text = getGridRow(searchText).getHtmlText();
        if (text != null) {
            rowElements = text.split("\n");
        }
        return rowElements;
    }

    public boolean isRowDisable(String searchText) {
        ready(true);
        String cls = getGridRow(searchText).getAttributeClass();
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
        TableRow tableRow = new TableRow(this, searchText, "eq");
        if (tableRow.ready()) {
            text = new WebLocator(tableRow, "//td[" + columnId + "]").getHtmlText();
        } else {
            logger.warn("searchText was not found in grid: " + searchText);
        }
        return text;
    }

    public String getText(int rowIndex, int columnIndex) {
        return getGridCell(rowIndex, columnIndex).getHtmlText();
    }

    /**
     * returns if a specific Grid contains a certain element
     *
     * @param searchText  the element that is already part of the grid
     * @param columnIndex the column index where the comparison is done (STARTS AT 0)
     * @param compareText the text to which the element found is compared to
     * @return
     */
    public boolean isTextPresent(String searchText, int columnIndex, String compareText) {
        String text = getText(searchText, columnIndex);
        if (text != null) {
            return text.trim().equals(compareText);
        }
        return false;
    }

    public boolean checkboxSMSelectRow(int rowIndex) {
        if (ready(true)) {
            String path;
            if (rowIndex != -1) {
                WebLocator gridRow = getGridRow(rowIndex);
                String cls = gridRow.getAttributeClass();
                boolean isSelected = cls != null && cls.contains("x-grid3-row-selected");
                path = "//*[contains(@class, 'x-grid3-row-checker')]";
                WebLocator element = new WebLocator(gridRow, path);
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
                cls = gridRow.getAttributeClass();
                return (cls != null && cls.contains("x-grid3-row-selected")) != isSelected;
            }
            return false;
        } else {
            logger.warn("checkboxSMSelectRow : grid is not ready for use: " + toString());
            return false;
        }
    }

    public int checkboxSMSelectRow(List<String> searchTexts) {
        int selected = 0;
        List<GridCell> cells = new ArrayList<GridCell>();
        for (String searchText : searchTexts) {
            cells.add(getCheckerCell(searchText));
        }
        if (ready(true)) {
            scrollTop();
            do {
                for (Iterator<GridCell> it = cells.iterator(); it.hasNext(); ) {
                    if (it.next().select()) {
                        selected++;
                        it.remove(); // remove to not try to select it more times
                    }
                }
            } while (selected < searchTexts.size() && scrollPageDown());
        } else {
            logger.warn("checkboxSMSelectRow : grid is not ready for use: " + toString());
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
            GridCell cell = getCheckerCell(searchText, containsText);
            do {
                selected = cell.select();
            } while (!selected && scrollPageDown());
            return selected;
        } else {
            logger.warn("checkboxSMSelectRow : grid is not ready for use: " + toString());
            return false;
        }
    }

    private GridCell getCheckerCell(final String searchText) {
        return getCheckerCell(searchText, false);
    }

    private GridCell getCheckerCell(final String searchText, boolean containsText) {
        String cellPath = getTableRowSearchPath(searchText, containsText);
        cellPath += "//*[contains(@class, 'x-grid3-row-checker')]";
        return new GridCell(getGridRow(searchText), cellPath).setInfoMessage("row-checker (" + searchText + ")");
    }

    private GridCell getCheckboxCell(final String searchText, int columnIndex) {
        return getCheckboxCell(searchText, columnIndex, false);
    }

    public GridCell getCheckboxCell(final String searchText, int columnIndex, boolean containsText) {
        String cellPath = getTableRowSearchPath(searchText, containsText);
        cellPath += "//td[" + columnIndex + "]//*[contains(@class, 'x-grid3-check-col')]";
        return new GridCell(getGridRow(searchText), cellPath).setInfoMessage("row-checker (" + searchText + ")");
    }

    private String getTableRowSearchPath(String searchText, boolean containsText) {
        String text = containsText ? ("contains(text(),'" + searchText + "')") : ("text()='" + searchText + "'");
        String searchCondition = "[count(//*[" + text + "]) > 0]";
        return "//tr" + searchCondition;
    }

    public boolean checkboxColumnSelect(String searchText, int columnIndex) {
        return checkboxColumnSelect(searchText, columnIndex, "eq");
    }

    public boolean checkboxColumnSelect(String searchText, int columnIndex, String searchType) {
        boolean selected = false;
        if (ready(true)) {
            SimpleCheckBox simpleCheckBox = new SimpleCheckBox().setContainer(getGridCell(searchText, columnIndex, searchType));
            if (simpleCheckBox.isSelected()) {
                selected = true;
            } else {
                selected = simpleCheckBox.click();
            }
        }
        return selected;
    }

    public boolean checkboxColumnDeselect(String searchText, int columnIndex) {
        return checkboxColumnDeselect(searchText, columnIndex, "eq");
    }

    public boolean checkboxColumnDeselect(String searchText, int columnIndex, String searchType) {
        boolean selected = false;
        if (ready(true)) {
            SimpleCheckBox simpleCheckBox = new SimpleCheckBox().setContainer(getGridCell(searchText, columnIndex, searchType));
            if (simpleCheckBox.isSelected()) {
                selected = simpleCheckBox.click();
            } else {
                selected = true;
            }
        }
        return selected;
    }

    /**
     * clicks in the checkbox found at the beginning of the grid which contains a specific element
     *
     * @param searchText
     * @return
     */
    public boolean checkboxColumnSelect(String searchText) {
        boolean selected = false;
        if (ready(true)) {
            String gridPath = getPath();
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
            logger.warn("checkboxColumnSelect: grid is not ready for use: " + toString());
            selected = false;
        }
        return selected;
    }

    public boolean isCheckBoxColumnSelected(String searchText) {
        boolean isSelected = false;
        if (ready(true)) {
            int rowIndex = getRowIndex(searchText);
            if (rowIndex != -1) {
                String path = getGridRow(rowIndex).getPath() + "//div[contains(@class,'x-grid3-check-col-on')]";
                isSelected = new WebLocator(null, path).exists();
            }
        }
        return isSelected;
    }

    //    public String returnTextFromColumn(int columnId) {
//        String path = new GridRow(this).getPath() + "//*[contains(@class,'x-grid3-td-" + columnId + "')]";
//        try {
//            return new WebLocator(null, path).getText();
//        } catch (Exception e) {
//            return "";
//        }
//    }
//
    public boolean waitToPopulate() {
        return waitToPopulate(timeout);
    }

    public boolean waitToPopulate(int seconds) {
        WebLocator firstRow = getGridRow(1).setInfoMessage("first row");
        return firstRow.waitToRender(seconds);
    }

//    public boolean ready() {
//        return super.ready() && waitToLoad();
//    }

    //    public boolean ready(int seconds) {
//        return super.ready() && waitToLoad(seconds);
//    }
//
    public boolean ready(boolean waitRows) {
        return ready() && (!waitRows || waitToPopulate());
    }
}
