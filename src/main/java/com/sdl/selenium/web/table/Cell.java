package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Cell extends AbstractCell {

    public Cell() {
        setRenderMillis(200);
        setClassName("Cell");
        setTag("td");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Cell(WebLocator container) {
        this();
        setContainer(container);
    }

    public Cell(WebLocator container, int columnIndex) {
        this(container);
        setTemplateValue("tagAndPosition", columnIndex + "");
    }

    public Cell(String columnText, SearchType... searchTypes) {
        this();
        setText(columnText, searchTypes);
    }

    public Cell(int columnIndex, String columnText, SearchType... searchTypes) {
        this();
        setTemplateValue("tagAndPosition", columnIndex + "");
        setText(columnText, searchTypes);
    }

    public Cell(String header, String cellText, SearchType... searchTypes) {
        this();
        setTag(getPathBuilder().getTag() + "[count(//th[text()='" + header + "']/preceding-sibling::*) + number(boolean(//th[text()='" + header + "']/preceding-sibling::*))]");
        setText(cellText, searchTypes);
    }

    public Cell(WebLocator container, int columnIndex, String columnText, SearchType... searchTypes) {
        this(container, columnIndex);
        setText(columnText, searchTypes);
    }
}
