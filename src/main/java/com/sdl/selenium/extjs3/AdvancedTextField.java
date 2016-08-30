package com.sdl.selenium.extjs3;

import com.sdl.selenium.extjs3.form.TextField;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO extend other class not textfield
public class AdvancedTextField extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdvancedTextField.class);
    
    private static String menuClass = "x-menu-floating";

    public AdvancedTextField() {
        withClassName("Menu");
    }

    public AdvancedTextField(String cls) {
        this();
        withClasses(cls);
    }

    public AdvancedTextField(WebLocator container) {
        this();
        withContainer(container);
    }

    public AdvancedTextField(WebLocator container, String label) {
        this();
        withContainer(container);
        withLabel(label);
    }

    public AdvancedTextField(String cls, WebLocator container) {
        this();
        withContainer(container);
        withClasses(cls);
    }

    public boolean select(String value) {
        LOGGER.debug("select: " + value);
        boolean found = false;
        if(clickIcon("arrow")){
            Utils.sleep(500);
            //TODO move this in a menu class
            String stylePath = "";
            if(!WebDriverConfig.isIE()){
                stylePath = " and contains(@style,'visible')";
            }
            String valuePath = "//*[contains(@class, '" + menuClass + "')" + stylePath + "]//*[text()='" + value + "']";
            WebLocator iconLocator = new WebLocator();
            iconLocator.withElxPath(valuePath);
            found = iconLocator.clickAt();
            if (!found) {
                LOGGER.error("The option " + value + " could not be located.");
            }
        }
        return found;
    }
}
