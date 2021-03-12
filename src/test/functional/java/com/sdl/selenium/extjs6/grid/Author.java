package com.sdl.selenium.extjs6.grid;

public class Author {
    private String author;
    private BString bTitle;
    private String title;
    private String manufacturer;
    private String productGroup;

    public Author(String title, String manufacturer, String productGroup) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.productGroup = productGroup;
    }

    public Author(String author, String title, String manufacturer, String productGroup) {
        this.author = author;
        this.title = title;
        this.manufacturer = manufacturer;
        this.productGroup = productGroup;
    }

    public Author(String author, BString title, String manufacturer, String productGroup) {
        this.author = author;
        this.bTitle = title;
        this.manufacturer = manufacturer;
        this.productGroup = productGroup;
    }

    public Author() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBTitle() {
        return bTitle.getTitle();
    }

    public void setBTitle(String bTitle) {
        this.bTitle = new BString(bTitle);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }
}
