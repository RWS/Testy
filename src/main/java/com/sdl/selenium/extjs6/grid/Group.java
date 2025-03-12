package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        setResultIdx(indexRow);
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
        String cls = tr.getAttribute("class", true);
        return cls != null && cls.contains("x-grid-group-hd-collapsed");
    }

    public boolean expand() {
        return !isCollapsed() || click();
    }

    public boolean collapse() {
        return isCollapsed() || click();
    }

    public List<Row> getRows() {
        WebLocator container = getPathBuilder().getContainer();
        Row row = new Row(container).setFinalXPath(" | //table[preceding-sibling::table[." + group.getXPath() + "] and not(.//div[contains(@class, 'x-grid-group-title')])]").setChildNodes(group);
        int size = row.size();
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Row rowTmp;
            if (i == 1) {
                rowTmp = new Row().setTag("tr").setChildNodes(group);
            } else {
                rowTmp = new Row().setElPath("(" + row.getXPath() + ")[" + i + "]");
            }
            rows.add(rowTmp);
            if (i == 1) {
                rowTmp = rows.get(0).getNextRow("/following-sibling::", "tr");
                rows.add(rowTmp);
            }
        }
        return rows;
    }

    public Row getRow(AbstractCell... cells) {
        Row row = new Row(this) {
            public boolean isCollapsed() {
                WebLocator locator = new WebLocator(this).setRoot("/ancestor::").setTag("table");
                String aClass = locator.getAttributeClass();
                return !Strings.isNullOrEmpty(aClass) && aClass.contains("x-grid-row-collapsed");
            }

            protected boolean doExpanded() {
                Cell cell = getCell(1);
                return cell.doClick();
            }
        }.setTag("tr").setRoot("//").setClasses("x-grid-row").setChildNodes(cells);
        if (!row.isPresent()) {
            row.setRoot("/following-sibling::table//");
        }
        return row;
    }

    public String getNameGroup() {
        return getText(true);
    }
}