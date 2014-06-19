package com.extjs.selenium.list;

import com.extjs.selenium.grid.GridCell;
import com.extjs.selenium.grid.GridPanel;
import com.extjs.selenium.grid.GridRow;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.thoughtworks.selenium.Selenium;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

public class List extends GridPanel {
    private static final Logger logger = Logger.getLogger(List.class);

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
        if (WebDriverConfig.hasWebDriver()) {
            sendKeys(Keys.CONTROL, Keys.DOWN);
            for (String value : values) {
                select = rowSelect(value, SearchType.EQUALS);
                if (!select) {
                    sendKeys(Keys.UP);
                    return false;
                }
            }
            sendKeys(Keys.UP);
        } else {
            Selenium selenium = WebDriverConfig.getSelenium();
            selenium.controlKeyDown();
            for (String value : values) {
                select = rowSelect(value, SearchType.EQUALS);
                if (!select) {
                    selenium.controlKeyUp();
                    return false;
                }
            }
            selenium.controlKeyUp();
        }
        return select;
    }

    public boolean selectRowsWithJs(String ...values) {
        String id = getAttributeId();
        return (Boolean) WebLocatorUtils.doExecuteScript("return (function(m,v){m.setValue(v);return m.getValue() == v.toLowerCase()})(Ext.getCmp('" + id + "'),'" + StringUtils.join(values, ",") + "');");
    }

    public boolean isSelectedRows(String ...values) {
        boolean select = false;
        for (String value : values) {
            WebLocator webLocator = new WebLocator(getGridCell(value, false), "/parent::*/parent::dl");
            select = webLocator.getAttributeClass().contains("ux-mselect-selected");
            if (!select) {
                return false;
            }
        }
        return select;
    }

    @Override
    public GridCell getGridCell(String searchElement, SearchType searchType) {
        String textCondition = searchType.equals(SearchType.STARTS_WITH) ? ("starts-with(text(),'" + searchElement + "')") : ("text()='" + searchElement + "'");
        String cellPath = "//*[" + textCondition + "]";
        GridCell cell = new GridCell(this, cellPath);
        cell.setInfoMessage("cell(" + searchElement + ")");
        return cell;
    }

    /**
     * @param searchElement searchElement
     * @param searchType searchType
     * @return true or fasle
     */
    @Override
    public boolean rowSelect(String searchElement, SearchType searchType) {
        //TODO When Override ScrollTop method, this method must be removed
        GridCell cell = getGridCell(searchElement, searchType);
        return cell.select();
    }

    @Override
    public GridRow getRowLocator(int rowIndex) {
        return new GridRow(this, "//dl[" + rowIndex + "]");
    }
}

