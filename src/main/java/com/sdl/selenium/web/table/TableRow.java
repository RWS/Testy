package com.sdl.selenium.web.table;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TableRow extends Row {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableRow.class);

    private XPathBuilder pathBuilder = getPathBuilder();
    public TableRow(By ...bys) {
        pathBuilder.defaults(By.tag("tr")).init(bys);
        setRenderMillis(200);
    }

    public TableRow(WebLocator container) {
        this(By.container(container));
    }

    public TableRow(WebLocator container, By ...bys) {
        this(bys);
        pathBuilder.setContainer(container);
    }

    public TableRow(WebLocator container, int indexRow) {
        this(container, By.position(indexRow));
    }

    public TableRow(WebLocator table, String searchElement, SearchType searchType) {
        this(table, By.text(searchElement, searchType, SearchType.DEEP_CHILD_NODE_OR_SELF));
    }

    public TableRow(WebLocator table, Cell... cells) {
        this(table, By.childNodes(cells));
    }

    public TableRow(WebLocator table, int indexRow, Cell... cells) {
        this(table, By.position(indexRow), By.childNodes(cells));
    }

    public List<String> getCellsText() {
        WebLocator columnsEl = new WebLocator(this).setTag("td");
        int columns = columnsEl.size() + 1;

        List<String> list = new ArrayList<String>();
        for (int j = 1; j < columns; j++) {
            TableCell cell = new TableCell(this, j);
            list.add(cell.getHtmlText());
        }
        return list;
    }
}
