package com.sdl.selenium.web;

import java.util.*;

public class SearchText {

    private String text;
    private List<SearchType> searchType = new ArrayList<>();

    public SearchText(String text, List<SearchType> searchType) {
        this.text = text;
        this.searchType = searchType;
    }

    public SearchText(String text, SearchType... searchType) {
        this.text = text;
        setSearchType(searchType);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SearchType> getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType... searchType) {
        if (searchType != null) {
            if (searchType.length == 0) {
                Collections.addAll(this.searchType, SearchType.EQUALS);
            } else {
                Collections.addAll(this.searchType, searchType);
            }
        } else {
            this.searchType.addAll(new XPathBuilder().defaultSearchTextType);
        }
        new XPathBuilder().cleanUpSearchType(this.searchType);
    }
}
