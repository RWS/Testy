package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.extjs6.form.CheckBox;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;

public class Cell extends com.sdl.selenium.web.table.Cell {

    public Cell() {
        setRenderMillis(200);
        setClassName("Cell");
        setTag("td");
        getXPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Cell(Locator container) {
        this();
        setContainer(container);
    }

    public Cell(Locator container, int columnIndex) {
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

    public Cell(Locator container, int columnIndex, String columnText, SearchType... searchTypes) {
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

    public Boolean isChecked() {
        CheckBox checkBox = new CheckBox(this).setBaseCls("x-grid-checkcolumn");
        if (!checkBox.waitToRender(500L, false)) {
            return null;
        }
        String attributeClass = checkBox.getAttributeClass();
        if (Strings.isNullOrEmpty(attributeClass)) {
            attributeClass = checkBox.getAttributeClass();
        }
        return attributeClass.contains("x-grid-checkcolumn-checked");
    }

    private void scrollInGrid(Cell cell) {
        while (!cell.waitToRender(100)) {
            Grid grid;
            try {
                grid = (Grid) getXPathBuilder().getContainer();
            } catch (ClassCastException e) {
                grid = (Grid) getXPathBuilder().getContainer().getXPathBuilder().getContainer();
            }
            if (!grid.scrollPageDown()) {
                break;
            }
        }
    }
}