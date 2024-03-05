package com.sdl.selenium.extjs6.grid;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class Author {
    private String author;
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

    public Author() {
    }

    @Override
    public String toString() {
        return "Author{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", productGroup='" + productGroup + '\'' +
                '}';
    }
}
