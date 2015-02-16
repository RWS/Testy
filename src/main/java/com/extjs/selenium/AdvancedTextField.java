package com.extjs.selenium;

import com.extjs.selenium.form.TextField;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO extend other class not textfield
public class AdvancedTextField extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdvancedTextField.class);
    
    private static String menuClass = "x-menu-floating";

    public AdvancedTextField() {
        setClassName("Menu");
    }

    public AdvancedTextField(String cls) {
        this();
        setClasses(cls);
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
        setClasses(cls);
    }

    public boolean select(String value) {
        LOGGER.debug("select: " + value);
        boolean found = false;
        if(clickIcon("arrow")){
            Utils.sleep(500);
            //TODO move this in a menu class
            String stylePath ="";
            if(!WebDriverConfig.isIE()){
                stylePath = " and contains(@style,'visible')";
            }
            String valuePath = "//*[contains(@class, '" + menuClass + "')" + stylePath + "]//*[text()='" + value + "']";
            WebLocator iconLocator = new WebLocator();
            iconLocator.setElPath(valuePath);
            found = iconLocator.clickAt();
            if (!found) {
                LOGGER.error("The option " + value + " could not be located.");
            }
        }
        return found;
    }
}
