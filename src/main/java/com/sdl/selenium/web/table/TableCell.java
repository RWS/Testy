package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import org.apache.log4j.Logger;

public class TableCell extends Cell {
    private static final Logger logger = Logger.getLogger(TableCell.class);

    public TableCell() {
        setRenderMillis(200);
        setClassName("TableCell");
        setTag("td");
    }

    public TableCell(TableRow tableRow, int columnIndex) {
        this();
        setContainer(tableRow);
        setPosition(columnIndex);
    }

    public TableCell(int columnIndex, String columnText, String searchType) {
        this();
        setPosition(columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    public String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            itemPath = "//" + getTag() + "[" + getPosition() + "]" + ("".equals(getItemPathText()) ? "" : "[" + Utils.fixPathSelector(getItemPathText()) + "]");
        }
        return itemPath;
    }

    public String getItemPathText() {
        String selector = super.getItemPathText();
        if (!"".equals(selector)) {
            String text = Utils.fixPathSelector(selector);
            selector = text + " or count(.//*[" + text + "]) > 0";
        }
        return selector;
    }
}
