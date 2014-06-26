package com.sdl.selenium.web.table;

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

    public TableCell(WebLocator container, int columnIndex) {
        this(container);
        setPosition(columnIndex);
    }

    public TableCell(int columnIndex, String columnText, SearchType ...searchType) {
        this();
        setPosition(columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    public TableCell(WebLocator container, int columnIndex, String columnText, SearchType ...searchType) {
        this(container, columnIndex);
        setText(columnText);
        setSearchTextType(searchType);
    }

    @Override
    protected String addPositionToPath(String itemPath) {
        if (hasPosition()) {
            String itemPathText = getItemPathText();
            itemPath = "//" + getTag() + "[" + getPosition() + "]" + ("".equals(itemPathText) ? "" : (itemPathText == null ? "" : "[" + itemPathText + "]"));
        }
        return itemPath;
    }

    @Override
    protected String getItemPathText() {
        String selector = super.getItemPathText();
        if (!"".equals(selector) && selector != null) {
            selector = selector + " or count(.//*[" + selector + "]) > 0";
        }
        return selector;
    }
}
