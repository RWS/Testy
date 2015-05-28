package com.sdl.selenium.extjs3.slider;


import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO this class is too specific to BeBlobal slider only
public class Slider extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slider.class);

    public Slider() {
        setClassName("Slider");
        //LOGGER.debug(getClassName() + "() constructor");
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
        WebLocator element = new WebLocator().setElPath(thumbPath);
        if (thumbPath != null && element.exists()) {
            // to scroll to this element (if element is not visible)
            WebDriver driver = WebDriverConfig.getDriver();
            WebElement thumbElement = driver.findElement(By.xpath(thumbPath));
            element.sendKeys(Keys.TAB);
            element.click();
            new Actions(driver).dragAndDropBy(thumbElement, distance, 1).build().perform();
        } else {
            LOGGER.warn("The slider for " + getPathBuilder().getLabel() + " has not been selected or is missing");
            exists = false;
        }
        return exists;
    }
}
