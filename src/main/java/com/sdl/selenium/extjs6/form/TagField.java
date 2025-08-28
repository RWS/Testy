package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TagField extends Tag {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TagField.class);

    public TagField() {
        setClassName("TagField");
        setBaseCls("x-tagfield-input-field");
        setTag("input");
    }

    public TagField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TagField(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        } else {
            List<SearchType> types = new ArrayList<>(Arrays.asList(searchTypes));
            types.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
            searchTypes = types.toArray(new SearchType[0]);
        }
        setLabel(label, searchTypes);
    }

    @Override
    public boolean select(String value) {
        return select(SearchType.EQUALS, value);
    }

    public boolean select(String... values) {
        return select(SearchType.EQUALS, values);
    }

    public boolean select(boolean holdOpen, String... values) {
        return select(SearchType.EQUALS, holdOpen, values);
    }

    public boolean select(SearchType searchType, String... values) {
        boolean selected = doSelect(searchType, Duration.ofMillis(300), true, values);
        assertThat("Could not selected value on : " + this, selected, is(true));
        return selected;
    }

    public boolean select(List<SearchType> searchTypes, List<String> values) {
        boolean selected = doSelect(searchTypes, Duration.ofMillis(300), true, values);
        assertThat("Could not selected value on : " + this, selected, is(true));
        return selected;
    }

    public boolean select(SearchType searchType, Duration duration, String... values) {
        boolean selected = doSelect(searchType, duration, true, values);
        assertThat("Could not selected value on : " + this, selected, is(true));
        return selected;
    }

    public boolean select(SearchType searchType, boolean holdOpen, String... values) {
        boolean selected = doSelect(searchType, Duration.ofMillis(300), holdOpen, values);
        assertThat("Could not selected value on : " + this, selected, is(true));
        return selected;
    }

    /**
     * @param searchType         use {@link SearchType}
     * @param optionRenderMillis eg. 300ms
     * @param holdOpen           true | false
     * @param values             values[]
     * @return true if value was selected
     */
    public boolean doSelect(SearchType searchType, long optionRenderMillis, boolean holdOpen, String... values) {
        return doSelect(searchType, Duration.ofMillis(optionRenderMillis), holdOpen, values);
    }

    /**
     * @param searchType use {@link SearchType}
     * @param duration   eg. 300ms
     * @param holdOpen   true | false
     * @param values     values[]
     * @return true if value was selected
     */
    public boolean doSelect(SearchType searchType, Duration duration, boolean holdOpen, String... values) {
        return doSelect(List.of(searchType), duration, holdOpen, Arrays.asList(values));
    }

    public boolean doSelect(List<SearchType> searchTypes, List<String> values) {
        return doSelect(searchTypes, Duration.ofMillis(300), true, values);
    }

    public boolean doSelect(List<SearchType> searchTypes, Duration duration, List<String> values) {
        return doSelect(searchTypes, duration, true, values);
    }

    /**
     * @param searchTypes searchTypes, use {@link SearchType}
     * @param duration    eg. Duration.ofMillis(300)
     * @param holdOpen    true | false
     * @param values      values
     * @return true if value was selected
     */
    public boolean doSelect(List<SearchType> searchTypes, Duration duration, boolean holdOpen, List<String> values) {
        boolean selected = true;
        String info = toString();
        ready();
        List<String> selectedValues = getAllSelectedValues();
        List<String> selectValues = new ArrayList<>();
        for (String value : values) {
            if (!selectedValues.contains(value)) {
                selectValues.add(value);
            }
        }
        if (holdOpen) {
            if (expand()) {
                for (String value : selectValues) {
                    WebLocator option = getComboEl(value, duration, searchTypes.toArray(new SearchType[0]));
                    selected = selected && option.doClick();
                    if (selected) {
                        log.info("Set value({}): {}", info, value);
                    } else {
                        log.debug("({}) The option '{}' could not be located. {}", info, value, option.getXPath());
                    }
                }
                collapse(); // to close combo
            }
        } else {
            for (String value : selectValues) {
                expand();
                WebLocator option = getComboEl(value, duration, searchTypes.toArray(new SearchType[0]));
                selected = selected && option.doClick();
                if (selected) {
                    log.info("Set value({}): {}", info, value);
                    collapse();
                } else {
                    log.debug("({}) The option '{}' could not be located. {}", info, value, option.getXPath());
                }
            }
        }
        return selected;
    }

    public boolean doSelect(List<String> values, Function<String, Boolean> function) {
        boolean selected = true;
        ready();
        List<String> selectedValues = getAllSelectedValues();
        List<String> selectValues = new ArrayList<>();
        for (String value : values) {
            if (!selectedValues.contains(value)) {
                selectValues.add(value);
            }
        }
        if (expand()) {
            for (String value : selectValues) {
                function.apply(value);
            }
            collapse(); // to close combo
        }

        return selected;
    }

    public boolean setValue(String value) {
        assertReady(value);
        boolean setValue = executor.setValue(this, value);
        Utils.sleep(25);
        return setValue && sendKeys(Keys.ENTER) != null;
    }
}