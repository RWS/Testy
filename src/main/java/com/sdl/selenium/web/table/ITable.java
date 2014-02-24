package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;

public interface ITable {

    /**
     * selects (clicks) on a table which contains a certain element
     *
     * @param searchText the searchText of the table element on which the search is done
     *                  default SearchType is {@link com.sdl.selenium.web.SearchType#EQUALS}
     * @return true if selected
     */
    boolean rowSelect(String searchText);

    /**
     * @param searchText
     * @param searchType
     * @return
     */
    public boolean rowSelect(String searchText, SearchType searchType);

    /**
     * @return row count. -1 if not table not ready to be used or not found
     */
    int getCount();

    /**
     * TODO rename in getRow
     * @param rowIndex
     * @return
     */
    Row getRowLocator(int rowIndex);

    Row getRow(Cell... byCells);

    Cell getCell(int rowIndex, int columnIndex);

}
