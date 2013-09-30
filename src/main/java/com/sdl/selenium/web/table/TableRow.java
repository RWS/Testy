package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TableRow extends Row {
    private static final Logger logger = Logger.getLogger(TableRow.class);

    public TableRow() {
        setRenderMillis(200);
        setClassName("TableRow");
        setTag("tr");
    }

    public TableRow(WebLocator container){
        this();
        setContainer(container);
    }

    public TableRow(WebLocator table, String searchElement, SearchType searchType) {
        this(table);
        setText(searchElement);
        setSearchTextType(searchType);
    }

    public TableRow(WebLocator table, Cell... cells) {
        this(table);
        setRowCells(cells);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        return "//" + getTag() + ("".equals(selector) ? "" : "[" + selector + "]");
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
