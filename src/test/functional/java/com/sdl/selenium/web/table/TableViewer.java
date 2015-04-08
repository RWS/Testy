package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableViewer extends Table {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableViewer.class);

    public TableViewer() {
        setTag("div");
        setTemplate("table", "@tabindex='1' and contains(@style, '%s')");
        setTemplateValue("table", "width: 988px; height: 407px;");
    }

    public TableViewer(WebLocator container) {
        this();
        setContainer(container);
    }

    public TableViewerRow getRow(TableViewerCell... byCells) {
        return new TableViewerRow(this, byCells).setInfoMessage("-TableRow");
    }

    public TableViewerCell getTableCell(int columnIndex, TableViewerCell... byCells) {
        return new TableViewerCell(getRow(byCells), columnIndex);
    }

    public static void main(String[] args) {
        TableViewer tableViewer = new TableViewer();
        LOGGER.debug("path {}", tableViewer.getPath());

        TableViewerRow tableViewerRow = new TableViewerRow();
        LOGGER.debug("path {}", tableViewerRow.getPath());

        TableViewerCell tableViewerCell = new TableViewerCell(1, "Carbon", SearchType.EQUALS);
        LOGGER.debug("path {}", tableViewerCell.getPath());

        TableViewerRow tableViewerRow1 = tableViewer.getRow(tableViewerCell);
        LOGGER.debug("path {}", tableViewerRow1.getPath());
    }
}
