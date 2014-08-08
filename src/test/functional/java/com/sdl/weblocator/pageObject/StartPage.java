package com.sdl.weblocator.pageObject;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StartPage {
    private static final Logger logger = Logger.getLogger(StartPage.class);

    @FindBy(how = How.ID, using = "open")
    private WebElement open;


    public void open() {
        open.click();
    }
}
