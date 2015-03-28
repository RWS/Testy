package com.sdl.selenium.web;

import java.util.Arrays;

public abstract class By<T> {

    private T value;
    private SearchType[] searchType;

    // abstract TODO remove it
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

    public abstract void init(PathBuilder builder);

    public abstract void initDefault(PathBuilder builder);

    // =========================

    public static By className(final String className) {
        if (className == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the className expression is null.");

        return new ByClassName(className);
    }

    private static class ByClassName extends By<String> {

        public ByClassName(String className) {
            setValue(className);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setClassName(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getClassName() == null) {
                init(builder);
            }
        }
    }

    public static By baseCls(final String baseCls) {
        if (baseCls == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the baseCls expression is null.");

        return new ByBaseCls(baseCls);
    }

    private static class ByBaseCls extends By<String> {

        public ByBaseCls(String baseCls) {
            setValue(baseCls);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setBaseCls(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getBaseCls() == null) {
                init(builder);
            }
        }
    }

    public static By cls(final String cls) {
        if (cls == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the cls expression is null.");

        return new ByCls(cls);
    }

    private static class ByCls extends By<String> {

        public ByCls(String cls) {
            setValue(cls);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setCls(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getCls() == null) {
                init(builder);
            }
        }
    }

    public static By classes(final String... classes) {
        if (classes == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the classes expression is null.");

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

        public void init(PathBuilder builder) {
            builder.setClasses(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getClasses() == null) {
                init(builder);
            }
        }
    }

    public static By excludeClasses(final String... excludeClasses) {
        if (excludeClasses == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the excludeClasses expression is null.");

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

        public void init(PathBuilder builder) {
            builder.setExcludeClasses(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getExcludeClasses() == null) {
                init(builder);
            }
        }
    }

    public static By id(final String id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the id expression is null.");

        return new ById(id);
    }

    private static class ById extends By<String> {

        public ById(String id) {
            setValue(id);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setId(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getId() == null) {
                init(builder);
            }
        }
    }

    public static By name(final String name) {
        if (name == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the name expression is null.");

        return new ByName(name);
    }

    private static class ByName extends By<String> {

        public ByName(String id) {
            setValue(id);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setName(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getName() == null) {
                init(builder);
            }
        }
    }

    public static By text(final String text, final SearchType... searchType) {
        if (text == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the text expression is null.");

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

        public void init(PathBuilder builder) {
            builder.setText(getValue(), getSearchType());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getText() == null) {
                init(builder);
            }
        }
    }

    public static By style(final String style) {
        if (style == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the style expression is null.");

        return new ByStyle(style);
    }

    private static class ByStyle extends By<String> {

        public ByStyle(final String style) {
            setValue(style);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setStyle(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getStyle() == null) {
                init(builder);
            }
        }
    }

    public static By title(final String title) {
        if (title == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the title expression is null.");

        return new ByTitle(title);
    }

    private static class ByTitle extends By<String> {

        public ByTitle(final String style) {
            setValue(style);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setTitle(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getTitle() == null) {
                init(builder);
            }
        }
    }

    public static By tag(final String tag) {
        if (tag == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the tag expression is null.");

        return new ByTag(tag);
    }

    private static class ByTag extends By<String> {

        public ByTag(String tag) {
            setValue(tag);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setTag(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if ("*".equals(builder.getTag())) {
                init(builder);
            }
        }
    }

    public static By labelTag(final String labelTag) {
        if (labelTag == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the labelTag expression is null.");

        return new ByLabelTag(labelTag);
    }

    private static class ByLabelTag extends By<String> {

        public ByLabelTag(String labelTag) {
            setValue(labelTag);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setLabelTag(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getLabelTag() == null) {
                init(builder);
            }
        }
    }

    public static By label(final String label, final SearchType... searchType) {
        if (label == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the label expression is null.");

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

        public void init(PathBuilder builder) {
            builder.setLabel(getValue(), getSearchType());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getLabel() == null) {
                init(builder);
            }
        }
    }

    public static By labelPosition(final String labelPosition) {
        if (labelPosition == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the labelPosition expression is null.");

        return new ByLabelPosition(labelPosition);
    }

    private static class ByLabelPosition extends By<String> {

        public ByLabelPosition(String labelPosition) {
            setValue(labelPosition);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setLabelPosition(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getLabelPosition() == null) {
                init(builder);
            }
        }
    }

    public static By position(final Integer position) {
        if (position == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the position expression is null.");

        return new ByPosition(position);
    }

    private static class ByPosition extends By<Integer> {

        public ByPosition(Integer position) {
            setValue(position);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(PathBuilder builder) {
            builder.setPosition(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getPosition() == -1) {
                init(builder);
            }
        }
    }

    public static By pathSuffix(final String pathSuffix) {
        if (pathSuffix == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the pathSuffix expression is null.");

        return new ByPathSuffix(pathSuffix);
    }

    private static class ByPathSuffix extends By<String> {

        public ByPathSuffix(String xpath) {
            setValue(xpath);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setElPathSuffix(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getElPathSuffix() == null) {
                init(builder);
            }
        }
    }

    public static By xpath(final String xpath) {
        if (xpath == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the xpath expression is null.");

        return new ByXpath(xpath);
    }

    private static class ByXpath extends By<String> {

        public ByXpath(String xpath) {
            setValue(xpath);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setElPath(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getElPath() == null) {
                init(builder);
            }
        }
    }

    public static By container(final WebLocator container) {
        if (container == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the container expression is null.");

        return new ByContainer(container);
    }

    private static class ByContainer extends By<WebLocator> {

        public ByContainer(WebLocator webLocator) {
            setValue(webLocator);
        }

        public String getPath() {
            return getValue().getPath();
        }

        public void init(PathBuilder builder) {
            builder.setContainer(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getContainer() == null) {
                init(builder);
            }
        }
    }

    public static By infoMessage(final String infoMessage) {
        if (infoMessage == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the infoMessage expression is null.");

        return new ByInfoMessage(infoMessage);
    }

    private static class ByInfoMessage extends By<String> {

        public ByInfoMessage(String infoMessage) {
            setValue(infoMessage);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setInfoMessage(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getInfoMessage() == null) {
                init(builder);
            }
        }
    }

    public static By visibility(final Boolean visibility) {
        if (visibility == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the visibility expression is null.");

        return new ByVisibility(visibility);
    }

    private static class ByVisibility extends By<Boolean> {

        public ByVisibility(Boolean visibility) {
            setValue(visibility);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(PathBuilder builder) {
            builder.setVisibility(getValue());
        }

        public void initDefault(PathBuilder builder) {
            if (!builder.isVisibility()) {
                init(builder);
            }
        }
    }

    public static By searchType(final SearchType... searchType) {
        if (searchType == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the searchType expression is null.");

        return new BySearchType(searchType);
    }

    private static class BySearchType extends By<SearchType[]> {

        public BySearchType(SearchType... searchType) {
            setValue(searchType);
        }

        public String getPath() {
            return getValue().toString();
        }

        public void init(PathBuilder builder) {
            builder.defaultSearchTextType.addAll(Arrays.asList(getValue()));
        }

        public void initDefault(PathBuilder builder) {
//            if (builder.getSearchTextType() == null) {
                init(builder);
//            }
        }
    }
}
