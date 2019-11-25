package com.sdl.selenium.extjs6.grid;

public class Author {
    private String author;
    private String title;
    private String manufacturer;
    private String productGroup;
//    private String test;

    public Author(String author, String title, String manufacturer, String productGroup) {
        this.author = author;
        this.title = title;
        this.manufacturer = manufacturer;
        this.productGroup = productGroup;
    }

//    public Author(String author, String title, String manufacturer, String productGroup, String test) {
//        this.author = author;
//        this.title = title;
//        this.manufacturer = manufacturer;
//        this.productGroup = productGroup;
//        this.test = test;
//    }

    public Author() {
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getProductGroup() {
        return this.productGroup;
    }

//    public String getTest() {
//        return test;
//    }
//
//    public void setTest(String test) {
//        this.test = test;
//    }
}
