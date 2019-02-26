package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ComboBox extends TextField implements ICombo {

    //TODO change the way comboBox is identified, without using cls
    // (create baseCls and if there is no cls, label then take first combo by baseCls)
    public ComboBox() {
        setClassName("ComboBox");
    }

    public ComboBox(Locator container) {
        this();
        setContainer(container);
    }

    public ComboBox(Locator container, String label) {
        this(container);
        setLabel(label);
    }

    public ComboBox(String name, Locator container) {
        this(container);
        setName(name);
    }

    /**
     * @param value value
     * @return true if value was selected
     */
    @Override
    public boolean setValue(String value) {
        return select(value);
    }

    /**
     * @param value value
     * @param searchType {@link SearchType}
     * @param optionRenderMillis 300
     * @return true or false
     */
    public boolean doSelect(String value, SearchType searchType, long optionRenderMillis) {
        boolean selected;
        String componentId;
        String info = toString();

        WebLocator comboListElement = new WebLocator().setClasses("x-combo-list").setStyle("visibility: visible;").setInfoMessage(this + " -> combo-list");
        WebLocator option = new WebLocator(comboListElement).setText(value, searchType).setRenderMillis(optionRenderMillis).setInfoMessage(value);

        if (expand()) {
            try {
                // TODO temporary try this solution for IE because is too slow
//                if (isIE()) {
//                    componentId = getAttributeId();
//                    selected = setValueWithJs(componentId, value);
//                    return selected;
//                }
                if (WebDriverConfig.isIE()) {
                    comboListElement.setId(getListId());
                    option.setContainer(comboListElement);
                }
                selected = option.click();
            } catch (Exception e) {
//                log.error(e);
                ready();
                componentId = getAttributeId();
                selected = setValueWithJs(componentId, value);
            }

            if (selected) {
                log.info("Set value(" + info + "): " + value);
                Utils.sleep(200);
                return true;
            } else {
                collapse(); // to close combo
            }
            log.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getXPath());
        } else {
            log.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    public boolean select(String value, SearchType searchType) {
        boolean selected = doSelect(value, searchType, 300);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    private String getListId() {
        String componentId;
        ready();
        componentId = getAttributeId();
        String getListIdScript = "return Ext.getCmp('" + componentId + "').list.id;";
        log.debug("script:" + getListIdScript);
        String listId = (String) WebLocatorUtils.doExecuteScript(getListIdScript);
        log.debug("listId:" + listId);
        return listId;
    }

    /**
     * this method is used in case normal flow for selection fails
     *
     * @param componentId ComboBox id so we can use directly js to force selection of that value
     * @param value       value
     * @return true or false
     */
    private boolean setValueWithJs(final String componentId, final String value) {
        boolean selected;
        String script = "return (function(){var c  = Ext.getCmp('" + componentId + "'); var record = c.findRecord(c.displayField, '" + value + "');" +
                "if(record){c.onSelect(record, c.store.indexOf(record)); return true;} return false;})()";
        log.warn("force ComboBox Value with js: " + script);
        selected = (Boolean) WebLocatorUtils.doExecuteScript(script);
        log.warn("force ComboBox select result: " + selected);
        return selected;
    }

    @Override
    public boolean select(String value) {
        return select(value, SearchType.EQUALS);
    }

    @Override
    public boolean expand() {
        return  clickIcon("arrow");
    }

    @Override
    public boolean collapse() {
        return  clickIcon("arrow");
    }
}