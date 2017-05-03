package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.extjs6.form.CheckBox;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cell extends com.sdl.selenium.web.table.Cell {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cell.class);

    public Cell() {
        setRenderMillis(200);
        setClassName("Cell");
        setTag("td");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Cell(WebLocator container) {
        this();
        setContainer(container);
    }

    public Cell(WebLocator container, int columnIndex) {
        this(container);
        setPosition(columnIndex);
    }

    public Cell(String columnText, SearchType... searchTypes) {
        this();
        setText(columnText, searchTypes);
    }

    public Cell(int columnIndex, String columnText, SearchType... searchTypes) {
        this();
        setPosition(columnIndex);
        setText(columnText, searchTypes);
    }

    public Cell(WebLocator container, int columnIndex, String columnText, SearchType... searchTypes) {
        this(container, columnIndex);
        setText(columnText, searchTypes);
    }

    public void check() {
        scrollInGrid(this);
        if (!isChecked()) {
            CheckBox checkBox = new CheckBox(this).setBaseCls("x-grid-checkcolumn");
            checkBox.click();
        }
    }

    public void unCheck() {
        scrollInGrid(this);
        if (isChecked()) {
            CheckBox checkBox = new CheckBox(this).setBaseCls("x-grid-checkcolumn");
            checkBox.click();
        }
    }

    public boolean isChecked() {
        CheckBox checkBox = new CheckBox(this).setBaseCls("x-grid-checkcolumn");
        return checkBox.getAttributeClass().contains("x-grid-checkcolumn-checked");
    }

    private void scrollInGrid(Cell cell) {
        int time = 0;
        while (!cell.waitToRender(100) && time < 1000) {
            ((Grid)getPathBuilder().getContainer()).scrollPageDown();
            time++;
        }
    }
}
