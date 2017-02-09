package com.sdl.selenium.web.table;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRow extends WebLocator implements IRow {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRow.class);

    public AbstractRow() {
        setClassName("AbstractRow");
    }
}
