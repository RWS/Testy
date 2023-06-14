package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

import java.time.Duration;

public class Cell extends AbstractCell {

    public Cell() {
        setRender(Duration.ofMillis(200));
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
        setTemplateValue("tagAndPosition", String.valueOf(columnIndex));
    }

    public Cell(String columnText, SearchType... searchTypes) {
        this();
        setText(columnText, searchTypes);
    }

    public Cell(int columnIndex, String columnText, SearchType... searchTypes) {
        this();
        setTemplateValue("tagAndPosition", String.valueOf(columnIndex));
        setText(columnText, searchTypes);
    }

    public Cell(int columnIndex, WebLocator... iconEl) {
        this();
        setTemplateValue("tagAndPosition", String.valueOf(columnIndex));
        setChildNodes(iconEl);
    }

    public Cell(int columnIndex, SearchType searchType, WebLocator... iconEl) {
        this();
        setTemplateValue("tagAndPosition", String.valueOf(columnIndex));
        setChildNodes(searchType, iconEl);
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
