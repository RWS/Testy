package com.extjs.selenium.slider;


import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.apache.log4j.Logger;

// TODO this class is too specific to GeBlobal slider only
public class Slider extends ExtJsComponent {
    private static final Logger logger = Logger.getLogger(Slider.class);

    public Slider() {
        setClassName("Slider");
        //logger.debug(getClassName() + "() constructor");
    }

    /**
     * Constructor basen on ExtJsComponent
     *
     * @param container
     */
    public Slider(ExtJsComponent container) {
        this();
        setContainer(container);
    }

    public Slider(ExtJsComponent container, String label) {
        this(container);
        setLabel(label);
    }


    public String getThumbPath(int thumbIndex) {
        return getPath() + "//descendant::*//*[contains(@class,'x-slider-thumb')][" + thumbIndex + "]";
    }

    public boolean move(int thumbIndex, int distance) {
        boolean exists = true;
        String thumbPath = getThumbPath(thumbIndex);
        WebLocator element = new WebLocator(null, thumbPath);
        if (thumbPath != null && element.exists()) {
            // to scroll to this element (if element is not visible)
            if (hasWebDriver()) {
                WebElement thumbElement = driver.findElement(By.xpath(thumbPath));
                element.sendKeys(Keys.TAB);
                element.click();
                new Actions(driver).dragAndDropBy(thumbElement, distance, 1).build().perform();
            } else {
                selenium.focus(thumbPath);
                selenium.dragAndDrop(thumbPath, "" + distance + ",0");
            }
        } else {
            logger.warn("The slider for " + getLabel() + " has not been selected or is missing");
            exists = false;
        }
        return exists;
    }
}
