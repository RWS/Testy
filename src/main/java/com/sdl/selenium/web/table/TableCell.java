package com.sdl.selenium.web.table;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableCell extends Cell {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableCell.class);

    public TableCell(By... bys) {
        getPathBuilder().setDefaultSearchTextType(SearchType.DEEP_CHILD_NODE_OR_SELF);
        getPathBuilder().defaults(By.tag("td")).init(bys);
        setRenderMillis(200);
    }

    public TableCell(WebLocator container, By... bys) {
        this(bys);
        getPathBuilder().setContainer(container);
    }

    public TableCell(WebLocator container, int columnIndex) {
        this(container, By.tagIndex(columnIndex));
    }

    public TableCell(int columnIndex, String columnText, SearchType... searchType) {
        this(By.tagIndex(columnIndex), By.text(columnText, searchType));
    }

    public TableCell(WebLocator container, int columnIndex, String columnText, SearchType... searchType) {
        this(container, By.tagIndex(columnIndex), By.text(columnText, searchType));
    }
}
