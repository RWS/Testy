package com.sdl.selenium.web.table;

import com.sdl.selenium.web.ILocator;
import com.sdl.selenium.web.SearchType;

import java.time.Duration;

public interface ITable<R extends AbstractRow, C extends AbstractCell> extends ILocator {

    /**
     * @return row count. -1 if not table not ready to be used or not found
     */
    int getCount();

    R getRow(int rowIndex);

    R getRow(C... byCells);

    C getCell(int rowIndex, int columnIndex);

    C getCell(String searchElement);

    C getCell(String searchElement, SearchType... searchTypes);

    C getCell(int columnIndex, C... byRowCells);

    C getCell(int columnIndex, String text, C... byRowCells);

    boolean ready(boolean waitRows);

    boolean ready(boolean waitRows, Duration duration);
}
