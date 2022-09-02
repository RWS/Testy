package com.sdl.selenium.extjs6.form;

import com.google.common.base.Strings;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ITag;
import com.sdl.selenium.web.utils.RetryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Tag extends Combo implements ITag {

    private final WebLocator list = new WebLocator(this).setElPath("/ancestor::*[contains(concat(' ', @class, ' '), ' x-tagfield-list ')]");
    private final WebLocator aria = new WebLocator(this).setElPath("/ancestor::*[contains(concat(' ', @class, ' '), ' x-tagfield ') and contains(concat(' ', @class, ' '), ' x-form-field ')]");

    public boolean remove(String... values) {
        boolean removed = true;
        for (String value : values) {
            WebLocator item = new WebLocator(list).setClasses("x-tagfield-item").setText(value, SearchType.DEEP_CHILD_NODE_OR_SELF);
            WebLocator closeEl = new WebLocator(item).setClasses("x-tagfield-item-close");
            removed = removed && RetryUtils.retry(14, closeEl::click);
        }
        return removed;
    }

    public boolean doRemove(String... values) {
        boolean removed = true;
        for (String value : values) {
            WebLocator item = new WebLocator(list).setClasses("x-tagfield-item").setText(value, SearchType.DEEP_CHILD_NODE_OR_SELF);
            WebLocator closeEl = new WebLocator(item).setClasses("x-tagfield-item-close");
            removed = removed && RetryUtils.retry(15, closeEl::doClick);
        }
        return removed;
    }

    public List<String> getAllSelectedValues() {
        String text = list.getText();
        if (!Strings.isNullOrEmpty(text)) {
            boolean isEmpty = false;
            String[] comboValues = text.split("\\n");
            if (comboValues.length == 1) {
                isEmpty = "".equals(comboValues[0]);
            }
            if (isEmpty) {
                return new ArrayList<>();
            } else {
                return Arrays.stream(comboValues).map(String::trim).collect(Collectors.toList());
            }
        } else {
            return new ArrayList<>();
        }
    }

    public boolean expand() {
        return "true".equals(aria.getAttribute("aria-expanded")) || clickIcon("arrow-trigger");
    }

    public boolean collapse() {
        return "false".equals(aria.getAttribute("aria-expanded")) || clickIcon("arrow-trigger");
    }

    public String getError() {
        WebLocator error = new WebLocator(this).setRoot("/../../../../../../../").setClasses("x-form-error-wrap");
        return error.getText();
    }
}