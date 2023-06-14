package com.sdl.selenium.web.table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface IColumns {

    int getHeadersCount();

    default List<Integer> getColumns(int columns, int[] excludedColumns) {
        List<Integer> excluded = Arrays.stream(excludedColumns).boxed().collect(Collectors.toList());

        return IntStream.rangeClosed(1, columns)
                .filter(column -> !excluded.contains(column))
                .boxed()
                .collect(Collectors.toList());
    }

    default List<Integer> getColumns(int[] excludedColumns) {
        int columns = getHeadersCount();
        return getColumns(columns, excludedColumns);
    }
}