package com.sdl.selenium.web.table;

import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.SearchType;

public interface ITable <R extends Row, C extends Cell> extends IWebLocator {

    /**
     * selects (clicks) on a table which contains a certain element
     *
     * @param searchText the searchText of the table element on which the search is done
     *                  default SearchType is {@link com.sdl.selenium.web.SearchType#EQUALS}
     * @return true if selected
     */
    boolean rowSelect(String searchText);

    /**
     * @param searchText searchText
     * @param searchType searchType
     * @return true or false
     */
    public boolean rowSelect(String searchText, SearchType... searchType);

    /**
     * @return row count. -1 if not table not ready to be used or not found
     */
    int getCount();

    /**
     * TODO rename in getRow
     * @param rowIndex rowIndex
     * @return R
     */
    R getRowLocator(int rowIndex);

    R getRow(C... byCells);

    C getCell(int rowIndex, int columnIndex);

    C getCell(String searchElement);

    C getCell(String searchElement, SearchType... searchType);

    C getCell(int columnIndex, C... byRowCells);

    C getCell(int columnIndex, String text, C... byRowCells);
}
