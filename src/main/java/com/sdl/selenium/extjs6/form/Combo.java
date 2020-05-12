package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.extjs6.panel.Pagination;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public abstract class Combo extends Field implements ICombo {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Combo.class);
    private Pagination paginationEl = new Pagination(getBoundList()).setRenderMillis(300);

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
        WebLocator item = new WebLocator(comboList).setClasses("x-boundlist-item");
        int size = item.size();
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            item.setResultIdx(i);
            String text = item.getText();
            result.add(text);
        }
        collapse();
        return result;
    }

    public <V> List<V> getAllValues(Class<V> type) {
        List<String> values = getAllValues();
        if (values == null) {
            return null;
        }
        Class<?> newClazz;
        int size = values.get(0).split("\\n").length;
        Constructor constructor = null;
        try {
            newClazz = Class.forName(type.getTypeName());
            Constructor[] constructors = newClazz.getConstructors();
            for (Constructor c : constructors) {
                int parameterCount = c.getParameterCount();
                if (size == parameterCount) {
                    constructor = c;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Constructor finalConstructor = constructor;
        return values.stream().map(t -> {
            try {
                Constructor<V> constructorTemp = (Constructor<V>) finalConstructor;
                String[] split = t.split("\\n");
                List<String> list = Arrays.asList(split);
                return constructorTemp.newInstance(list.toArray(new Object[0]));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public boolean expand() {
        return "true".equals(getAttribute("aria-expanded")) || clickIcon("trigger");
    }

    public boolean collapse() {
        return "false".equals(getAttribute("aria-expanded")) || clickIcon("trigger");
    }

    public boolean select(String value) {
        return select(value, SearchType.EQUALS);
    }

    public boolean select(String value, SearchType... searchType) {
        boolean selected = doSelect(value, 300L, false, searchType);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    /**
     * @param value              value
     * @param optionRenderMillis eg. 300ms
     * @param pagination         true | false
     * @param searchType         use {@link SearchType}
     * @return true if value was selected
     */
    public boolean doSelect(String value, long optionRenderMillis, boolean pagination, SearchType... searchType) {
        if (value.equals(getValue())) {
            return true;
        }
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, optionRenderMillis, searchType).setVisibility(true);
        boolean trigger = expand();
        if (trigger) {
            if (pagination) {
                do {
                    if (selected = option.doClick()) {
                        break;
                    }
                } while (paginationEl.goToNextPage());
            } else {
                selected = option.doClick();
                if (!selected && option.isPresent()) {
                    WebLocatorUtils.scrollToWebLocator(option);
                    selected = option.doClick();
                    if (!selected && option.isPresent()) {
                        WebLocatorUtils.doExecuteScript("arguments[0].click()", option.getWebElement());
                    }
                }
            }
            if (selected) {
                log.info("Set value(" + info + "): " + value);
                Utils.sleep(20);
                return true;
            }
            collapse();
            log.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            log.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }
}