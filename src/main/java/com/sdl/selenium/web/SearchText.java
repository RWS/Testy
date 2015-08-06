package com.sdl.selenium.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SearchText {

    private String text;
    private Set<SearchType> searchType = new HashSet<>();

    public SearchText(String text, Set<SearchType> searchType) {
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

    public Set<SearchType> getSearchType() {
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
    }
}
