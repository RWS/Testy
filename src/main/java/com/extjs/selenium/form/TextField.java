package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;

import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class TextField extends ExtJsComponent {
    private static final Logger logger = Logger.getLogger(TextField.class);

    public TextField() {
        setClassName("TextField");
        setTag("input");
    }

    public TextField(String cls) {
        this();
        setCls(cls);
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
    public String getBasePathSelector() {
        String selector = super.getBasePathSelector();

        selector += " and not (@type='hidden') ";
        // TODO use also if disabled some parents then can;t click/select some children
        // x-panel x-panel-noborder x-masked-relative x-masked  x-border-panel
        selector = Utils.fixPathSelector(selector);

        return selector;
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector;
    }

    public static void copyToClipboard(final String text) {
        final StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,
                new ClipboardOwner() {
                    @Override
                    public void lostOwnership(final java.awt.datatransfer.Clipboard clipboard, final Transferable contents) {
                        // do nothing
                    }
                });
    }

    public boolean pasteInValue(String value) {
        if (ready()) {
            if (value != null) {
                currentElement.clear();
                copyToClipboard(value);
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
        if (value != null) {
            if (executor.isSamePath(this, this.getPath()) || ready()) {
                if (hasWebDriver()) {
                    try {
                        currentElement.clear();
                        currentElement.sendKeys(value);
                    } catch (StaleElementReferenceException exception){
                        logger.warn("StaleElementReferenceException" + exception);
                        logger.warn("Set value(" + this + ") second try:  '" + value + "'");
                        if(ready()){
                            currentElement.clear();
                            currentElement.sendKeys(value);
                        }
                    }
                } else {
                    String path = getPath();
                    selenium.focus(path); // to scroll to this element (if element is not visible)
                    selenium.type(path, value);
                    selenium.keyUp(path, (value.length() == 0) ? "\13" : value.substring(value.length() - 1));
                    selenium.fireEvent(path, "blur");
                }
                logger.info("Set value(" + this + "): '" + value + "'");
                return true;
            } else {
                logger.warn("setValue : field is not ready for use: " + this);
            }
        }
        return false;
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
        String value = "";
        if (ready()) {
            if (hasWebDriver()) {
                final String attributeValue = currentElement.getAttribute("value");
                if (attributeValue != null) {
                    value = attributeValue;
                } else {
                    logger.warn("getValue : value attribute is null: " + this);
                }
            } else {
                value = selenium.getValue(getPath());
            }
        } else {
            logger.warn("getValue : field is not ready for use: " + this);
        }
        return value;
    }

    /**
     * Using xPath only
     *
     * @param value
     * @return
     */
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
            iconLocator.setInfoMessage("trigger-" + icon);
            try {
                if (hasWebDriver()) {
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