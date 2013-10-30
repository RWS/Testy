package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import org.apache.log4j.Logger;

public abstract class Row extends Cell {
    private static final Logger logger = Logger.getLogger(Row.class);

    public Row() {
        setClassName("Row");
    }

    protected void setRowCells(Cell... cells) {
        String path = "";
        for (Cell cell : cells) {
            String itemPathText = cell.getItemPathText();
            if (cell.getPosition() != -1 && !"".equals(itemPathText)) {
                path += " and " + getSearchPath(cell.getPosition(), Utils.fixPathSelector(itemPathText));
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
