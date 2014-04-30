package com.sdl.selenium.web.table;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;

public abstract class Row extends WebLocator {
    private static final Logger logger = Logger.getLogger(Row.class);

    public Row() {
        setClassName("Row");
    }

    protected void setRowCells(Cell... cells) {
        String path = "";
        for (Cell cell : cells) {
            String itemPathText = cell.getItemPathText();
            if (cell.getPosition() != -1 && !"".equals(itemPathText) && itemPathText != null) {
                path += " and " + getSearchPath(cell.getPosition(),itemPathText);
            } else if (cell.getPosition() == -1 && !"".equals(itemPathText) && itemPathText != null) {
                path += " and " + getSearchPath(itemPathText);
            } else {
                logger.warn("cell.getPosition()=" + cell.getPosition());
                logger.warn("itemPathText=" + itemPathText);
                logger.warn("Please use : new TableCell(3, \"1234\", \"eq\")");
            }
        }
        setElPath("//" + getTag() + (isVisibility() ? "[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]" : "") + "[" + Utils.fixPathSelector(path) + "]");
    }

    protected String getSearchPath(int columnIndex, String textCondition) {
        return "count(td[" + columnIndex + "][" + textCondition + "]) > 0";
    }

    protected String getSearchPath(String textCondition) {
        return "count(td/*[" + textCondition + "]) > 0";
    }
}
