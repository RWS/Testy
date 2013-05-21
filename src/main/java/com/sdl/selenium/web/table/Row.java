package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public abstract class Row extends WebLocator {
    private static final Logger logger = Logger.getLogger(Row.class);

    public Row(){
        setClassName("Row");
    }

    protected void setRowCells(Cell... cells){
        String path = "";
        for (Cell cell : cells) {
            if (cell.getPosition() != -1 && !"".equals(cell.getItemPathText())) {
                path += " and " + getSearchPath(cell.getPosition(), Utils.fixPathSelector(cell.getItemPathText()));
            } else {
                logger.warn("Please use : new TableCell(3, \"1234\", \"eq\")");
            }
        }
        setElPath("//" + getTag() + "[" + Utils.fixPathSelector(path) + "]");
    }

    protected String getSearchPath(int columnIndex, String textCondition) {
        return "count(td[" + columnIndex + "][" + textCondition + "]) > 0";
    }
}
