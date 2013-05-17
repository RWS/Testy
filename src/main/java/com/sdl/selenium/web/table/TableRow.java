package com.sdl.selenium.web.table;

import com.extjs.selenium.Utils;
import org.apache.log4j.Logger;

public class TableRow extends Row {
    private static final Logger logger = Logger.getLogger(TableRow.class);

    public TableRow() {
        setRenderMillis(200);
        setClassName("TableRow");
        setTag("tr");
    }

    public TableRow(Table table, String searchElement, String searchType) {
        this();
        setContainer(table);
        setText(searchElement);
        setSearchTextType(searchType);
    }

    public TableRow(Table table, Cell... cells) {
        this();
        setContainer(table);
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

    private String getSearchPath(int columnIndex, String textCondition) {
        return "count(td[" + columnIndex + "][" + textCondition + "]) > 0";
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
