package com.extjs.selenium.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class GridCell extends com.sdl.selenium.extjs3.grid.GridCell {

    public GridCell() {
        super();
    }

    public GridCell(WebLocator container) {
        super(container);
    }

    public GridCell(WebLocator container, String elPath) {
        super(container, elPath);
    }

    public GridCell(WebLocator container, int columnIndex) {
        super(container, columnIndex);
    }

    public GridCell(String text, WebLocator container) {
        super(text, container);
    }

    public GridCell(String text, SearchType searchType) {
        super(text, searchType);
    }

    public GridCell(WebLocator container, String text, SearchType searchType) {
        super(container, text, searchType);
    }

    public GridCell(int columnIndex, String columnText, SearchType searchType) {
        super(columnIndex, columnText, searchType);
    }

    public GridCell(WebLocator container, int columnIndex, String columnText, SearchType searchType) {
        super(container, columnIndex, columnText, searchType);
    }
}
