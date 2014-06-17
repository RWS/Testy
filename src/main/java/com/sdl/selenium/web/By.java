package com.sdl.selenium.web;

import java.util.Arrays;

public abstract class By<T> {

    private T value;
    private SearchType[] searchType;

    private By() {
    }

    // abstract
    abstract public String getPath();

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public SearchType[] getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType... searchType) {
        this.searchType = searchType;
    }

    //abstract void init(PathBuilder builder);
    abstract void init(PathBuilder pathBuilder, WebLocator builder);

    // =========================

    public static By cls(final String cls) {
        if (cls == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the class name expression is null.");

        return new ByCls(cls);
    }

    public static class ByCls extends By<String> {

        public ByCls(String cls) {
            setValue(cls);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setCls(getValue());
            webLocator.setCls(getValue());
        }
    }

    public static By classes(final String... classes) {
        if (classes == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the class name expression is null.");

        return new ByClasses(classes);
    }

    public static class ByClasses extends By<String[]> {

        public ByClasses(String... classes) {
            if (classes != null) {
                setValue(classes);
            }
        }

        public String getPath() {
            return Arrays.toString(getValue());
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setClasses(getValue());
            webLocator.setClasses(getValue());
        }
    }

    public static By id(final String id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the id expression is null.");

        return new ById(id);
    }

    public static class ById extends By<String> {

        public ById(String id) {
            setValue(id);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setId(getValue());
            webLocator.setId(getValue());
        }
    }

    public static By xpath(final String xpath) {
        if (xpath == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the xpath expression is null.");

        return new ByXpath(xpath);
    }

    public static class ByXpath extends By<String> {

        public ByXpath(String xpath) {
            setValue(xpath);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setElPath(getValue());
            webLocator.setElPath(getValue());
        }
    }

    public static By text(final String text, final SearchType... searchType) {
        if (text == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the text expression is null.");

        return new ByText(text, searchType);
    }

    public static class ByText extends By<String> {

        public ByText(final String text, final SearchType... searchType) {
            setValue(text);
            setSearchType(searchType);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setText(getValue(), getSearchType());
            webLocator.setText(getValue(), getSearchType());
        }
    }

    public static By container(final WebLocator container) {
        if (container == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the container expression is null.");

        return new ByContainer(container);
    }

    public static class ByContainer extends By<WebLocator> {

        public ByContainer(WebLocator webLocator) {
            setValue(webLocator);
        }

        public String getPath() {
            return getValue().getPath();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setContainer(getValue());
            webLocator.setContainer(getValue());
        }
    }

    public static By tag(final String tag) {
        if (tag == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the tag expression is null.");

        return new ByTag(tag);
    }

    public static class ByTag extends By<String> {

        public ByTag(String tag) {
            setValue(tag);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setTag(getValue());
            webLocator.setTag(getValue());
        }
    }
}
