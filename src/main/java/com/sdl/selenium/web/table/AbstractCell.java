package com.sdl.selenium.web.table;

import com.sdl.selenium.web.WebLocator;

public abstract class AbstractCell extends WebLocator implements ICell {

    //public abstract boolean ready(boolean waitRows);

    @Override
    public String toString() {
        return super.getXPath();
    }
}
