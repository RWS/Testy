package com.sdl.selenium.web.table;

import com.sdl.selenium.web.IText;
import com.sdl.selenium.web.IWebLocator;

import java.util.ArrayList;
import java.util.List;

public interface IRow extends IWebLocator, IText {

    default List<Integer> getColumns(int columns, int[] excludedColumns) {
        List<Integer> excluded = new ArrayList<>();
        for (int excludedColumn : excludedColumns) {
            excluded.add(excludedColumn);
        }

        List<Integer> columnsList = new ArrayList<>();
        for (int i = 1; i <= columns; i++) {
            if (!excluded.contains(i)) {
                columnsList.add(i);
            }
        }
        return columnsList;
    }
}