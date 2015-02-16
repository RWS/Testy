package com.extjs.selenium.grid;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.panel.Panel;
import com.extjs.selenium.tab.TabPanel;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.ElementRemovedSuccessCondition;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.ITable;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.utils.Utils;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GridPanel extends Panel implements ITable<GridRow, GridCell> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridPanel.class);

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
        WebLocator container = tabPanel.getContainer();
        gridPanel.setContainer(container);

        tabPanel.setContainer(null); // hack to have path without container
        String elPath = tabPanel.getPath();
        tabPanel.setContainer(container); // set container back

        gridPanel.setElPath(elPath);
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
            LOGGER.warn(this + " still has x-mask-loading");
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

    protected boolean executeScrollScript(String info, String script) {
        Boolean scrolled;
        //LOGGER.info(this + " - " + info);
        scrolled = (Boolean) WebLocatorUtils.doExecuteScript(script);
        LOGGER.info(this + " - " + info + " > " + scrolled);
        // TODO make configurable if has buffer view
        Utils.sleep(200); // because of Buffer view that can have scroll delay
        return scrolled;
    }

    /**
     * Will Fail if id is null
     *
     * @return attribute
     */
    private String getAttrId() {
        String id = getAttributeId();
        LOGGER.debug("id=" + id);
        if (id == null) {
            LOGGER.warn("GridPanel id is null: " + getPath());
            Assert.fail("Could not scroll because id of grid is null: " + this);
        }
        return id;
    }

    /**
     * Scroll on the top in Grid
     *
     * @return true if scrolled
     */
    public boolean scrollTop() {
        String id = getAttrId();
        return scrollTop(id);
    }
    private boolean scrollTop(String id) {
        String script = "return (function(g){var a=g.view.scroller;if(a.dom.scrollTop!=0){a.dom.scrollTop=0;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return executeScrollScript("scrollTop", script);
    }

    public boolean scrollBottom() {
        String id = getAttrId();
        String script = "return (function(g){var a=g.view.scroller;a.dom.scrollTop=g.view.mainBody.getHeight();return true})(window.Ext.getCmp('" + id + "'))";
        return executeScrollScript("scrollButtom", script);
    }

    /**
     * Scroll Up one visible page in Grid
     *
     * @return true if scrolled
     */
    public boolean scrollPageUp() {
        String id = getAttrId();
        String script = "return (function(c){var a=c.view,b=a.scroller;if(b.dom.scrollTop>0){b.dom.scrollTop-=b.getHeight()-10;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return executeScrollScript("scrollPageUp", script);
    }

    /**
     * Scroll Down one visible page in Grid
     *
     * @return true if scrolled
     */
    public boolean scrollPageDown() {
        String id = getAttrId();
        return scrollPageDown(id);
    }
    private boolean scrollPageDown(String id) {
        String script = "return (function(c){var a=c.view,b=a.scroller;if(b.dom.scrollTop<(a.mainBody.getHeight()-b.getHeight())){b.dom.scrollTop+=b.getHeight()-10;return true}return false})(window.Ext.getCmp('" + id + "'))";
        return executeScrollScript("scrollPageDown", script);
    }

    @Override
    public boolean rowSelect(String searchText) {
        return rowSelect(searchText, SearchType.EQUALS);
    }

    public boolean assertRowSelect(String searchElement) {
        boolean selected = rowSelect(searchElement);
        if (!selected) {
            Assert.fail("Could not select row with text: " + searchElement);
        }
        return selected;
    }

    @Override
    public boolean rowSelect(String searchText, SearchType searchType) {
        ready(true);
        GridCell cell = getGridCell(searchText, searchType);
        return doCellSelect(cell);
    }

    /**
     * @param searchElement searchElement
     * @param columnId 1,2,3...
     * @param searchType    accepted values are: SearchType.EQUALS
     * @return true or false
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
        String id = getAttrId();
        scrollTop(id); // make sure always start from top then scroll down till the end of the page
        do {
            ready();// if the row is not in visible (need to scroll down - errors when used BufferView in grid)
            if ("doubleClickAt".equals(action)) {
                selected = cell.doubleClickAt();
            } else {
                selected = cell.select();
            }
        } while (!selected && scrollPageDown(id));

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
     * Scroll Page Down to find the cell. If you found it return true, if not return false.
     *
     * @param searchElement searchElement
     * @param columnId columnId
     * @param searchType SearchType.EQUALS
     * @return true or false
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
            GridCell cell = getCell(rowIndex, columnIndex);
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
     * @return true or false
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
        LOGGER.debug("Select-all checker path: " + getSelectAllChecker(columnId));
        return getSelectAllChecker(columnId).assertClick();
    }

    /**
     * returns if a grid contains a certain element
     *
     * @param searchElement the searchElement of the grid element on which the search is done
     * @return true or false
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

    @Override
    public int getCount() {
        if (ready()) {
            return new GridRow(this).size();
        } else {
            LOGGER.warn("grid is not ready to be used");
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
                GridRow row = getRowLocator(rowIndex);
                String cls = row.getAttributeClass();
                if (cls != null && cls.contains("x-grid3-row-selected")) {
                    index = rowIndex;
                    break;
                }
                rowIndex++;
            }
            if (index == -1) {
                LOGGER.warn("no selected row was not found.");
            }
            return index;
        } else {
            LOGGER.warn("getSelectedRowIndex : grid is not ready for use: " + toString());
            return -1;
        }
    }

    /**
     * returns the index of the grid that contains a certain element
     *
     * @param searchElement the name of the grid element on which the search is done
     * @return int
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
                //LOGGER.debug("row[" + i + "]" + option);
                if (option != null && option.contains(searchElement)) {
                    LOGGER.debug("The '" + searchElement + "' element index is " + startRowIndex);
                    index = startRowIndex;
                    break;
                }
                startRowIndex++;
                path = getGridCell(startRowIndex).getPath();
                currentElement.setElPath(path);
            }
            if (index == -1) {
                LOGGER.warn("The element '" + searchElement + "' was not found.");
            }
        } else {
            LOGGER.warn("getRowIndex : grid is not ready for use: " + toString());
        }
        return index;
    }

    public GridRow getGridRow() {
        return new GridRow(this);
    }

    @Override
    public GridRow getRowLocator(int rowIndex) {
        return new GridRow(this, rowIndex);
    }

    public GridRow getGridRow(String searchElement) {
        return getGridRow(searchElement, SearchType.EQUALS);
    }

    public GridRow getGridRow(String searchElement, SearchType searchType) {
        return new GridRow(this, searchColumnId, searchElement, searchType);
    }

    @Override
    public GridCell getCell(int rowIndex, int columnIndex) {
        Row row = getRowLocator(rowIndex);
        return new GridCell(row, columnIndex);
    }

    public GridCell getGridCell(int rowIndex) {
        String rowPath = "//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]//*[contains(@class, 'x-grid3-cell-inner')]";
        return new GridCell(getRowLocator(rowIndex), rowPath);
    }

    /**
     *
     * @param searchElement Text searched.
     * @param searchType SearchType.EQUALS
     * @return GridCell
     */
    public GridCell getGridCell(String searchElement, SearchType searchType) {
        WebLocator textCell = new WebLocator().setText(searchElement, searchType);
        String cellPath = "//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]" + textCell.getPath();
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
        GridRow gridRow = getRowLocator(rowIndex);
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

    @Override
    public GridRow getRow(GridCell... byCells) {
        return new GridRow(this, byCells).setInfoMessage("-GridRow");
    }

    public boolean selectRow(GridCell... byCells) {
        GridCell gridCell = getGridCell(1, byCells);
        return doCellSelect(gridCell);
    }

    public GridCell getGridCell(int position, String text, GridCell... byCells) {
        return new GridCell(getRow(byCells)).setPosition(position).setText(text);
    }

    public GridCell getGridCell(int position, GridCell... byCells) {
        return new GridCell(getRow(byCells)).setPosition(position);
    }

    public String[] getRow(int rowIndex) {
        String[] rowElements = null;
        if (rowIndex != -1) {
            Row row = getRowLocator(rowIndex);
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
     * @param searchText searchText
     * @return all text elements from a grid
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

    public String getText(String searchText, int columnId) {
        String text = null;
        GridCell cell = getGridCell(searchText, columnId);
        if (this.ready(true) && cell.ready()) {
            text = cell.getHtmlText();
        } else {
            LOGGER.warn("searchText was not found in grid: " + searchText);
        }
        return text;
    }

    public String getText(String searchText, String searchColumnId, int columnId) {
        String text = null;
        GridCell cell = getGridCell(searchText, searchColumnId, columnId);
        if (this.ready(true) && cell.ready()) {
            text = cell.getHtmlText();
        } else {
            LOGGER.warn("searchText was not found in grid: " + searchText);
        }
        return text;
    }

    public String getText(int rowIndex, int columnIndex) {
        GridCell cell = getCell(rowIndex, columnIndex);
        return cell.getHtmlText();
    }

    /**
     * returns if a specific Grid contains a certain element
     *
     * @param searchText  the element that is already part of the grid
     * @param columnIndex the column index where the comparison is done (STARTS AT 0)
     * @param compareText the text to which the element found is compared to
     * @return if a specific Grid contains a certain element
     */
    public boolean isTextPresent(String searchText, int columnIndex, String compareText) {
        String text = getText(searchText, columnIndex);
        return text != null && text.trim().equals(compareText);
    }

    public boolean checkboxSMSelectRow(int rowIndex) {
        if (ready(true)) {
            String path;
            if (rowIndex != -1) {
                GridRow gridRow = getRowLocator(rowIndex);
                String cls = gridRow.getAttributeClass();
                boolean isSelected = cls != null && cls.contains("x-grid3-row-selected");
                path = "//*[contains(@class, 'x-grid3-row-checker')]";
                WebLocator element = new WebLocator(gridRow, path);
                element.setInfoMessage("row-checker");
                if (element.exists()) {
                    // TODO (verify if is working) to scroll to this element (if element is not visible)
                    new WebLocator(this, "//*[contains(@class,'x-grid3-focus')]").sendKeys(Keys.TAB); //TODO work with selenium????
                    element.click();
                    LOGGER.info("Clicking on checkbox corresponding to line index: " + rowIndex);
                } else {
                    LOGGER.warn("Could not click on checkbox corresponding to line index: " + rowIndex + "; path = " + path);
                    return false;
                }
                cls = gridRow.getAttributeClass();
                return (cls != null && cls.contains("x-grid3-row-selected")) != isSelected;
            }
            return false;
        } else {
            LOGGER.warn("checkboxSMSelectRow : grid is not ready for use: " + toString());
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
            LOGGER.warn("checkboxSMSelectRow : grid is not ready for use: " + toString());
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
            LOGGER.warn("checkboxSMSelectRow : grid is not ready for use: " + toString());
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
                    LOGGER.debug("gridCellPath: " + gridCell.getPath());
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
                LOGGER.debug("path: " + gridCell.getPath());
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
     * @param searchText searchText
     * @return true or false
     */
    public boolean checkboxColumnSelect(String searchText) {
        boolean selected = false;
        if (ready(true)) {
            String gridPath = getPath();
            String path;
            int rowIndex = getRowIndex(searchText);
            if (rowIndex != -1) {
                path = getRowLocator(rowIndex).getPath() + "//div[contains(@class,'x-grid3-check-col')]";
                WebLocator element = new WebLocator(null, path);
                if (element.exists()) {
                    // TODO (verify if is working) to scroll to this element (if element is not visible)
                    new WebLocator(null, gridPath + "//*[contains(@class,'x-grid3-focus')]").sendKeys(Keys.TAB);
                    selected = isCheckBoxColumnSelected(searchText) || element.click();
                    LOGGER.info("Clicking on checkboxColumnSelect corresponding to line : " + searchText);
                } else {
                    LOGGER.warn("Could not click on checkboxColumnSelect corresponding to line : " + searchText + "; path = " + path);
                    return false;
                }
            }
        } else {
            LOGGER.warn("checkboxColumnSelect: grid is not ready for use: " + toString());
            selected = false;
        }
        return selected;
    }

    public boolean isCheckBoxColumnSelected(String searchText) {
        boolean isSelected = false;
        if (ready(true)) {
            int rowIndex = getRowIndex(searchText);
            if (rowIndex != -1) {
                String path = getRowLocator(rowIndex).getPath() + "//div[contains(@class,'x-grid3-check-col-on')]";
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
        //LOGGER.debug("waitToPopulate: " + seconds + "; " + toString());
        WebLocator firstRow = getRowLocator(1).setInfoMessage("first row");
        return firstRow.waitToRender(seconds * 1000L);
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