package com.sdl.selenium.web.form;

import com.sdl.selenium.web.PathBuilder;

public abstract class By<T> extends com.sdl.selenium.web.By {

    public static By type(final String type) {
        if (type == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the className expression is null.");

        return new ByType(type);
    }

    private static class ByType extends By<String> {

        public ByType(String className) {
            setValue(className);
        }

        public String getPath() {
            return (String) getValue();
        }

        public void init(PathBuilder builder) {
            builder.setElPathSuffix("input-type", builder.applyTemplate("input-type", (String) getValue()));
        }

        public void initDefault(PathBuilder builder) {
            if (builder.getElPathSuffix() == null) {
                init(builder);
            }
        }
    }

}
