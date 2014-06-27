package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;

import java.util.List;

public abstract class Row extends WebLocator {
    private static final Logger logger = Logger.getLogger(Row.class);

    public Row() {
        setClassName("Row");
    }

    protected void setRowCells(Cell... cells) {
        String path = "";
        for (Cell cell : cells) {
            String text = cell.getText();
            List<SearchType> searchTextType = cell.getSearchTextType();
            WebLocator cellEl = new WebLocator().setText(cell.getText(), searchTextType.toArray(new SearchType[searchTextType.size()]));
            String tagCell = cell.getTag();
            if (cell.getPosition() != -1 && !"".equals(text) && text != null) {
                path += " and " + getSearchPath(cell.getPosition(), cellEl, tagCell);
            } else if (cell.getPosition() == -1 && !"".equals(text) && text != null) {
                path += " and " + getSearchPath(cellEl, tagCell);
            } else {
                logger.warn("cell.getPosition()=" + cell.getPosition());
                logger.warn("text=" + text);
                logger.warn("Please use : new TableCell(3, \"1234\", \"eq\")");
            }
        }
        setElPath("//" + getTag() + (isVisibility() ? "[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]" : "") + "[" + Utils.fixPathSelector(path) + "]");
    }

    protected String getSearchPath(int columnIndex, WebLocator cellEl, String tag) {
        if (tag == null || "".equals(tag)) {
            tag = "td";
        }
        return "count(" + tag + "[" + columnIndex + "]" + cellEl.getPath().substring(3) + ") > 0";
    }

    protected String getSearchPath(WebLocator cellEl, String tag) {
        if (tag == null || "".equals(tag)) {
            tag = "td";
        }
        return "count(" + tag + cellEl.getPath().substring(1) + ") > 0";
    }
}
