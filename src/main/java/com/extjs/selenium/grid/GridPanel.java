package com.extjs.selenium.grid;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.Utils;
import com.extjs.selenium.panel.Panel;
import com.extjs.selenium.tab.TabPanel;
import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.ElementRemovedSuccessCondition;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.*;

public class GridPanel extends Panel {
    private static final Logger logger = Logger.getLogger(GridPanel.class);

    private String searchColumnId = "0";

    // TODO temporary solution for verification if loaded
    private boolean isLoaded = false;
    private int timeout = 60;

    public GridPanel() {
        setClassName("GridPanel");
        setBaseCls("x-grid-panel");
        setHeaderBaseCls("x-panel");
    }

    public GridPanel(String cls) {
        this();
        setClasses(cls);
    }

    public GridPanel(WebLocator container) {
        this();
        setContainer(container);
    }

    public GridPanel(String cls, String searchColumnId) {
        this(cls);
        this.searchColumnId = searchColumnId;
    }

    public GridPanel(WebLocator container, String searchColumnId) {
        this(container);
        setSearchColumnId(searchColumnId);
    }

    public GridPanel(WebLocator container, String cls, String searchColumnId) {
        this(container);
        setClasses(cls);
        setSearchColumnId(searchColumnId);
    }

    public static GridPanel getInstance(WebLocator container, String searchColumnId) {
        return new GridPanel(container, searchColumnId);
    }

    // TODO find better solution for GridPanel that is used in TabPanel
    public static GridPanel getInstanceByTabPanel(TabPanel tabPanel, String searchColumnId) {
        GridPanel gridPanel = new GridPanel();
        gridPanel.setContainer(tabPanel.getContainer());
        gridPanel.setElPath(tabPanel.getItemPath(false));
        gridPanel.setSearchColumnId(searchColumnId);
        return gridPanel;
    }

    public String getSearchColumnId() {
        return searchColumnId;
    }

    public void setSearchColumnId(String searchColumnId) {
        this.searchColumnId = searchColumnId;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    // Methods

    /**
     * TODO find better solution
     * (for example when grid has been loaded but has no records or when grid is loading for second time)
     */
    public boolean waitToLoad() {
        return waitToLoad(timeout);
    }

    public boolean waitToLoad(int seconds) {
        ExtJsComponent mask = new ExtJsComponent("x-mask-loading", this);
        Condition condition = new ConditionManager(seconds * 1000).add(new ElementRemovedSuccessCondition(mask)).execute();
        isLoaded = condition.isSuccess();
        if (!isLoaded) {
            logger.warn(this + " still has x-mask-loading");
        }
        return isLoaded;
    }

    public boolean waitToLoad(int seconds, boolean waitRows) {
        waitToLoad(seconds);
        return waitRows ? waitToPopulate(seconds) : isLoaded;
    }

    public boolean waitToLoad(boolean waitRows) {
        return waitToLoad() && (!waitRows || waitToPopulate());
    }

    public boolean executeScrollScript(String info, String script) {
        Boolean scrolled;
        //logger.info(this + " - " + info);
        if (WebDriverConfig.hasWebDriver()) {
            scrolled = (Boolean) executeScript(script);
        } else {
            scrolled = Boolean.parseBoolean((String) executeScript(script));
        }
        logger.info(this + " - " + info + " > " + scrolled);
        // TODO make configurable if has buffer view
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
        if (WebDriverConfig.hasWebDriver()) {
            script = "return " + script;
        }
        return executeScrollScript("scrollTop", script);
    }

    public boolean scrollBottom() {
        String id = getAttributeId();
        String script = "(function(g){var a=g.view.scroller;a.dom.scrollTop=g.view.mainBody.getHeight();return true})(window.Ext.getCmp('" + id + "'))";
        if (WebDriverConfig.hasWebDriver()) {
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
        if (WebDriverConfig.hasWebDriver()) {
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
        if (WebDriverConfig.hasWebDriver()) {
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

    public boolean assertRowSelect(String searchElement) {
        boolean selected = rowSelect(searchElement);
        if (!selected) {
            Assert.fail("Could not select row with text: " + searchElement);
        }
        return selected;
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
        GridCell cell = getGridCell(searchElement, startWith);
        return doCellSelect(cell);
    }

    /**
     * @param searchElement
     * @param searchType    accepted values are: {"equals"/"eq", "starts-with", "contains"}
     * @return
     */
    public boolean rowSelect(String searchElement, SearchType searchType) {
        ready(true);
        GridCell cell = getGridCell(searchElement, searchType);
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
        GridCell cell = new GridCell(this, columnId, searchElement, searchType);
        return doCellSelect(cell);
    }

    public boolean doCellSelect(GridCell cell) {
        return doCellAction(cell, null);
    }

    public boolean doCellDoubleClickAt(GridCell cell) {
        return doCellAction(cell, "doubleClickAt");
    }

    private boolean doCellAction(GridCell cell, String action) {
        boolean selected;
        scrollTop(); // make sure always start from top then scroll down till the end of the page
        do {
            // if the row is not in visible (need to scroll down - errors when used BufferView in grid)
            if ("doubleClickAt".equals(action)) {
                selected = cell.doubleClickAt();
            } else {
                selected = cell.select();
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

    /**
     * Scroll Page Down to find the cell. If you found it return true, if not return false.
     * @param searchElement
     * @param columnId
     * @param searchType
     * @return
     */
    public boolean isCellPresent(String searchElement, int columnId, SearchType searchType) {
        ready();
        GridCell cell = new GridCell(this, columnId, searchElement, searchType);
        boolean selected;
        do {
            selected = cell.isElementPresent();
        } while (!selected && scrollPageDown());
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
            GridCell cell = getGridCell(rowIndex, columnIndex);
            return cell.select();
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
        if (WebDriverConfig.hasWebDriver()) {
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
        GridCell cell = getGridCell(searchElement, false);
//        scrollTop(); // make sure always start from top then scroll down till the end of the page
//        do {
        // if the row is not in visible (need to scroll down - errors when used BufferView in grid)
        found = cell.isElementPresent();
//        } while(!found && scrollPageDown());

        return found;
    }

    public boolean isRowPresent(String searchElement, boolean containsText) {
        ready();
        GridCell cell = getGridCell(containsText, searchElement);
        return cell.isElementPresent();
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
            return new GridRow(this).size();
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
                GridRow row = getGridRow(rowIndex);
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
            String path = getGridCell(startRowIndex).getPath();
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
                path = getGridCell(startRowIndex).getPath();
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

    public GridRow getGridRow() {
        return new GridRow(this);
    }

    public GridRow getGridRow(int rowIndex) {
        return new GridRow(this, rowIndex);
    }

    public GridRow getGridRow(String searchElement) {
        return getGridRow(searchElement, SearchType.EQUALS);
    }

    public GridRow getGridRow(String searchElement, SearchType searchType) {
        return new GridRow(this, searchColumnId, searchElement, searchType);
    }

    public GridCell getGridCell(int rowIndex, int columnIndex) {
        WebLocator gridRow = getGridRow(rowIndex);
        return new GridCell(gridRow, columnIndex);
    }

    public GridCell getGridCell(int rowIndex) {
        String rowPath = "//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]//*[contains(@class, 'x-grid3-cell-inner')]";
        return new GridCell(getGridRow(rowIndex), rowPath);
    }

    /**
     * TODO improve *reuse searchType from WebLocator
     *
     * @param searchElement
     * @param searchType    accepted values are: {"equals", "starts-with", "contains"}
     * @return
     */
    public GridCell getGridCell(String searchElement, SearchType searchType) {
        String textCondition = "text()='" + searchElement + "'";
        if (SearchType.EQUALS.equals(searchType)) {
        } else if (SearchType.CONTAINS.equals(searchType)) {
            textCondition = "contains(text(),'" + searchElement + "')";
        } else if (SearchType.STARTS_WITH.equals(searchType)) {
            textCondition = "starts-with(text(),'" + searchElement + "')";
        } else {
            logger.warn("searchType did not math to any accepted values");
        }
        String cellPath = "//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]//*[" + textCondition + "]";
        GridCell cell = new GridCell(this, cellPath);
        cell.setInfoMessage("cell(" + searchElement + ")");
        return cell;
    }

    public GridCell getGridCell(String searchElement, Boolean startWidth) {
        return getGridCell(searchElement, startWidth ? SearchType.STARTS_WITH : SearchType.EQUALS);
    }

    public GridCell getGridCell(boolean containsText, String searchElement) {
        return getGridCell(searchElement, containsText ? SearchType.CONTAINS : SearchType.EQUALS);
    }

    public GridCell getGridCell(int rowIndex, int columnIndex, String text) {
        GridRow gridRow = getGridRow(rowIndex);
        return getGridCellWithText(gridRow, columnIndex, text, SearchType.CONTAINS);
    }

    public GridCell getGridCell(String searchElement, int columnIndex, String columnText) {
        return getGridCell(searchElement, columnIndex, columnText, SearchType.CONTAINS);
    }

    public GridCell getGridCell(String searchElement, int columnIndex, String columnText, SearchType searchType) {
        GridRow gridRow = getGridRow(searchElement, SearchType.CONTAINS);
        return getGridCellWithText(gridRow, columnIndex, columnText, searchType);
    }

    private GridCell getGridCellWithText(GridRow gridRow, int columnIndex, String columnText, SearchType searchType) {
        WebLocator gridColTd = new WebLocator(gridRow, "//td[" + columnIndex + "]");
        return new GridCell(gridColTd, columnText, searchType);
    }

    public GridCell getGridCell(String searchElement, int columnIndex) {
        GridRow gridRow = getGridRow(searchElement, SearchType.CONTAINS);// TODO verify tests and use "eq", because "eq" must be default
        return new GridCell(gridRow, columnIndex);
    }

    public GridCell getGridCell(String searchElement, String searchColumnId, int columnIndex) {
        GridRow gridRow = new GridRow(this, searchColumnId, searchElement, SearchType.CONTAINS);
        return new GridCell(gridRow, columnIndex);
    }

    public GridRow findGridRow(GridCell... byCells) {
        return new GridRow(this, byCells).setInfoMessage("-GridRow");
    }

    public boolean selectRow(GridCell... byCells) {
        GridRow gridRow = findGridRow(byCells);
        boolean selected;
        do {
            selected = gridRow.clickAt();
        } while (!selected && scrollPageDown());
        return selected;
    }

    public GridCell getGridCell(int position, String text, GridCell... byCells) {
        return new GridCell(findGridRow(byCells)).setPosition(position).setText(text);
    }

    public String[] getRow(int rowIndex) {
        String[] rowElements = null;
        if (rowIndex != -1) {
            GridRow row = getGridRow(rowIndex);
            String text = row.getHtmlText();
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
        GridRow row = new GridRow(this, searchText, getSearchColumnId(), SearchType.CONTAINS);
        String text = row.getHtmlText();
        if (text != null) {
            rowElements = text.split("\n");
        }
        return rowElements;
    }

    public boolean isRowDisabled(String searchText) {
        String cls = getGridRow(searchText).getAttribute("class");
        return cls != null && cls.contains("x-item-disabled");
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
        GridCell cell = getGridCell(searchText, columnId);
        if (this.ready(true) && cell.ready()) {
            text = cell.getHtmlText();
        } else {
            logger.warn("searchText was not found in grid: " + searchText);
        }
        return text;
    }

    public String getText(String searchText, String searchColumnId, int columnId) {
        String text = null;
        GridCell cell = getGridCell(searchText, searchColumnId, columnId);
        if (this.ready(true) && cell.ready()) {
            text = cell.getHtmlText();
        } else {
            logger.warn("searchText was not found in grid: " + searchText);
        }
        return text;
    }

    public String getText(int rowIndex, int columnIndex) {
        GridCell cell = getGridCell(rowIndex, columnIndex);
        return cell.getHtmlText();
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
        return text != null && text.trim().equals(compareText);
    }

    public boolean checkboxSMSelectRow(int rowIndex) {
        if (ready(true)) {
            String path;
            if (rowIndex != -1) {
                GridRow gridRow = getGridRow(rowIndex);
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

    public GridCell getCheckerCell(final String searchText, boolean containsText) {
        String cellPath = getTableRowSearchPath(searchText, containsText);
        cellPath += "//*[contains(@class, 'x-grid3-row-checker')]";
        return new GridCell(getGridRow(), cellPath).setInfoMessage("row-checker (" + searchText + ")");
    }

    private GridCell getCheckboxCell(final String searchText, int columnIndex) {
        return getCheckboxCell(searchText, columnIndex, false);
    }

    public GridCell getCheckboxCell(final String searchText, int columnIndex, boolean containsText) {
        String cellPath = getTableRowSearchPath(searchText, containsText);
        cellPath += "//td[" + columnIndex + "]//*[contains(@class, 'x-grid3-check-col')]";
        return new GridCell(getGridRow(), cellPath).setInfoMessage("row-checker (" + searchText + ")");
    }

    private String getTableRowSearchPath(String searchText, boolean containsText) {
        String text = containsText ? ("contains(text(),'" + searchText + "')") : ("text()='" + searchText + "'");
        return "//tr[count(*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]//*[" + text + "]) > 0]";
    }

    public boolean checkboxColumnSelect(String searchText, int columnIndex) {
        return checkboxColumnSelect(searchText, columnIndex, false);
    }

    public boolean checkboxColumnSelect(String searchText, int columnIndex, boolean containsText) {
        boolean selected = false;
        if (ready(true)) {
            GridCell gridCell = getCheckboxCell(searchText, columnIndex, containsText);
            gridCell.ready();
            String cls = gridCell.getAttributeClass();
            boolean isSelected = cls != null && cls.contains("x-grid3-check-col-on");
            if (isSelected) {
                selected = isSelected;
            } else {
                selected = gridCell.clickAt();
                if (!selected) {
                    logger.debug("gridCellPath: " + gridCell.getPath());
                }
            }
        }
        return selected;
    }

    public boolean checkboxColumnDeselect(String searchText, int columnIndex) {
        return checkboxColumnDeselect(searchText, columnIndex, false);
    }

    public boolean checkboxColumnDeselect(String searchText, int columnIndex, boolean containsText) {
        boolean selected = false;
        if (ready(true)) {
            GridCell gridCell = getCheckboxCell(searchText, columnIndex, containsText);
            String cls = gridCell.getAttributeClass();
            boolean isSelected = cls != null && cls.contains("x-grid3-check-col-on");
            if (isSelected) {
                logger.debug("path: " + gridCell.getPath());
                selected = gridCell.clickAt();
            } else {
                selected = isSelected;
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
                path = getGridRow(rowIndex).getPath() + "//div[contains(@class,'x-grid3-check-col')]";
                WebLocator element = new WebLocator(null, path);
                if (element.exists()) {
                    // TODO (verify if is working) to scroll to this element (if element is not visible)
                    new WebLocator(null, gridPath + "//*[contains(@class,'x-grid3-focus')]").sendKeys(Keys.TAB);
                    selected = isCheckBoxColumnSelected(searchText) || element.click();
                    logger.info("Clicking on checkboxColumnSelect corresponding to line : " + searchText);
                } else {
                    logger.warn("Could not click on checkboxColumnSelect corresponding to line : " + searchText + "; path = " + path);
                    return false;
                }
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

    public String returnTextFromColumn(int columnId) {
        String path = new GridRow(this).getPath() + "//*[contains(@class,'x-grid3-td-" + columnId + "')]";
        try {
            return new WebLocator(null, path).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean waitToPopulate() {
        return waitToPopulate(timeout);
    }

    public boolean waitToPopulate(int seconds) {
        //logger.debug("waitToPopulate: " + seconds + "; " + toString());
        WebLocator firstRow = getGridRow(1).setInfoMessage("first row");
        return firstRow.waitToRender(seconds);
    }

    public boolean ready() {
        return super.ready() && waitToLoad();
    }

    public boolean ready(int seconds) {
        return super.ready() && waitToLoad(seconds);
    }

    public boolean ready(boolean waitRows) {
        return ready() && (!waitRows || waitToPopulate());
    }
}