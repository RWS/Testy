package com.sdl.weblocator.pageObject;

import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WritePage {
    private static final Logger logger = Logger.getLogger(WritePage.class);

    @FindBy(how = How.ID, using = "open")
    private WebElement open;

    @FindBy(how = How.ID, using = "email")
    private WebElement email;

    @FindBy(how = How.ID, using = "userName")
    private WebElement userName;

    @FindBy(how = How.ID, using = "saveButton")
    private WebElement saveButton;

    @FindBy(how = How.ID, using = "close")
    private WebElement close;

//    @FindBy(how = How.ID, using = "myModal")
//    private WebElement myModal;

    public void complete() {
        open.click();
//        myModal.isDisplayed();
        Utils.sleep(1000);
        email.sendKeys("culeaviorel@gmail.com");
        userName.sendKeys("Culea Viorel");
    }

    public void save() {
        saveButton.click();
        close.click();
//        Utils.sleep(100);
    }
}
