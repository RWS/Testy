package com.sdl.selenium.web;

public enum SearchType {
    EQUALS(false, 0),
    CONTAINS(false, 1),
    STARTS_WITH(false, 2),

    TRIM_EQUALS(true, 0),
    TRIM_CONTAINS(true, 1),
    TRIM_STARTS_WITH(true, 2);

    private final boolean trim;
    private final int type;

    SearchType(boolean trim, int type) {
        this.trim = trim;
        this.type = type;
    }

    boolean isTrim(){
        return trim;
    }

    boolean isSameType(SearchType searchType){
        return this.type == searchType.type;
    }

}
