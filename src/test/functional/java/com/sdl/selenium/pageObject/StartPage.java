package com.sdl.selenium.pageObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StartPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartPage.class);

    @FindBy(how = How.ID, using = "open")
    private WebElement open;


    public void open() {
        open.click();
    }
}
