package com.sdl.selenium.web.test;

public abstract class By<T> {

    private T value;

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

    abstract void init(PathBuilder builder);

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
            return ".//*[" + containingWord("class", getValue()) + "]";
        }

        private String containingWord(String attribute, String word) {
            return "contains(concat(' ',normalize-space(@" + attribute + "),' '),' "
                    + word + " ')";
        }

        public void init(PathBuilder builder) {
            builder.setClasses(getValue());
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

        public void init(PathBuilder builder) {
            builder.setId(getValue());
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

        public void init(PathBuilder builder) {
            builder.setElPath(getValue());
        }
    }

    public static By text(final String text) {
        if (text == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the xpath expression is null.");

        return new ByText(text);
    }

    public static class ByText extends By<String> {

        public ByText(String text) {
            setValue(text);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setText(getValue());
        }
    }

}
