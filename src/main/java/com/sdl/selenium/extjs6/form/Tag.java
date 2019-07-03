package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.form.ITag;
import com.sdl.selenium.web.utils.RetryUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class Tag extends Field implements ITag {

    private WebLocator list = new WebLocator(this).setElPath("/ancestor::*[contains(concat(' ', @class, ' '), ' x-tagfield-list ')]");

    protected WebLocator getComboEl(String value, long optionRenderMillis, SearchType... searchType) {
        return new WebLocator(getBoundList()).setTag("li").setText(value, searchType).setRenderMillis(optionRenderMillis).setInfoMessage(value);
    }

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

    public List<String> getAllValues() {
        waitToRender(400L);
        expand();
        WebLocator comboList = new WebLocator(getBoundList()).setClasses("x-list-plain").setVisibility(true);
        String text = comboList.getText();
        String[] comboValues = new String[0];
        if (text != null) {
            comboValues = text.split("\\n");
        }
        collapse();
        return Arrays.asList(comboValues);
    }

    public List<String> getAllSelectedValues() {
        String text = list.getText();
        if (text != null) {
            boolean isEmpty = false;
            String[] comboValues = text.split("\\n");
            if (comboValues.length == 1) {
                isEmpty = "".equals(comboValues[0]);
            }
            return isEmpty ? new ArrayList<>() : Arrays.asList(comboValues);
        }
        return new ArrayList<>();
    }

    public boolean clickIcon(String icon) {
        if (ready()) {
            WebLocator iconLocator = getTriggerEl(this, icon);
            iconLocator.setRenderMillis(500);
            return iconLocator.click();
        } else {
            log.warn("clickIcon : field is not ready for use: " + this);
        }
        return false;
    }

    public boolean expand() {
        return clickIcon("arrow-trigger");
    }
}