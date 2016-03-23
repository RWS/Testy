package com.sdl.selenium.web;

import java.util.*;

public class SearchText {

    private String value;
    private List<SearchType> searchTypes = new ArrayList<>();

    public SearchText(String value, List<SearchType> searchTypes) {
        this.value = value;
        this.searchTypes = searchTypes;
    }

    public SearchText(String value, SearchType... searchTypes) {
        this.value = value;
        setSearchTypes(searchTypes);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<SearchType> getSearchTypes() {
        return searchTypes;
    }

    public void setSearchTypes(SearchType... searchTypes) {
        if (searchTypes != null) {
            if (searchTypes.length == 0) {
                Collections.addAll(this.searchTypes, SearchType.EQUALS);
            } else {
                Collections.addAll(this.searchTypes, searchTypes);
            }
        } else {
            this.searchTypes.addAll(new XPathBuilder().defaultSearchTextType);
        }
        new XPathBuilder().cleanUpSearchType(this.searchTypes);
    }
}
