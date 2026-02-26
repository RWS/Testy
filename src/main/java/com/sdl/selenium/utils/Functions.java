package com.sdl.selenium.utils;

import com.sdl.selenium.extjs6.grid.Cell;
import com.sdl.selenium.web.WebLocator;

import java.util.List;
import java.util.function.Function;

public class Functions {

    public static Function<Cell, String> getBooleanValue() {
        return f -> {
            WebLocator check = new WebLocator(f).setClasses("x-grid-checkcolumn");
            boolean checked = check.getAttributeClass().contains("x-grid-checkcolumn-checked");
            return checked ? "true" : "false";
        };
    }

    public static <T> Function<List<T>, Boolean> auditor(List<T> values) {
        return array -> array.containsAll(values);
    }
}

