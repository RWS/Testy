package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;

import java.util.ArrayList;
import java.util.List;

public class Group extends WebLocator {

    private final WebLocator group = new WebLocator().setClasses("x-grid-group-title").setTemplate("title", "contains(.,'%s')");

    public Group() {
        setTag("table");
        setChildNodes(group);
    }

    public Group(WebLocator container) {
        this();
        setContainer(container);
    }

    public Group(WebLocator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    public Group(WebLocator container, String groupName) {
        this(container);
        group.setTemplateValue("title", groupName);
    }

    public Group(WebLocator container, String groupName, int indexRow) {
        this(container, groupName);
        setResultIdx(indexRow);
    }

    public boolean isCollapsed() {
        WebLocator tr = new WebLocator(this).setTag("tr").setPosition(1);
        String cls = tr.getAttributeClass();
        return cls != null && cls.contains("x-grid-group-hd-collapsed");
    }

    public boolean expand() {
        return !isCollapsed() || click();
    }

    public boolean collapse() {
        return isCollapsed() || click();
    }

    public List<Row> getRows() {
        expand();
        Row firstRow = new Row(this).setTag("tr").setClasses("x-grid-row");
        Row row = new Row(this).setTag("tr").setRoot("//following::").setClasses("x-grid-row");
        int size = row.size();
        ArrayList<Row> rows = new ArrayList<>();
        rows.add(firstRow);
        for (int i = 1; i <= size; i++) {
            final Row tmpRow = new Row(this).setTag("tr").setRoot("//following::").setClasses("x-grid-row").setResultIdx(i);
            rows.add(tmpRow);
        }
        return rows;
    }

    public Row getRow(AbstractCell... cells) {
        return new Row(this) {
            public boolean isCollapsed() {
                WebLocator locator = new WebLocator(this).setRoot("/ancestor::").setTag("table");
                String aClass = locator.getAttributeClass();
                return !Strings.isNullOrEmpty(aClass) && aClass.contains("x-grid-row-collapsed");
            }

            protected boolean doExpanded() {
                Cell cell = getCell(1);
                return cell.doClick();
            }
        }.setTag("tr").setRoot("//following::").setClasses("x-grid-row").setChildNodes(cells);
    }

    public String getNameGroup() {
        WebLocator groupTitle = new WebLocator(this).setClasses("x-grid-group-title");
        WebLocator spanTitle = new WebLocator(groupTitle).setResultIdx(1);
        return spanTitle.getText();
    }
}