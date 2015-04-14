package com.sdl.selenium.web;

import java.util.Arrays;

public abstract class By<T> {

    private T value;
    private T key;
    private SearchType[] searchType;

    // abstract TODO remove it
    abstract public String getPath();

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public SearchType[] getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType... searchType) {
        this.searchType = searchType;
    }

    public abstract void init(XPathBuilder builder);

    // =========================

    public static By baseCls(final String baseCls) {
        return new ByBaseCls(baseCls);
    }

    private static class ByBaseCls extends By<String> {

        public ByBaseCls(String baseCls) {
            setValue(baseCls);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setBaseCls(getValue());
        }
    }

    public static By cls(final String cls) {
        return new ByCls(cls);
    }

    private static class ByCls extends By<String> {

        public ByCls(String cls) {
            setValue(cls);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setCls(getValue());
        }
    }

    public static By classes(final String... classes) {
        return new ByClasses(classes);
    }

    private static class ByClasses extends By<String[]> {

        public ByClasses(String... classes) {
            if (classes != null) {
                setValue(classes);
            }
        }

        public String getPath() {
            return Arrays.toString(getValue());
        }

        public void init(XPathBuilder builder) {
            builder.setClasses(getValue());
        }
    }

    public static By excludeClasses(final String... excludeClasses) {
        return new ByExcludeClasses(excludeClasses);
    }

    private static class ByExcludeClasses extends By<String[]> {

        public ByExcludeClasses(String... excludeClasses) {
            if (excludeClasses != null) {
                setValue(excludeClasses);
            }
        }

        public String getPath() {
            return Arrays.toString(getValue());
        }

        public void init(XPathBuilder builder) {
            builder.setExcludeClasses(getValue());
        }
    }

    public static By id(final String id) {
        return new ById(id);
    }

    private static class ById extends By<String> {

        public ById(String id) {
            setValue(id);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setId(getValue());
        }
    }

    public static By name(final String name) {
        return new ByName(name);
    }

    private static class ByName extends By<String> {

        public ByName(String name) {
            setValue(name);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setName(getValue());
        }
    }

    public static By text(final String text, final SearchType... searchType) {
        return new ByText(text, searchType);
    }

    private static class ByText extends By<String> {

        public ByText(final String text, final SearchType... searchType) {
            setValue(text);
            setSearchType(searchType);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setText(getValue(), getSearchType());
        }
    }

    public static By style(final String style) {
        return new ByStyle(style);
    }

    private static class ByStyle extends By<String> {

        public ByStyle(final String style) {
            setValue(style);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setStyle(getValue());
        }
    }

    public static By title(final String title) {
        return new ByTitle(title);
    }

    private static class ByTitle extends By<String> {

        public ByTitle(final String style) {
            setValue(style);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setTitle(getValue());
        }
    }

    public static By tag(final String tag) {
        return new ByTag(tag);
    }

    private static class ByTag extends By<String> {

        public ByTag(String tag) {
            setValue(tag);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setTag(getValue());
        }
    }

    public static By labelTag(final String labelTag) {
        return new ByLabelTag(labelTag);
    }

    private static class ByLabelTag extends By<String> {

        public ByLabelTag(String labelTag) {
            setValue(labelTag);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setLabelTag(getValue());
        }
    }

    public static By label(final String label, final SearchType... searchType) {
        return new ByLabel(label, searchType);
    }

    private static class ByLabel extends By<String> {

        public ByLabel(String label, SearchType... searchTypes) {
            setValue(label);
            setSearchType(searchTypes);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setLabel(getValue(), getSearchType());
        }
    }

    public static By labelPosition(final String labelPosition) {
        return new ByLabelPosition(labelPosition);
    }

    private static class ByLabelPosition extends By<String> {

        public ByLabelPosition(String labelPosition) {
            setValue(labelPosition);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setLabelPosition(getValue());
        }
    }

    public static By position(final Integer position) {
        return new ByPosition(position);
    }

    private static class ByPosition extends By<Integer> {

        public ByPosition(Integer position) {
            setValue(position);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(XPathBuilder builder) {
            builder.setPosition(getValue());
        }
    }

    public static By pathSuffix(final String key, final String pathSuffix) {
        return new ByPathSuffix(key, pathSuffix);
    }

    private static class ByPathSuffix extends By<String> {

        public ByPathSuffix(String key, String xpath) {
            setKey(key);
            setValue(xpath);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setElPathSuffix(getKey(), getValue());
        }
    }

    public static By template(final String key, final String template) {
        return new ByTemplate(key, template);
    }

    private static class ByTemplate extends By<String> {

        public ByTemplate(String key, String template) {
            setKey(key);
            setValue(template);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setTemplate(getKey(), getValue());
        }
    }

    public static By xpath(final String xpath) {
        return new ByXpath(xpath);
    }

    private static class ByXpath extends By<String> {

        public ByXpath(String xpath) {
            setValue(xpath);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setElPath(getValue());
        }
    }

    public static By container(final WebLocator container) {
        return new ByContainer(container);
    }

    private static class ByContainer extends By<WebLocator> {

        public ByContainer(WebLocator webLocator) {
            setValue(webLocator);
        }

        public String getPath() {
            return getValue().getPath();
        }

        public void init(XPathBuilder builder) {
            builder.setContainer(getValue());
        }

    }

    public static By childNodes(final WebLocator... childNodes) {
        return new ByChildNodes(childNodes);
    }

    private static class ByChildNodes extends By<WebLocator[]> {

        public ByChildNodes(WebLocator... childNodes) {
            setValue(childNodes);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(XPathBuilder builder) {
            builder.setChildNotes(getValue());
        }
    }

    public static By infoMessage(final String infoMessage) {
        return new ByInfoMessage(infoMessage);
    }

    private static class ByInfoMessage extends By<String> {

        public ByInfoMessage(String infoMessage) {
            setValue(infoMessage);
        }

        public String getPath() {
            return getValue();
        }

        public void init(XPathBuilder builder) {
            builder.setInfoMessage(getValue());
        }
    }

    public static By visibility(final Boolean visibility) {
        return new ByVisibility(visibility);
    }

    private static class ByVisibility extends By<Boolean> {

        public ByVisibility(Boolean visibility) {
            setValue(visibility);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(XPathBuilder builder) {
            builder.setVisibility(getValue());
        }
    }

    public static By searchType(final SearchType... searchType) {
        return new BySearchType(searchType);
    }

    private static class BySearchType extends By<SearchType[]> {

        public BySearchType(SearchType... searchType) {
            setValue(searchType);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(XPathBuilder builder) {
            //searchType(getValue());
            builder.setSearchTextType(getValue());
//            builder.defaultSearchTextType.addAll(Arrays.asList(getValue()));

        }
    }
}
