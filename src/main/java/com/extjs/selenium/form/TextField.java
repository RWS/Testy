package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ITextField;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

public class TextField extends ExtJsComponent implements ITextField {
    private static final Logger logger = Logger.getLogger(TextField.class);

    public TextField() {
        setClassName("TextField");
        setTag("input");
    }

    public TextField(String cls) {
        this();
        setClasses(cls);
    }

    public TextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public TextField(String name, WebLocator container) {
        this(container);
        setName(name);
    }

    // methods

    public String itemToString() {
        String info;
        if (hasLabel()) {
            info = getLabel();
        } else {
            info = super.itemToString();
        }
        return info;
    }

    /**
     * Containing baseCls, class, name and style
     *
     * @return baseSelector
     */
    protected String getBasePathSelector() {
        String selector = super.getBasePathSelector();

        selector += " and not(@type='hidden')";
        // TODO use also if disabled some parents then can;t click/select some children
        // x-panel x-panel-noborder x-masked-relative x-masked  x-border-panel
        return Utils.fixPathSelector(selector);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector;
    }

    public boolean pasteInValue(String value) {
        if (ready()) {
            if (value != null) {
                currentElement.clear();
                Utils.copyToClipboard(value);
                currentElement.sendKeys(Keys.CONTROL, "v");
                logger.info("Set value(" + this + "): " + value + "'");
                return true;
            }
        } else {
            logger.warn("setValue : field is not ready for use: " + toString());
        }
        return false;
    }

    public boolean setValue(String value) {
        return executor.setValue(this, value);
    }

    public boolean assertSetValue(String value) {
        boolean setted = setValue(value);
        if (!setted) {
            Assert.fail("Could not setValue on : " + this);
        }
        return true;
    }

    /**
     * getValue using xPath only, depending on the parameter
     *
     * @return
     */
    public String getValue() {
        return executor.getValue(this);
    }

    /**
     * Using xPath only
     *
     * @param value
     * @return
     */
    @Deprecated
    public boolean verifyValue(String value) {
        String v = getValue();
        logger.debug("The values '" + v + "' and '" + value + "' " + (value.equals(v) ? "" : "do NOT ") + "match");
        return value.equals(v);
    }

    public String getTriggerPath(String icon) {
        return "/parent::*//*[contains(@class,'x-form-" + icon + "-trigger')]";
    }

    public boolean clickIcon(String icon) {
        if (ready()) {
            String triggerPath = getTriggerPath(icon);
            WebLocator iconLocator = new WebLocator(this, triggerPath);
            iconLocator.setRenderMillis(500);
            iconLocator.setInfoMessage("trigger-" + icon);
            try {
                if (WebDriverConfig.hasWebDriver()) {
                    return iconLocator.click();
                } else {
                    return iconLocator.clickAt();
                }
            } catch (Exception e) {
                logger.error("Exception on clickIcon: " + e.getMessage());
                return false;
            }
        } else {
            logger.warn("clickIcon : field is not ready for use: " + this);
        }
        return false;
    }

    /**
     * @return true is the element doesn't have attribute readonly
     */
    public boolean isEditable() {
        return !"true".equals(getAttribute("readonly"));
    }

    /**
     * @return true is the element does have attribute disabled
     */
    public boolean isDisabled() {
        return "true".equals(getAttribute("disabled"));
    }
}