package com.extjs.selenium.grid;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class GridRow extends WebLocator {
    private static final Logger logger = Logger.getLogger(GridRow.class);

    private static final String GRID_ROW_PATH = "[contains(@class, 'x-grid3-row') and (not (contains(@class,'x-grid3-row-checker')))]";
    private static final String GRID_ROW_SELECTED_PATH = "[contains(@class,'x-grid3-row-selected') and (not (contains(@class,'x-grid3-row-checker')))]";

    public GridRow() {
        setRenderMillis(200);
        setClassName("GridRow");
    }

    public GridRow(WebLocator container, String elPath) {
        this();
        setContainer(container);
        setElPath(elPath);
    }

    public GridRow(GridPanel gridPanel) {
        this();
        setContainer(gridPanel);
        setElPath("//div" + GRID_ROW_PATH);
    }

    public GridRow(GridPanel gridPanel, int rowIndex) {
        this(gridPanel);
        setElPath("//div[" + rowIndex + "]" + GRID_ROW_PATH);
    }

    public GridRow(GridPanel gridPanel, int searchColumnIndex, String searchElement, String searchType) {
        this(gridPanel, new GridCell(searchColumnIndex, searchElement, searchType));
//        String textCondition = getTextCondition(searchElement, searchType);
//        setElPath("//*[" + getSearchPath(searchColumnId, textCondition) + "]");
    }

    public GridRow(GridPanel gridPanel, String searchColumnId, String searchElement, String searchType) {
        this(gridPanel);
        GridCell cell = new GridCell(null, searchElement, searchType);
//        this(gridPanel, new GridCell(searchColumnId, searchElement, searchType));
//        String textCondition = getTextCondition(searchElement, searchType);
        setElPath("//*[" + getSearchPath(searchColumnId, Utils.fixPathSelector(cell.getItemPathText())) + "]");
    }

    public GridRow(GridPanel gridPanel, GridCell ...cells) {
        this(gridPanel) ;
        String path = "";
        for(GridCell cell : cells){
            path += " and " + getSearchPath(cell.getPosition(), Utils.fixPathSelector(cell.getItemPathText()));
        }
        setElPath("//*[" + Utils.fixPathSelector(path) + "]");
    }

    private String getSearchPath(String searchColumnId, String textCondition) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//*[contains(@class, 'x-grid3-td-" + searchColumnId + "')]//*[" + textCondition + "]) > 0";
    }

    private String getSearchPath(int columnIndex, String textCondition) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//td[" +columnIndex +"]//*[" + textCondition + "]) > 0";
    }

    private String getSearchPath(int columnIndex, GridCell cell) {
        return "count(*[contains(@class, 'x-grid3-row-table')]//td[" +columnIndex +"]" + cell.getPath() + ") > 0";
    }

    public GridRow(GridPanel gridPanel, int rowIndex, boolean isSelected) {
        this(gridPanel, rowIndex);
        if (isSelected) {
            setElPath("//div[" + rowIndex + "]" + GRID_ROW_SELECTED_PATH);
        }
    }
}
