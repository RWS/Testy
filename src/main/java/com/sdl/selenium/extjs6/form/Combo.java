package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.Go;
import com.sdl.selenium.extjs6.panel.Pagination;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public abstract class Combo extends Field implements ICombo {
    private static final Logger log = LogManager.getLogger(Combo.class);
    private final Pagination paginationEl = new Pagination(getBoundList()).setRender(Duration.ofMillis(300));

    /**
     * Returns the combo element (option) matching the given value and search type, waiting for it to render.
     *
     * @param value              the value to search for in the combo box
     * @param optionRenderMillis the maximum time to wait for the option to render, in milliseconds
     * @param searchType         the search strategy to use (e.g., EQUALS, CONTAINS)
     * @return the WebLocator representing the combo option
     * @deprecated Use {@link #getComboEl(String, Duration, SearchType...)} instead.
     */
    @Deprecated
    protected WebLocator getComboEl(String value, long optionRenderMillis, SearchType... searchType) {
        return getComboEl(value, Duration.ofMillis(optionRenderMillis), searchType);
    }

    /**
     * Returns the combo element (option) matching the given value and search type, waiting for it to render.
     *
     * @param value      the value to search for in the combo box
     * @param duration   the maximum time to wait for the option to render
     * @param searchType the search strategy to use (e.g., EQUALS, CONTAINS)
     * @param <T>        the type of WebLocator
     * @return the WebLocator representing the combo option
     */
    protected <T extends WebLocator> T getComboEl(String value, Duration duration, SearchType... searchType) {
        return new WebLocator(getBoundList()).setTag("li").setText(value, searchType).setRender(duration).setInfoMessage(value);
    }

    /**
     * Gets the current value selected in the combo box.
     *
     * @return the selected value as a String
     */
    @Override
    public String getValue() {
        ready();
        return executor.getValue(this);
    }

    /**
     * Retrieves all values from the combo box as a list of strings.
     *
     * @return a list of all values in the combo box
     */
    public List<String> getAllValues() {
        waitToRender(Duration.ofMillis(300L));
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

    /**
     * Retrieves all values from the combo box and maps them to objects of the specified type.
     *
     * @param type the class type to map the values to
     * @param <V>  the type of the objects in the returned list
     * @return a list of objects of type V, or null if values are not available
     */
    public <V> List<V> getAllValues(Class<V> type) {
        List<String> values = getAllValues();
        if (values == null) {
            return null;
        }
        if (values.isEmpty()) {
            return new ArrayList<>();
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

    /**
     * Expands the combo box dropdown if it is not already expanded.
     *
     * @return true if the combo box is expanded after the call, false otherwise
     */
    public boolean expand() {
        return "true".equals(getAttribute("aria-expanded")) || clickIcon("trigger");
    }

    /**
     * Collapses the combo box dropdown if it is not already collapsed.
     *
     * @return true if the combo box is collapsed after the call, false otherwise
     */
    public boolean collapse() {
        return "false".equals(getAttribute("aria-expanded")) || clickIcon("trigger");
    }

    /**
     * Selects the specified value in the combo box using the EQUALS search type.
     *
     * @param value the value to select in the combo box
     * @return true if the value is successfully selected, false otherwise
     */
    public boolean select(String value) {
        return select(value, SearchType.EQUALS);
    }

    /**
     * Selects the specified value in the combo box using the provided search type(s).
     *
     * @param value      the value to select in the combo box
     * @param searchType the search strategy to use for finding the value (e.g., EQUALS, CONTAINS)
     * @return true if the value is successfully selected, false otherwise
     */
    public boolean select(String value, SearchType... searchType) {
        boolean selected = doSelect(value, Duration.ofMillis(300), false, searchType);
        assertThat("Could not selected value on: " + this, selected);
        return selected;
    }

    /**
     * Selects the specified value in the combo box and verifies if it is selected.
     * Uses a default timeout of 300 milliseconds for the selection operation.
     *
     * @param value      the value to select in the combo box
     * @param searchType the search strategy to use for finding the value (e.g., EQUALS, CONTAINS)
     * @return true if the value is successfully selected, false otherwise
     */
    public boolean selected(String value, SearchType... searchType) {
        return selected(value, Duration.ofMillis(300L), searchType);
    }

    /**
     * Selects the specified value in the combo box and verifies if it is selected within the given duration.
     * The method retries the selection up to 5 times if the value is not immediately selected.
     *
     * @param value      the value to select in the combo box
     * @param duration   the maximum time to wait for the option to be rendered and selected
     * @param searchType the search strategy to use for finding the value (e.g., EQUALS, CONTAINS)
     * @return true if the value is successfully selected, false otherwise
     */
    public boolean selected(String value, Duration duration, SearchType... searchType) {
        Boolean selected = RetryUtils.retry(5, () -> {
            doSelect(value, duration, false, searchType);
            return getValue().equals(value);
        });
        return selected;
    }

    /**
     * @param value      value
     * @param duration   eg. 300ms
     * @param pagination true | false
     * @param searchType use {@link SearchType}
     * @return true if value was selected
     */
    public boolean doSelect(String value, Duration duration, boolean pagination, SearchType... searchType) {
        if (value.equals(getValue())) {
            return true;
        }
        boolean selected;
        String info = toString();
        WebLocator option = getComboEl(value, duration, searchType).setVisibility(true);
        boolean trigger = expand();
        if (trigger) {
            if (pagination) {
                do {
                    selected = option.doClick();
                    if (selected) {
                        break;
                    }
                } while (paginationEl.goToNextPage());
            } else {
                selected = option.doClick();
                if (!selected && option.isPresent()) {
                    option.scrollIntoView(Go.NEAREST);
                    selected = option.doClick();
                }
            }
            if (selected) {
                log.info("Set value({}): {}", info, value);
                Utils.sleep(10);
                return true;
            }
            collapse();
            log.debug("({}) The option '{}' could not be located. {}", info, value, option.getXPath());
        } else {
            log.debug("({}) The combo or arrow could not be located.", info);
        }
        return false;
    }
}