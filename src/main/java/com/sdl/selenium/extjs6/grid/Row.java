package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.extjs6.form.CheckBox;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.table.AbstractCell;
import com.sdl.selenium.web.utils.RetryUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Row extends com.sdl.selenium.web.table.Row {

    public Row() {
        super();
        setTag("table");
    }

    public Row(Locator container) {
        this();
        setContainer(container);
    }

    public Row(Locator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    public Row(Locator table, String searchElement, SearchType... searchTypes) {
        this(table);
        setText(searchElement, searchTypes);
    }

    public Row(Locator table, AbstractCell... cells) {
        this(table);
        List<AbstractCell> collect = Stream.of(cells).filter(t -> t.getXPathBuilder().getText() != null).collect((Collectors.toList()));
        setChildNodes(collect.stream().toArray(AbstractCell[]::new));
    }

    public Row(Locator table, int indexRow, AbstractCell... cells) {
        this(table, cells);
        setPosition(indexRow);
    }

    @Override
    public Cell getCell(int columnIndex) {
        return new Cell(this, columnIndex);
    }

    public void select() {
        scrollInGrid(this);
        if (!isSelected()) {
            doSelect();
        }
    }

    public void unSelect() {
        scrollInGrid(this);
        if (isSelected()) {
            doSelect();
        }
    }

    protected void doSelect() {
        CheckBox checkBox = new CheckBox(this).setBaseCls("x-grid-row-checker");
        if (!checkBox.waitToRender(500L, false)) {
            checkBox.setBaseCls("x-selmodel-checkbox");
        }
        checkBox.click();
    }

    public boolean isSelected() {
        String aClass = getAttributeClass();
        return !Strings.isNullOrEmpty(aClass) && aClass.contains("x-grid-item-selected");
    }

    public boolean scrollTo() {
        return RetryUtils.retryIfNotSame(70, true, () -> {
            Locator container = getXPathBuilder().getContainer();
            int lastRowVisibleIndex = new Row(container).findElements().size() - 1;
            Row row = new Row(container, lastRowVisibleIndex);
            WebLocatorUtils.scrollToWebLocator(row);
            return isElementPresent();
        });
    }

    private void scrollInGrid(Row row) {
        while (!row.waitToRender(100L, false)) {
            Grid grid;
            if (getXPathBuilder().getContainer() instanceof Grid) {
                grid = (Grid) getXPathBuilder().getContainer();
            } else {
                if (getXPathBuilder().getContainer().getXPathBuilder().getContainer() instanceof Grid) {
                    grid = (Grid) getXPathBuilder().getContainer().getXPathBuilder().getContainer();
                } else {
                    grid = (Grid) getXPathBuilder().getContainer().getXPathBuilder().getContainer().getXPathBuilder().getContainer();
                }
            }
            if (!grid.scrollPageDown()) {
                break;
            }
        }
    }
}