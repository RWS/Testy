package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
/**
 * @deprecated use {@link Cell}
 */
public class TableCell extends Cell {

    /**
     * @deprecated use {@link Cell#Cell()}
     */
    public TableCell() {
        withRenderMillis(200);
        withClassName("TableCell");
        withTag("td");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    /**
     * @deprecated use {@link Cell#Cell(WebLocator)}
     * @param container WebLocator
     */
    public TableCell(WebLocator container) {
        this();
        withContainer(container);
    }

    /**
     * @deprecated use {@link Cell#Cell(WebLocator, int)}
     * @param container WebLocator
     * @param columnIndex int
     */
    public TableCell(WebLocator container, int columnIndex) {
        this(container);
        withPosition(columnIndex);
    }

    /**
     * @deprecated use {@link Cell#Cell(int, String, SearchType[])}
     * @param columnIndex int
     * @param columnText String
     * @param searchTypes SearchType
     */
    public TableCell(int columnIndex, String columnText, SearchType... searchTypes) {
        this();
        withPosition(columnIndex);
        withText(columnText, searchTypes);
    }

    /**
     * @deprecated use {@link Cell#Cell(WebLocator, int, String, SearchType[])}
     * @param container WebLocator
     * @param columnIndex int
     * @param columnText String
     * @param searchTypes SearchType
     */
    public TableCell(WebLocator container, int columnIndex, String columnText, SearchType... searchTypes) {
        this(container, columnIndex);
        withText(columnText, searchTypes);
    }
}
