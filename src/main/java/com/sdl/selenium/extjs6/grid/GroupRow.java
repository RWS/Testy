package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupRow extends Row {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRow.class);
    private WebLocator group = new WebLocator().setClasses("x-group-hd-container");

    public GroupRow() {
        super();
        group.setTemplate("groupTextRow", "count(*//text()[contains(.,'%s')]) > 0");
        setChildNodes(group);
    }

    public GroupRow(WebLocator container) {
        this();
        setContainer(container);
    }

    public GroupRow(WebLocator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    public GroupRow(WebLocator table, String groupName) {
        this(table);
        group.setTemplateValue("groupTextRow", groupName);
    }

    public GroupRow(WebLocator table, AbstractCell... cells) {
        this(table);
        List<AbstractCell> collect = Stream.of(cells).filter(t -> t.getPathBuilder().getText() != null).collect((Collectors.toList()));
        setChildNodes(collect.toArray(new AbstractCell[collect.size()]));
    }

    public GroupRow(WebLocator table, int indexRow, AbstractCell... cells) {
        this(table, cells);
        setPosition(indexRow);
    }

    public Row getRowSiblings() {
        WebLocator group = new WebLocator().setExcludeClasses("x-group-hd-container").setTag("td");
        Row row = new Row(this).setTag("following-sibling::table").setRoot("/").setChildNodes(group);
        return new Row().setElPath(row.getXPath() + " | " + getXPath());
    }

    public static void main(String[] args) {
        GroupRow groupRow = new GroupRow(null, "Cuisine: American");
        Row row = groupRow.getRowSiblings().setResultIdx(2);
        LOGGER.info("{}", row.getCell(2).getXPath());
    }
}
