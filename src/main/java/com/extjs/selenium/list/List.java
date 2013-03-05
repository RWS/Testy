package com.extjs.selenium.list;

import com.extjs.selenium.grid.GridCell;
import com.extjs.selenium.grid.GridPanel;
import com.extjs.selenium.grid.GridRow;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class List extends GridPanel {
    private static final Logger logger = Logger.getLogger(List.class);

    public List() {
        setClassName("List");
        setBaseCls("x-list-wrap");
    }

    public List(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public String getDefaultExcludePath(){
        return "";
    }

    public boolean selectRows(String[] values) {
        boolean select = false;
        if (hasWebDriver()) {
            logger.warn("//TODO not implemented for WebDriver");
//            http://code.google.com/p/selenium/issues/detail?id=3734&sort=-stars&colspec=ID%20Stars%20Type%20Status%20Priority%20Milestone%20Owner%20Summary
            /*Actions compAction = new Actions(driver);
            compAction = compAction.keyDown(Keys.CONTROL)
            compAction = compAction.click(ele1);
            compAction = compAction.click(ele2);
            compAction = compAction.KeyUp(Keys.CONTROL);
            compAction.build().perform();*/
        } else {
            selenium.controlKeyDown();
            for (String value : values) {
                select = rowSelect(value, false);
                if (!select) {
                    selenium.controlKeyUp();
                    return false;
                }
            }
            selenium.controlKeyUp();
        }
        return select;
    }

    @Override
    public GridCell getGridCell(String searchElement, Boolean startWidth) {
        String textCondition = startWidth ? ("starts-with(text(),'" + searchElement + "')") : ("text()='" + searchElement + "'");
        String cellPath = "//*[" + textCondition + "]";
        GridCell cell = new GridCell(this, cellPath);
        cell.setInfoMessage("cell(" + searchElement + ")");
        return cell;
    }

    @Override
    public boolean rowSelect(String searchElement, Boolean startWith) {
        //TODO When Override ScrollTop method, this method must be removed
        GridCell cell = getGridCell(searchElement, startWith);
        return cell.select();
    }

    @Override
    public GridRow getGridRow(int rowIndex){
        return new GridRow(this, "//dl[" + rowIndex + "]");
    }
}

