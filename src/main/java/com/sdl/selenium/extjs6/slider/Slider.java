package com.sdl.selenium.extjs6.slider;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.interactions.Actions;

@Slf4j
public class Slider extends WebLocator {

    public Slider() {
        setClassName("Slider");
        setBaseCls("x-slider");
    }

    public Slider(WebLocator container) {
        this();
        setContainer(container);
    }

    public Slider(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public int getValue() {
        return Integer.parseInt(getAttribute("aria-valuenow"));
    }

    public boolean move(int distance) {
        boolean exists = true;
        WebLocator element = new WebLocator(this).setTag("descendant::*").setClasses("x-slider-thumb");
        if (element.ready()) {
            element.mouseOver();
            boolean done = false;
            boolean plusOneValue = false;
            boolean minusOneValue = false;
            int distanceTemp = distance;
            do {
                boolean vertical = getAttributeClass().contains("x-slider-vert");
                int value = getValue();
                log.debug("distance: {}, value: {}", distance, value);
                if (value + 1 == distance) {
                    plusOneValue = true;
                    minusOneValue = false;
                    distanceTemp = 0;
                } else if (value - 1 == distance) {
                    minusOneValue = true;
                    plusOneValue = false;
                    distanceTemp = 0;
                } else if (value > distance) {
                    plusOneValue = false;
                    minusOneValue = false;
                    if (vertical) {
                        distanceTemp = value - distance;
                    } else {
                        distanceTemp = -1 * (value - distance);
                    }
                } else if (value < distance) {
                    plusOneValue = false;
                    minusOneValue = false;
                    if (vertical) {
                        distanceTemp = -1 * (distance - value);
                    } else {
                        distanceTemp = distance - value;
                    }
                } else {
                    done = true;
                }
                if (!done) {
                    if (vertical) {
                        distanceTemp = distanceTemp * 2 + 4;
                    } else {
                        if (distanceTemp == 1) {
                            distanceTemp = distanceTemp + 3;
                        } else if (distanceTemp == 2) {
                            distanceTemp = distanceTemp + 2;
                        } else if (distanceTemp == -1) {
                            distanceTemp = distanceTemp - 3;
                        } else if (distanceTemp == -2) {
                            distanceTemp = distanceTemp - 2;
                        } else if (plusOneValue) {
                            if (distance > 95 || distance < 5) {
                                if (distance % 2 == 0) {
                                    distanceTemp = 9;
                                } else {
                                    distanceTemp = 5;
                                }
                            } else {
                                distanceTemp = 4;
                            }
                        } else if (minusOneValue) {
                            if (distance > 95 || distance < 5) {
                                if (distance % 2 == 0) {
                                    distanceTemp = -9;
                                } else {
                                    distanceTemp = -4;
                                }
                            } else {
                                distanceTemp = -6;
                            }
                        } else if (distanceTemp < 0) {
                            distanceTemp = distanceTemp * 2 - 4;
                        } else {
                            distanceTemp = distanceTemp * 2 + 2;
                        }
                    }
                    if (vertical) {
                        new Actions(WebDriverConfig.getDriver()).dragAndDropBy(element.getWebElement(), 1, distanceTemp).perform();
                    } else {
                        new Actions(WebDriverConfig.getDriver()).dragAndDropBy(element.getWebElement(), distanceTemp, 1).perform();
                    }
                    element.mouseOver();
                }
            } while (!done);
        } else {
            log.warn("The slider for " + getPathBuilder().getLabel() + " has not been selected or is missing");
            exists = false;
        }
        return exists;
    }
}