package com.sdl.selenium.web;

import java.util.*;

public class SearchText {

    private String text;
    private List<SearchType> searchTypes = new ArrayList<>();

    public SearchText(String text, List<SearchType> searchTypes) {
        this.text = text;
        this.searchTypes = searchTypes;
    }

    public SearchText(String text, SearchType... searchTypes) {
        this.text = text;
        setSearchTypes(searchTypes);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
