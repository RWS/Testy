package com.sdl.selenium.extjs6.notification;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;

import static org.hamcrest.MatcherAssert.assertThat;

public class Notification extends WebLocator {

    public Notification() {
        setClasses("msg", "x-border-box");
        setVisibility(true);
        WebLocator c = new WebLocator().setClasses("msg-container");
        setContainer(c);
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Notification(String msg) {
        this(msg, SearchType.CONTAINS);
    }

    public Notification(String msg, SearchType... searchTypes) {
        this();
        setText(msg, searchTypes);
    }

    public void close() {
        boolean close = doClose();
        assertThat("Could not click " + toString(), close);
    }

    public boolean doClose() {
        WebLocator close = new WebLocator(this).setClasses("x-tool-close");
        Utils.sleep(350);
        return RetryUtils.retry(3, () -> {
            this.waitToRender(300L, false);
            return close.doClick() || !this.waitToRender(100L, false);
        });
    }
}