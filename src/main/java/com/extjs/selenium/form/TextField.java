package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

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
        * @return baseSelector
        */
       public String getBasePathSelector(){
           String selector = super.getBasePathSelector();

           selector += " and not (@type='hidden') ";
           // TODO use also if disabled some parents then can;t click/select some children
           // x-panel x-panel-noborder x-masked-relative x-masked  x-border-panel
           selector = Utils.fixPathSelector(selector);

           return selector;
       }

     /**
        * Containing baseCls, class, name and style
        * @return baseSelector
        */
       public String getBaseCssSelector(){
           String selector = super.getBaseCssSelector();

//           selector += " and not (@type='hidden') ";
           selector += ":not([type='hidden'])";
           // TODO use also if disabled some parents then can;t click/select some children
           // x-panel x-panel-noborder x-masked-relative x-masked  x-border-panel
           selector = Utils.fixCssSelector(selector);

           return selector;
       }


    public String getItemCssSelector(boolean disabled) {
        String selector = getBaseCssSelector();

//        selector = "//input" + (selector.length() > 0 ? ("["+ selector +"]") : "");
        selector = "";

        if (hasLabel()) {
//            selector = "//label[text()='" + label + "']//following-sibling::*" + selector;
            selector = selector + " label:contains('" + getLabel() + "')"  ;
        }
        selector = "" + (selector.length() > 0 ? ( selector +" + * " + getTag() + getBaseCssSelector()) : "");
        selector = Utils.fixCssSelector(selector);
        return selector;
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = "//" + getTag() + (selector.length() > 0 ? ("["+ selector +"]") : "");
        return selector;
    }

     public static void copyToClipboard( final String text ) {
        final StringSelection stringSelection = new StringSelection( text );
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents( stringSelection,
        new ClipboardOwner() {
              @Override
              public void lostOwnership( final java.awt.datatransfer.Clipboard clipboard, final Transferable contents )
              {
                // do nothing
              }
        } );
      }

    public boolean pasteInValue(String value) {
        if(ready()){
            if(value != null && value.length() > 0){
                String path = getPath();
                if (isElementPresent()) {
                    logger.info("Set value(" + toString() +"): " + value);
                    currentElement.clear();
                    copyToClipboard(value);
                    final Actions builder = new Actions( driver );
                    builder.click( currentElement ).keyDown( Keys.CONTROL ).sendKeys( "V" ).keyUp( Keys.CONTROL );
                          final Action paste = builder.build();
                          paste.perform();
                    return true;
                }
            }
        } else {
            logger.warn("setValue : field is not ready for use: " + toString());
        }
        return false;
    }

    public boolean setValue(String value) {
        return setValue(value, false);
    }

    public boolean setValue(String value, boolean useCssSelectors) {
        if (value != null) {
            if (ready(useCssSelectors)) {
                logger.info("Setting value(" + toString() + "): '" + value + "'");
                if(hasWebDriver()){
                    currentElement.clear();
                    currentElement.sendKeys(value);
                    return true;
                } else {
                    String path = getPath();
                    selenium.focus(path); // to scroll to this element (if element is not visible)
                    selenium.type(path, value);
                    selenium.keyUp(path, (value.length() == 0) ? "\13" : value.substring(value.length() - 1));
                    selenium.fireEvent(path, "blur");
                    return true;
                }
            } else {
                logger.warn("setValue : field is not ready for use: " + toString());
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
     * getValue using xPath
     * @return
     */
    public String getValue() {
        return getValue(false);
    }

    /**
     * getValue using xPath or Css Selectors, depending on the parameter
     * @param useCssSelectors
     * @return
     */
    public String getValue(boolean useCssSelectors) {
        String value = "";
        if(ready(useCssSelectors)){
            // using WebDriver -> there are situations when the value is taken by getText() or getAttribute("value")
            if (hasWebDriver()) {
                final String attributeValue = currentElement.getAttribute("value");
                final String text = currentElement.getText();
                if (!attributeValue.equals("") && text.equals("")) {
                    value = attributeValue;
                } else if (attributeValue.equals("") && !text.equals("")) {
                    value = text;
                } else if (!attributeValue.equals("") && !text.equals("") && attributeValue.equals(text)) {
                    value = text;
                } else if (!attributeValue.equals("") && !text.equals("") && !attributeValue.equals(text)) {
                    logger.debug("Not sure what value to use: \ncurrentElement.getText()= " + text + "\ncurrentElement.getAttribute(\"value\"): " + attributeValue);
                }
            } else {
                value = selenium.getValue(getPath());
            }
        } else {
            logger.warn("getValue : field is not ready for use: " + toString());
        }
        return value;
    }


    /**
     * Using xPath only
     * @param value
     * @return
     */
    public boolean verifyValue(String value) {
        return verifyValue(value, false);
    }

    /**
     * Using xPath or Css Selectors
     * @param value
     * @param useCssSelectors
     * @return
     */
    public boolean verifyValue(String value, boolean useCssSelectors) {
        String v = getValue(useCssSelectors);
        logger.debug("The values '" + v + "' and '" + value + "' " + (value.equals(v) ? "" : "do NOT ") + "match");
        return value.equals(v);
    }

    public String getTriggerPath(String icon) {
        String triggerPath = "/parent::*//*[contains(@class,'x-form-" + icon + "-trigger')]";
        return triggerPath;
    }

    public boolean clickIcon(String icon) {
        if(ready()){
            String triggerPath = getTriggerPath(icon);
            WebLocator iconLocator = new WebLocator(this, triggerPath);
            iconLocator.setInfoMessage("trigger-" + icon);
            try {
                if(hasWebDriver()){
                    return iconLocator.click();
                } else {
                    return iconLocator.clickAt();
                }
            } catch (Exception e){
                logger.error("Exception on clickIcon: " + e.getMessage());
                return false;
            }
        } else {
            logger.warn("clickIcon : field is not ready for use: " + toString());
        }
        return false;
    }
}
