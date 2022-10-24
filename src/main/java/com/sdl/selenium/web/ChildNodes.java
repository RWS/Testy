package com.sdl.selenium.web;

import java.util.ArrayList;
import java.util.List;

public class ChildNodes {
    private SearchType searchType;
    private List<? extends WebLocator> childNodes = new ArrayList<>();

    public ChildNodes() {
    }

    public ChildNodes(SearchType searchType, List<? extends WebLocator> childNodes) {
        this.searchType = searchType;
        this.childNodes = childNodes;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public List<? extends WebLocator> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<? extends WebLocator> childNodes) {
        this.childNodes = childNodes;
    }
}
