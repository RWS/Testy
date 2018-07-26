package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Group extends Row {
    private static final Logger LOGGER = LoggerFactory.getLogger(Group.class);

    private WebLocator group = new WebLocator().setClasses("x-group-hd-container").setTag("td");
    private static String nameGroup = null;

    public Group() {
        super();
        group.setTemplate("groupTextRow", "count(*//text()[contains(.,'%s')]) > 0");
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
        group.setTemplateValue("groupTextRow", groupName);
//        setContainer(container);
//        setTemplate("groupTextRow", "count(.//text()[contains(.,'%1$s')]) > 0] | //table[count(.//text()[contains(.,'%1$s')]) > 0]/following-sibling::table[following-sibling::table//tr//td[not(contains(concat(' ', @class, ' '), ' x-group-hd-container '))] | self::table//tr//td[not(contains(concat(' ', @class, ' '), ' x-group-hd-container '))]");
    }

    public Group(WebLocator container, String groupName, AbstractCell... cells) {
        this(null, groupName);
        List<AbstractCell> collect = Stream.of(cells).filter(t -> t.getPathBuilder().getText() != null).collect((Collectors.toList()));
        WebLocator xPath = new WebLocator().setChildNodes(collect.toArray(new AbstractCell[collect.size()])).setRoot("").setTag("");
        setElPath("(" + container.getXPath() + getXPath() + ")" + xPath.getXPath());
    }

    public Group(WebLocator container, int indexRow, AbstractCell... cells) {
        super(container, indexRow, cells);
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
        if (!expand()) {
            return null;
        }
        Row group = new Row(this).setTag("").setRoot(" | //table[count(.//text()[contains(.,'" + nameGroup + "')]) > 0]/following-sibling::table[following-sibling::table//tr//td[not(contains(concat(' ', @class, ' '), ' x-group-hd-container '))] | self::table//tr//td[not(contains(concat(' ', @class, ' '), ' x-group-hd-container '))]]");
        int size = group.size() + 1;
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            Row rowTemp = new Row().setElPath("(" + group.getXPath() + ")[" + i + "]");
            Row row = new Row(rowTemp).setTag("tr").setBaseCls("x-grid-row");
            rows.add(row);
        }
        return rows;
    }

    public List<Row> getRows(String toGroup) {
        if (!expand()) {
            return null;
        }
        Row group = new Row(this).setTag("").setRoot(" | //table[count(.//text()[contains(.,'" + nameGroup + "')]) > 0 and .//tr//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ')]]/following-sibling::table" + (Strings.isNullOrEmpty(toGroup) ? "" : "[following::table[.//tr//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ')] and count(.//text()[contains(.,'" + toGroup + "')]) > 0]]"));
        int size = group.size() + 1;
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            Row rowTemp = new Row().setElPath("(" + group.getXPath() + ")[" + i + "]");
            Row row = new Row(rowTemp).setTag("tr").setBaseCls("x-grid-row");
            rows.add(row);
        }
        return rows;
    }

    public static void main(String[] args) {
        Group group = new Group(null, "Cuisine: Coffee");
        List<Row> rows = group.getRows();
        LOGGER.info("{}", rows.get(0).getXPath());
    }
}
