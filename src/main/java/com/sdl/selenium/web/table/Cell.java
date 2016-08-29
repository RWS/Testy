package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cell extends AbstractCell {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cell.class);

    public Cell() {
        withRenderMillis(200);
        withClassName("Cell");
        withTag("td");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Cell(WebLocator container) {
        this();
        withContainer(container);
    }

    public Cell(WebLocator container, int columnIndex) {
        this(container);
        withPosition(columnIndex);
    }

    public Cell(int columnIndex, String columnText, SearchType... searchTypes) {
        this();
        withPosition(columnIndex);
        withText(columnText, searchTypes);
    }

    public Cell(WebLocator container, int columnIndex, String columnText, SearchType... searchTypes) {
        this(container, columnIndex);
        withText(columnText, searchTypes);
    }

    public Cell(int columnIndex, String columnText, boolean isInternationalized , SearchType... searchTypes) {
        this();
        withPosition(columnIndex);
        withText(columnText, isInternationalized, searchTypes);
    }

    public Cell(WebLocator container, int columnIndex, String columnText, boolean isInternationalized, SearchType... searchTypes) {
        this(container, columnIndex);
        withText(columnText, isInternationalized, searchTypes);
    }

    protected XPathBuilder createXPathBuilder() {
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
