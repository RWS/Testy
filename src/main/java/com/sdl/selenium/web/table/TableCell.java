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
        setRenderMillis(200);
        setClassName("TableCell");
        setTag("td");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    /**
     * @deprecated use {@link Cell#Cell(WebLocator)}
     */
    public TableCell(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     * @deprecated use {@link Cell#Cell(WebLocator, int)}
     */
    public TableCell(WebLocator container, int columnIndex) {
        this(container);
        setPosition(columnIndex);
    }

    /**
     * @deprecated use {@link Cell#Cell(int, String, SearchType[])}
     */
    public TableCell(int columnIndex, String columnText, SearchType... searchTypes) {
        this();
        setPosition(columnIndex);
        setText(columnText, searchTypes);
    }

    /**
     * @deprecated use {@link Cell#Cell(WebLocator, int, String, SearchType[])}
     */
    public TableCell(WebLocator container, int columnIndex, String columnText, SearchType... searchTypes) {
        this(container, columnIndex);
        setText(columnText, searchTypes);
    }
}
