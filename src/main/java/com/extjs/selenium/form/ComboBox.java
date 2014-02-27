package com.extjs.selenium.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;
import junit.framework.Assert;
import org.apache.log4j.Logger;

public class ComboBox extends TextField implements ICombo {
    private static final Logger logger = Logger.getLogger(ComboBox.class);
    private static String listClass = "x-combo-list";

    //TODO change the way comboBox is identified, without using cls
    // (create baseCls and if there is no cls, label then take first combo by baseCls)
    public ComboBox() {
        setClassName("ComboBox");
    }

    /**
     * @deprecated
     */
    public ComboBox(String cls) {
        this();
        this.setClasses(cls);
    }

    public ComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public ComboBox(WebLocator container, String label) {
        this();
        setContainer(container);
        setLabel(label);
    }

    public ComboBox(String name, WebLocator container) {
        this();
        setContainer(container);
        setName(name);
    }

    public ComboBox(WebLocator container, String cls, String name, boolean hasName) {
        this();
        setContainer(container);
        setClasses(cls);
        setName(name);
    }

    /**
     * @param value
     * @return true if value was selected
     */
    @Override
    public boolean setValue(String value) {
        return select(value);
    }

    public boolean select(String value, Boolean startWith) {
        boolean selected;
        String componentId;
        String info = toString();

        String valueTest = startWith ? ("starts-with(text(),'" + value + "')") : ("text()='" + value + "'");
        WebLocator comboListElement = new WebLocator(listClass).setStyle("visibility: visible;").setInfoMessage("ComboList");
        WebLocator option = new WebLocator(comboListElement).setElPath("//*[" + valueTest + "]").setRenderMillis(300).setInfoMessage(value);

        if (clickIcon("arrow")) {
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
//                logger.error(e);
                ready();
                componentId = getAttributeId();
                selected = setValueWithJs(componentId, value);
            }

            if (selected) {
                logger.info("Set value(" + info + "): " + value);
                Utils.sleep(200);
                return true;
            } else {
                clickIcon("arrow"); // to close combo
            }
            logger.debug("(" + info + ") The option '" + value + "' could not be located. " + option.getPath());
        } else {
            logger.debug("(" + info + ") The combo or arrow could not be located.");
        }
        return false;
    }

    private String getListId() {
        String componentId;
        ready();
        componentId = getAttributeId();
        String getListIdScript = "return Ext.getCmp('" + componentId + "').list.id;";
        logger.debug("script:" + getListIdScript);
        String listId = (String) WebLocatorUtils.doExecuteScript(getListIdScript);
        logger.debug("listId:" + listId);
        return listId;
    }

    /**
     * this method is used in case normal flow for selection fails
     *
     * @param componentId ComboBox id so we can use directly js to force selection of that value
     * @param value
     * @return
     */
    private boolean setValueWithJs(final String componentId, final String value) {
        boolean selected;
        String script = "return (function(){var c  = Ext.getCmp('" + componentId + "'); var record = c.findRecord(c.displayField, '" + value + "');" +
                "if(record){c.onSelect(record, c.store.indexOf(record)); return true;} return false;})()";
        logger.warn("force ComboBox Value with js: " + script);
        selected = (Boolean) WebLocatorUtils.doExecuteScript(script);
        logger.warn("force ComboBox select result: " + selected);
        return selected;
    }

    @Override
    public boolean select(String value) {
        return select(value, false);
    }

    public boolean assertSelect(String value) {
        boolean selected = select(value);
        if (!selected) {
            Assert.fail("Could not selected value on : " + this);
        }
        return true;
    }
}