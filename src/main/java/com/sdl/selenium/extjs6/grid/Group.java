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
        WebLocator group = new WebLocator(grid).setTag("").setRoot("//table[//table[count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0][1] | preceding-sibling::table//*[1][count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0]" + (Strings.isNullOrEmpty(toGroup) ? "]" : " and following-sibling::table[count(.//*[@class='x-grid-group-title' and contains(.,'" + toGroup + "')]) > 0]]"));
        int size = group.size();
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Row rowTemp = new Row().setElPath("(" + group.getXPath() + ")[" + i + "]");
            Row row = new Row(rowTemp).setTag("tr").setBaseCls("x-grid-row");
            rows.add(row);
        }
        return rows;
    }

    public Row getRow(AbstractCell ...cells){
        Grid grid = (Grid) getPathBuilder().getContainer();
        String toGroup = grid.getNextGroupName(nameGroup);
        if (!expand()) {
            return null;
        }
        WebLocator group = new WebLocator(grid).setTag("").setRoot("//table[//table[count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0][1] | preceding-sibling::table//*[1][count(.//*[@class='x-grid-group-title' and contains(.,'" + nameGroup + "')]) > 0]" + (Strings.isNullOrEmpty(toGroup) ? "]" : " and following-sibling::table[count(.//*[@class='x-grid-group-title' and contains(.,'" + toGroup + "')]) > 0]]"));
        return new Row(group).setTag("").setRoot("").setChildNodes(cells);
    }

    public String getNameGroup() {
        return getText().split(" \\(")[0];
    }
}