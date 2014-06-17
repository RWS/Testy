package com.sdl.selenium.web;

import java.util.Arrays;

public abstract class By<T> {

    private T value;
    private SearchType[] searchType;

//    private By() {
//    }

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
    public abstract void init(PathBuilder pathBuilder, WebLocator builder);

    // =========================

    public static By cls(final String cls) {
        if (cls == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the cls expression is null.");

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
                    "Cannot find elements when the classes expression is null.");

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

    public static By excludeClasses(final String... excludeClasses) {
        if (excludeClasses == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the excludeClasses expression is null.");

        return new ByExcludeClasses(excludeClasses);
    }

    public static class ByExcludeClasses extends By<String[]> {

        public ByExcludeClasses(String... excludeClasses) {
            if (excludeClasses != null) {
                setValue(excludeClasses);
            }
        }

        public String getPath() {
            return Arrays.toString(getValue());
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setExcludeClasses(getValue());
            webLocator.setExcludeClasses(getValue());
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

    public static By name(final String name) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the name expression is null.");

        return new ByName(name);
    }

    public static class ByName extends By<String> {

        public ByName(String id) {
            setValue(id);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setName(getValue());
            webLocator.setName(getValue());
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

    public static By style(final String style) {
        if (style == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the style expression is null.");

        return new ByStyle(style);
    }

    public static class ByStyle extends By<String> {

        public ByStyle(final String style) {
            setValue(style);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setStyle(getValue());
            webLocator.setStyle(getValue());
        }
    }

    public static By title(final String title) {
        if (title == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the title expression is null.");

        return new ByTitle(title);
    }

    public static class ByTitle extends By<String> {

        public ByTitle(final String style) {
            setValue(style);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setTitle(getValue());
            webLocator.setTitle(getValue());
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

    public static By labelTag(final String labelTag) {
        if (labelTag == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the labelTag expression is null.");

        return new ByLabelTag(labelTag);
    }

    public static class ByLabelTag extends By<String> {

        public ByLabelTag(String labelTag) {
            setValue(labelTag);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setLabelTag(getValue());
            webLocator.setLabelTag(getValue());
        }
    }

    public static By label(final String label, final SearchType... searchType) {
        if (label == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the label expression is null.");

        return new ByLabel(label, searchType);
    }

    public static class ByLabel extends By<String> {

        public ByLabel(String label, SearchType ...searchTypes) {
            setValue(label);
            setSearchType(searchTypes);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setLabel(getValue(), getSearchType());
            webLocator.setLabel(getValue(), getSearchType());
        }
    }

    public static By labelPosition(final String labelPosition) {
        if (labelPosition == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the labelPosition expression is null.");

        return new ByLabelPosition(labelPosition);
    }

    public static class ByLabelPosition extends By<String> {

        public ByLabelPosition(String labelPosition) {
            setValue(labelPosition);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setLabelPosition(getValue());
            webLocator.setLabelPosition(getValue());
        }
    }

    public static By position(final Integer position) {
        if (position == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the position expression is null.");

        return new ByPosition(position);
    }

    public static class ByPosition extends By<Integer> {

        public ByPosition(Integer position) {
            setValue(position);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setPosition(getValue());
            webLocator.setPosition(getValue());
        }
    }

    public static By pathSuffix(final String pathSuffix) {
        if (pathSuffix == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the pathSuffix expression is null.");

        return new ByPathSuffix(pathSuffix);
    }

    public static class ByPathSuffix extends By<String> {

        public ByPathSuffix(String xpath) {
            setValue(xpath);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setElPathSuffix(getValue());
            webLocator.setElPathSuffix(getValue());
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

    public static By infoMessage(final String infoMessage) {
        if (infoMessage == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the infoMessage expression is null.");

        return new ByInfoMessage(infoMessage);
    }

    public static class ByInfoMessage extends By<String> {

        public ByInfoMessage(String infoMessage) {
            setValue(infoMessage);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setInfoMessage(getValue());
            webLocator.setInfoMessage(getValue());
        }
    }

    public static By visibility(final Boolean visibility) {
        if (visibility == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the visibility expression is null.");

        return new ByVisibility(visibility);
    }

    public static class ByVisibility extends By<Boolean> {

        public ByVisibility(Boolean visibility) {
            setValue(visibility);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            builder.setVisibility(getValue());
            webLocator.setVisibility(getValue());
        }
    }
}
