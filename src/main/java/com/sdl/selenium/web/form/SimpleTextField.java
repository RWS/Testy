package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class SimpleTextField extends WebLocator {
    private static final Logger logger = Logger.getLogger(SimpleTextField.class);

    public SimpleTextField() {
        setClassName("SimpleTextField");
        setTag("input");
    }

    public SimpleTextField(String id) {
        this();
        setId(id);
    }

    public boolean setValue(String value) {
        if (ready()) {
            if (value != null) {
                logger.info("Setting value(" + toString() + "): '" + value + "'");
                if (hasWebDriver()) {
                    currentElement.clear();
                    currentElement.sendKeys(value);
                    return true;
                } else {
                    String path = getPath();
                    selenium.focus(path); // to scroll to this element (if element is not visible)
                    selenium.type(path, value);
                    selenium.keyUp(path, value.substring(value.length() - 1));
                    selenium.fireEvent(path, "blur");
                    return true;
                }
            }
        } else {
            logger.warn("setValue : field is not ready for use: " + toString());
        }
        return false;
    }


    /**
     * getValue using xPath
     *
     * @return
     */
    public String getValue() {
        String value = "";
        if (ready()) {
            // using WebDriver -> there are situations when the value is taken by getText() or getAttribute("value")
            if (hasWebDriver()) {
                if (!currentElement.getAttribute("value").equals("") && currentElement.getText().equals("")) {
                    value = currentElement.getAttribute("value");
                } else if (currentElement.getAttribute("value").equals("") && !currentElement.getText().equals("")) {
                    value = currentElement.getText();
                } else if (!currentElement.getAttribute("value").equals("") && !currentElement.getText().equals("") && currentElement.getAttribute("value").equals(currentElement.getText())) {
                    value = currentElement.getText();
                } else if (!currentElement.getAttribute("value").equals("") && !currentElement.getText().equals("") && !currentElement.getAttribute("value").equals(currentElement.getText())) {
                    logger.debug("Not sure what value to use: \ncurrentElement.getText()= " + currentElement.getText() + "\ncurrentElement.getAttribute(\"value\"): " + currentElement.getAttribute("value"));
                }
            } else {
                value = selenium.getValue(getPath());
            }
        } else {
            logger.warn("getValue : field is not ready for use: " + toString());
        }
        return value;
    }
}