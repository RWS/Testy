package com.sdl.selenium.web.table;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Row extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Row.class);

    public Row() {
        setClassName("Row");
    }
}
