package com.extjs.selenium.grid;

public class TestData {

    public String searchText;
    public int columnIndex;
    public String searchType;


    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public TestData(String searchText, int columnIndex, String searchType){
        setSearchText(searchText);
        setColumnIndex(columnIndex);
        setSearchType(searchType);
    }

}
