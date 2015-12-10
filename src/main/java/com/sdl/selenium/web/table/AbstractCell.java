package com.sdl.selenium.web.table;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCell extends WebLocator implements ICell {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCell.class);

    //public abstract boolean ready(boolean waitRows);
}
