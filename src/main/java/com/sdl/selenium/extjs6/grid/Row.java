package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.extjs6.form.CheckBox;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Row extends com.sdl.selenium.web.table.Row {
    private static final Logger LOGGER = LoggerFactory.getLogger(Row.class);

    public Row() {
        super();
        setTag("table");
    }

    public Row(WebLocator container) {
        this();
        setContainer(container);
    }

    public Row(WebLocator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    public Row(WebLocator table, String searchElement, SearchType... searchTypes) {
        this(table);
        setText(searchElement, searchTypes);
    }

    public Row(WebLocator table, AbstractCell... cells) {
        this(table);
        setChildNodes(cells);
    }

    public Row(WebLocator table, int indexRow, AbstractCell... cells) {
        this(table, indexRow);
        setChildNodes(cells);
    }

    public void select() {
        scrollInGrid(this);
        if (!isSelected()) {
            CheckBox checkBox = new CheckBox(this).setBaseCls("x-grid-row-checker");
            checkBox.click();
        }
    }

    public void unSelect() {
        scrollInGrid(this);
        if (isSelected()) {
            CheckBox checkBox = new CheckBox(this).setBaseCls("x-grid-row-checker");
            checkBox.click();
        }
    }

    public boolean isSelected() {
        return getAttributeClass().contains("x-grid-item-selected");
    }

    public boolean scrollTo() {
        while (!isElementPresent()) {
            WebLocator container = getPathBuilder().getContainer();
            int lastRowVisibleIndex = new Row(container).findElements().size() - 1;
            Row row = new Row(container, lastRowVisibleIndex);
            WebLocatorUtils.scrollToWebLocator(row);
        }
        return isElementPresent();
    }

    private void scrollInGrid(Row row) {
        int time = 0;
        while (!row.waitToRender(100) && time < 1000) {
            ((Grid)getPathBuilder().getContainer()).scrollPageDown();
            time++;
        }
    }
}
