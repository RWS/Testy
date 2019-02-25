package com.sdl.selenium.web.table;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.XPathBuilder;

public class Cell extends AbstractCell {

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

    public XPathBuilder createXPathBuilder() {
        return new XPathBuilder() {
            @Override
            protected String addPositionToPath(String itemPath) {
                if (hasPosition()) {
                    int beginIndex = 2 + getTag().length();
                    itemPath = getRoot() + getTag() + "[" + getPosition() + "]" + itemPath.substring(beginIndex);
                }
                return itemPath;
            }
        };
    }
}
