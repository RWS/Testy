package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridCell extends Cell {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridCell.class);

    public GridCell(By... bys) {
        getPathBuilder().init(bys);
        setRenderMillis(200);
    }

    /**
     * @deprecated use other constructors
     */
    public GridCell(WebLocator container) {
        this(By.container(container));
    }

    /**
     * @deprecated use other constructors
     */
    public GridCell(WebLocator container, String elPath) {
        this(By.container(container), By.xpath(elPath));
    }

    public GridCell(int columnIndex) {
        this(By.xpath("//td[" + columnIndex + "]//*[contains(@class, 'x-grid3-cell-inner')]"),
                By.infoMessage("td[" + columnIndex + "]//x-grid3-cell-inner"));
    }

    /**
     * @deprecated use other constructors
     */
    public GridCell(WebLocator container, int columnIndex) {
        this(columnIndex);
        getPathBuilder().setContainer(container);
    }

    public GridCell(String text, WebLocator container) {
        this(By.container(container), By.text(text), By.classes("x-grid3-cell-inner"));
    }

    public GridCell(String text, SearchType searchType) {
        this(By.text(text, searchType), By.classes("x-grid3-cell-inner"));
    }

    public GridCell(WebLocator container, String text, SearchType searchType) {
        this(By.container(container), By.text(text, searchType), By.classes("x-grid3-cell-inner"));
    }

    public GridCell(int columnIndex, String columnText, SearchType searchType) {
        this();
        getPathBuilder().setTag("td[" + columnIndex + "]//*");
        setText(columnText);
        setSearchTextType(searchType);
    }

    /**
     * @deprecated use other constructors
     */
    public GridCell(WebLocator container, int columnIndex, String columnText, SearchType searchType) {
        this(columnIndex, columnText, searchType);
        getPathBuilder().setContainer(container);
    }

    public boolean select() {
        try {
            return click();
        } catch (Exception e) {
            LOGGER.error("GridCell select ", e);
            return false;
        }
    }
}
