package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.form.ICombo;

import java.util.Arrays;
import java.util.List;

public abstract class Combo extends Field implements ICombo {

    protected WebLocator getComboEl(String value, long optionRenderMillis, SearchType... searchType) {
        return new WebLocator(getBoundList()).setTag("li").setText(value, searchType).setRenderMillis(optionRenderMillis).setInfoMessage(value);
    }

    @Override
    public String getValue() {
        ready();
        return executor.getValue(this);
    }

    public List<String> getAllValues() {
        waitToRender(300L);
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

    public boolean expand() {
        return "true".equals(getAttribute("aria-expanded")) || clickIcon("trigger");
    }

    public boolean collapse() {
        return "false".equals(getAttribute("aria-expanded")) || clickIcon("trigger");
    }
}