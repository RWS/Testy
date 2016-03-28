package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.*;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Grid extends Table {
    private static final Logger LOGGER = LoggerFactory.getLogger(Grid.class);

    public Grid() {
        withClassName("Grid");
        withBaseCls("x-grid");
        withTag("*");
    }

//    public static void main(String[] args) {
//        Tab tab = new Tab("API Keys");
//        Grid grid = new Grid(tab);
//        LOGGER.debug(grid.getRow(4, new TableCell(2, "Api key", SearchType.CONTAINS)).getXPath());
//    }

    public Grid(WebLocator container) {
        this();
        withContainer(container);
    }

    /**
     * @deprecated use {@link #getRow(int, AbstractCell...)}
     * @param indexRow eg. 1
     * @param byCells TableCell
     * @return TableRow
     */
    public TableRow getRow(int indexRow, TableCell... byCells) {
        return new TableRow(this, indexRow, byCells).withTag("table").withInfoMessage("-Row");
    }

    public Row getRow(int indexRow, AbstractCell... byCells) {
        return new Row(this, indexRow, byCells).withTag("table").withInfoMessage("-Row");
    }

    public boolean waitToActivate(int seconds) {
        String info = toString();
        int count = 0;
        boolean hasMask;
        while ((hasMask = hasMask()) && (count < seconds)) {
            count++;
            LOGGER.info("waitToActivate:" + (seconds - count) + " seconds; " + info);
            Utils.sleep(900);
        }
        return !hasMask;
    }

    private boolean hasMask(){
        WebLocator mask = new WebLocator(this).withClasses("x-mask").withElxPathSuffix("style", "not(contains(@style, 'display: none'))");
        return mask.waitToRender(500);
    }
}
