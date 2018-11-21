package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;

import java.util.ArrayList;
import java.util.List;

public class Group extends WebLocator {

    private static String nameGroup = null;
    private WebLocator group = new WebLocator().setClasses("x-grid-group-title").setTemplate("title", "contains(.,'%s')");

    public Group() {
        setTag("table");
//        setTemplate("groupTextRow", "count(*//text()[contains(.,'%s')]) > 0");
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
        nameGroup = groupName;
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
        Grid grid = (Grid) getPathBuilder().getContainer();
        String toGroup = grid.getNextGroupName(nameGroup);
        if (!expand()) {
            return null;
        }
        WebLocator group;
        if (Strings.isNullOrEmpty(toGroup)) {
            group = new WebLocator(grid).setTag("").setRoot("//table[count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0][1] | //table[count(.//text()[contains(.,'" + nameGroup + "')]) > 0 and .//tr//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ')]]/following-sibling::table");
        } else {
            group = new WebLocator(grid).setTag("").setRoot("//table[//table[count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0][1] | preceding-sibling::table//*[1][count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0] and following-sibling::table[count(.//*[@class='x-grid-group-title' and contains(translate(text(),'" + toGroup.toUpperCase() + "','" + toGroup.toLowerCase() + "'),'" + toGroup.toLowerCase() + "')]) > 0]]");
        }
        int size = group.size();
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Row rowTemp = new Row().setElPath("(" + group.getXPath() + ")[" + i + "]");
            Row row = new Row(rowTemp).setTag("tr").setBaseCls("x-grid-row");
            rows.add(row);
        }
        return rows;
    }

    public Row getRow(AbstractCell... cells) {
        Grid grid = (Grid) getPathBuilder().getContainer();
        String toGroup = grid.getNextGroupName(nameGroup);
        if (!expand()) {
            return null;
        }
        WebLocator group;
        if (Strings.isNullOrEmpty(toGroup)) {
            group = new WebLocator(grid).setTag("").setRoot("//table[count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0][1] | //table[count(.//text()[contains(.,'" + nameGroup + "')]) > 0 and .//tr//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ')]]/following-sibling::table");
        } else {
            group = new WebLocator(grid).setTag("").setRoot("//table[//table[count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0][1] | preceding-sibling::table//*[1][count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0] and following-sibling::table[count(.//*[@class='x-grid-group-title' and contains(translate(text(),'" + toGroup.toUpperCase() + "','" + toGroup.toLowerCase() + "'),'" + toGroup.toLowerCase() + "')]) > 0]]");
        }
        return new Row(group).setTag("").setRoot("").setChildNodes(cells);
    }

    public String getNameGroup() {
        return getText().split(" \\(")[0];
    }
}