package com.sdl.selenium.extjs6.grid;

public class Author {
    private String author;
    private String title;
    private String manufacturer;
    private String productGroup;

    public Author(String author, String title, String manufacturer, String productGroup) {
        this.author = author;
        this.title = title;
        this.manufacturer = manufacturer;
        this.productGroup = productGroup;
    }

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
}
