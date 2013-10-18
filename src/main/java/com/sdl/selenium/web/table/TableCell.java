package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TableCell extends Cell {
    private static final Logger logger = Logger.getLogger(TableCell.class);

    public TableCell() {
        setRenderMillis(200);
        setClassName("TableCell");
        setTag("td");
    }

    public TableCell(WebLocator container) {
        this();
        setContainer(container);
    }

    public TableCell(Row row, int columnIndex) {
        this(row);
        setPosition(columnIndex);
    }

    public TableCell(int columnIndex, String columnText, SearchType searchType) {
        this();
        setPosition(columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    public TableCell(Row row, int columnIndex, String columnText, SearchType searchType) {
        this(row, columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    @Override
    protected String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            itemPath = "//" + getTag() + "[" + getPosition() + "]" + ("".equals(getItemPathText()) ? "" : "[" + Utils.fixPathSelector(getItemPathText()) + "]");
        }
        return itemPath;
    }

    @Override
    protected String getItemPathText() {
        String selector = super.getItemPathText();
        if (!"".equals(selector)) {
            String text = Utils.fixPathSelector(selector);
            selector = text + " or count(.//*[" + text + "]) > 0";
        }
        return selector;
    }
}
