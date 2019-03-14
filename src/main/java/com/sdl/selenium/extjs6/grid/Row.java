package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import com.sdl.selenium.web.utils.RetryUtils;

import java.util.stream.Stream;

public class Row extends com.sdl.selenium.web.table.Row {
    private String version;

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
        setChildNodes(Stream.of(cells).filter(t -> t.getPathBuilder().getText() != null).toArray(AbstractCell[]::new));
    }

    public Row(WebLocator table, int indexRow, AbstractCell... cells) {
        this(table, cells);
        setPosition(indexRow);
    }

    @Override
    public Cell getCell(int columnIndex) {
        return new Cell(this, columnIndex);
    }

    private String getVersion() {
        Grid grid = (Grid) getPathBuilder().getContainer();
        if (grid != null) {
            this.version = grid.getVersion();
        }
        return version;
    }

    public <T extends Row> T setVersion(String version) {
        this.version = version;
        return (T) this;
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
        WebLocator checkBox = new WebLocator(this);
        if ("6.7.0".equals(getVersion())) {
            checkBox.setBaseCls("x-selmodel-column");
        } else {
            checkBox.setBaseCls("x-grid-row-checker");
        }
        checkBox.click();
    }

    public boolean isSelected() {
        String aClass = getAttributeClass();
        return !Strings.isNullOrEmpty(aClass) && aClass.contains("x-grid-item-selected");
    }

    public void expand() {
        scrollInGrid(this);
        if (isCollapsed()) {
            doExpanded();
        }
    }

    public void collapse() {
        scrollInGrid(this);
        if (!isCollapsed()) {
            doExpanded();
        }
    }

    protected void doExpanded() {
        WebLocator expander = new WebLocator(this).setBaseCls("x-grid-row-expander");
        expander.click();
    }

    public boolean isCollapsed() {
        String aClass = getAttributeClass();
        return !Strings.isNullOrEmpty(aClass) && aClass.contains("x-grid-row-collapsed");
    }

    public boolean scrollTo() {
        return RetryUtils.retryIfNotSame(70, true, () -> {
            WebLocator container = getPathBuilder().getContainer();
            int lastRowVisibleIndex = new Row(container).findElements().size() - 1;
            Row row = new Row(container, lastRowVisibleIndex);
            WebLocatorUtils.scrollToWebLocator(row);
            return isElementPresent();
        });
    }

    private void scrollInGrid(Row row) {
        while (!row.waitToRender(100L, false)) {
            Grid grid;
            if (getPathBuilder().getContainer() instanceof Grid) {
                grid = (Grid) getPathBuilder().getContainer();
            } else {
                if (getPathBuilder().getContainer().getPathBuilder().getContainer() instanceof Grid) {
                    grid = (Grid) getPathBuilder().getContainer().getPathBuilder().getContainer();
                } else {
                    grid = (Grid) getPathBuilder().getContainer().getPathBuilder().getContainer().getPathBuilder().getContainer();
                }
            }
            if (!grid.scrollPageDown()) {
                break;
            }
        }
    }
}