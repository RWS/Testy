package com.extjs.selenium;

import com.extjs.selenium.form.TextField;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

// TODO extend other class not textfield
public class AdvancedTextField extends TextField {
    private static final Logger logger = Logger.getLogger(AdvancedTextField.class);
    private static String menuClass = "x-menu-floating";

    public AdvancedTextField() {
        setClassName("Menu");
    }

    public AdvancedTextField(String cls) {
        this();
        setCls(cls);
    }

    public AdvancedTextField(ExtJsComponent container) {
        this();
        setContainer(container);
    }

    public AdvancedTextField(ExtJsComponent container, String label) {
        this();
        setContainer(container);
        setLabel(label);
    }

    public AdvancedTextField(String cls, ExtJsComponent container) {
        this();
        setContainer(container);
        setCls(cls);
    }

    public boolean select(String value) {
        logger.debug("select: " + value);
        boolean found = false;
        if(clickIcon("arrow")){
            Utils.sleep(500);
            //TODO move this in a menu class
            String stylePath ="";
            if(!isIE()){
                stylePath = " and contains(@style,'visible')";
            }
            String valuePath = "//*[contains(@class, '" + menuClass + "')" + stylePath + "]//*[text()='" + value + "']";
            WebLocator iconLocator = new WebLocator();
            iconLocator.setElPath(valuePath);
            found = iconLocator.clickAt();
            if (!found) {
                logger.error("The option " + value + " could not be located.");
            }
        }
        return found;
    }
}
