package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
/**
 * @deprecated use {@link Row}
 */
public class TableRow extends Row {

    /**
     * @deprecated use {@link Row#Row()}
     */
    public TableRow() {
        setRenderMillis(200);
        setClassName("TableRow");
        setTag("tr");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    /**
     * @deprecated use {@link Row#Row(WebLocator)}
     * @param container WebLocator
     */
    public TableRow(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     * @deprecated use {@link Row#Row(WebLocator, int)}
     * @param container WebLocator
     * @param indexRow int
     */
    public TableRow(WebLocator container, int indexRow) {
        this(container);
        setPosition(indexRow);
    }

    /**
     * @deprecated use {@link Row#Row(WebLocator, String, SearchType[])}
     * @param table WebLocator
     * @param searchElement int
     * @param searchTypes SearchType
     */
    public TableRow(WebLocator table, String searchElement, SearchType... searchTypes) {
        this(table);
        setText(searchElement, searchTypes);
    }

    /**
     * @deprecated use {@link Row#Row(WebLocator, AbstractCell[])}
     * @param table WebLocator
     * @param cells AbstractCell
     */
    public TableRow(WebLocator table, AbstractCell... cells) {
        this(table);
        setChildNodes(cells);
    }

    /**
     * @deprecated use {@link Row#Row(WebLocator, int, AbstractCell[])}
     * @param table WebLocator
     * @param indexRow int
     * @param cells AbstractCell
     */
    public TableRow(WebLocator table, int indexRow, AbstractCell... cells) {
        this(table, indexRow);
        setChildNodes(cells);
    }
}
