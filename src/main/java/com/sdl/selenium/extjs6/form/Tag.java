package com.sdl.selenium.extjs6.form;

import com.google.common.base.Strings;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ITag;
import com.sdl.selenium.web.utils.RetryUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Tag extends Combo implements ITag {

    private final WebLocator list = new WebLocator(this).setElPath("/ancestor::*[contains(concat(' ', @class, ' '), ' x-tagfield-list ')]");
    private final WebLocator aria = new WebLocator(this).setElPath("/ancestor::*[contains(concat(' ', @class, ' '), ' x-tagfield ') and contains(concat(' ', @class, ' '), ' x-form-field ')]");

    public boolean removeAll() {
        boolean removed;
        WebLocator item = new WebLocator(list).setClasses("x-tagfield-item");
        int size = item.size();
        if(size > 0) {
            WebLocator closeEl = new WebLocator(item).setClasses("x-tagfield-item-close");
            removed = RetryUtils.retry(size, () -> closeEl.click() && !item.ready(Duration.ofMillis(500)));
        } else {
            removed = true;
        }
        return removed;
    }

    public boolean doRemoveAll() {
        boolean removed;
        WebLocator item = new WebLocator(list).setClasses("x-tagfield-item");
        int size = item.size();
        if(size > 0) {
            WebLocator closeEl = new WebLocator(item).setClasses("x-tagfield-item-close");
            removed = RetryUtils.retry(size, () -> closeEl.doClick() && !item.ready(Duration.ofMillis(500)));
        } else {
            removed = true;
        }
        return removed;
    }

    public boolean remove(String... values) {
        boolean removed = true;
        for (String value : values) {
            WebLocator item = new WebLocator(list).setClasses("x-tagfield-item").setText(value, SearchType.DEEP_CHILD_NODE_OR_SELF);
            if (item.ready(Duration.ofMillis(500))) {
                WebLocator closeEl = new WebLocator(item).setClasses("x-tagfield-item-close");
                removed = removed && RetryUtils.retry(2, () -> closeEl.click() && !item.ready(Duration.ofMillis(500)));
            }
        }
        return removed;
    }

    public boolean doRemove(String... values) {
        boolean removed = true;
        for (String value : values) {
            WebLocator item = new WebLocator(list).setClasses("x-tagfield-item").setText(value, SearchType.DEEP_CHILD_NODE_OR_SELF);
            if (item.ready(Duration.ofMillis(500))) {
                WebLocator closeEl = new WebLocator(item).setClasses("x-tagfield-item-close");
                removed = removed && RetryUtils.retry(2, () -> closeEl.doClick() && !item.ready(Duration.ofMillis(500)));
            }
        }
        return removed;
    }

    public List<String> getAllSelectedValues() {
        String text = list.getText();
        if (Strings.isNullOrEmpty(text)) {
            return new ArrayList<>();
        }
        
        return Arrays.stream(text.split("\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    public String getValue(){
        return String.join(",", getAllSelectedValues());
    }

    public boolean expand() {
        if ("false".equals(aria.getAttribute("aria-expanded"))) {
            clickIcon("arrow-trigger");
            return "true".equals(aria.getAttribute("aria-expanded"));
        } else {
            return true;
        }
    }

    public boolean collapse() {
        return "false".equals(aria.getAttribute("aria-expanded")) || clickIcon("arrow-trigger");
    }

    public String getError() {
        WebLocator error = new WebLocator(this).setRoot("/../../../../../../../").setClasses("x-form-error-wrap");
        return error.getText();
    }
}