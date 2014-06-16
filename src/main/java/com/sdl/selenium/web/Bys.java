package com.sdl.selenium.web;

public abstract class Bys<T> {

    private T value;

    private Bys() {
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

    public static Bys cls(final String cls) {
        if (cls == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the class name expression is null.");

        return new BysCls(cls);
    }

    public static class BysCls extends Bys<String> {

        public BysCls(String cls) {
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

    public static Bys classes(final String classes) {
        if (classes == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the class name expression is null.");

        return new BysClasses(classes);
    }

    public static class BysClasses extends Bys<String> {

        public BysClasses(String cls) {
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

    public static Bys id(final String id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the id expression is null.");

        return new BysId(id);
    }

    public static class BysId extends Bys<String> {

        public BysId(String id) {
            setValue(id);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setId(getValue());
        }
    }

    public static Bys xpath(final String xpath) {
        if (xpath == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the xpath expression is null.");

        return new BysXpath(xpath);
    }

    public static class BysXpath extends Bys<String> {

        public BysXpath(String xpath) {
            setValue(xpath);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder) {
            builder.setElPath(getValue());
        }
    }

    public static Bys text(final String text) {
        if (text == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the xpath expression is null.");

        return new BysText(text);
    }

    public static class BysText extends Bys<String> {

        public BysText(String text) {
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
