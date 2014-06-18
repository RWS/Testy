package com.sdl.selenium.web.test;

import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.WebLocator;

public abstract class By extends com.sdl.selenium.web.By<String> {

    @Override
    public String getPath() {
        return null;
    }

    public static By icon(String icon) {
        if (icon == null)
            throw new IllegalArgumentException(
                    "Cannot find elements when the icon expression is null.");

        return new ByIcon(icon);
    }

    public static class ByIcon extends By {

        public ByIcon(String icon) {
            setValue(icon);
        }

        public String getPath() {
            return getValue();
        }

        public void init(PathBuilder builder, WebLocator webLocator) {
            IconPathBuilder pathBuilder2 = (IconPathBuilder) builder;
//            pathBuilder2.setIcon(getValue());
            ((TextField) webLocator).setIcon(getValue());
        }
    }
}
