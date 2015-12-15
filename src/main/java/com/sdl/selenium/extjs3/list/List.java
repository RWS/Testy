package com.sdl.selenium.extjs3.list;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.extjs3.grid.GridCell;
import com.sdl.selenium.extjs3.grid.GridPanel;
import com.sdl.selenium.extjs3.grid.GridRow;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class List extends GridPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(List.class);

    public List() {
        setClassName("List");
        setBaseCls("ux-form-multiselect");
        setElPathSuffix("exclude-hide-cls", null);
    }

    public List(WebLocator container) {
        this();
        setContainer(container);
    }

    public boolean selectRows(String ...values) {
        boolean select = false;
        sendKeys(Keys.CONTROL, Keys.DOWN);
        for (String value : values) {
            select = rowSelect(value, SearchType.EQUALS);
            if (!select) {
                sendKeys(Keys.UP);
                return false;
            }
        }
        sendKeys(Keys.UP);
        return select;
    }

    public boolean selectRowsWithJs(String ...values) {
        String id = getAttributeId();
        return (Boolean) WebLocatorUtils.doExecuteScript("return (function(m,v){m.setValue(v);return m.getValue() == v.toLowerCase()})(Ext.getCmp('" + id + "'),'" + StringUtils.join(values, ",") + "');");
    }

    public boolean isSelectedRows(String ...values) {
        boolean select = false;
        for (String value : values) {
            WebLocator webLocator = new WebLocator(getCell(value)).setElPath("/parent::*/parent::dl");
            select = webLocator.getAttributeClass().contains("ux-mselect-selected");
            if (!select) {
                return false;
            }
        }
        return select;
    }

    @Override
    public GridCell getCell(String searchElement, SearchType... searchTypes) {
        WebLocator textCell = new WebLocator().setText(searchElement, searchTypes);
        GridCell cell = new GridCell().setContainer(this).setElPath(textCell.getXPath());
        cell.setInfoMessage("cell(" + searchElement + ")");
        return cell;
    }

    /**
     * @param searchElement searchElement
     * @param searchTypes searchType
     * @return true or fasle
     */
    @Override
    public boolean rowSelect(String searchElement, SearchType... searchTypes) {
        //TODO When Override ScrollTop method, this method must be removed
        GridCell cell = getCell(searchElement, searchTypes);
        return cell.select();
    }

    /**
     * @deprecated use {@link #getRow(int)}
     */
    public GridRow getRowLocator(int rowIndex) {
        return getRow(rowIndex);
    }

    @Override
    public GridRow getRow(int rowIndex) {
        return new GridRow(this).setElPath("//dl[" + rowIndex + "]");
    }
}

