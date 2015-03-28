package com.sdl.weblocator.pageObject;

import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WritePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(WritePage.class);

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

        Utils.sleep(1000);
        email.sendKeys("culeaviorel@gmail.com");
        userName.sendKeys("Culea Viorel");

//        save();
//        open.click();
//        email.sendKeys("culeaviorel@gmail.com");
//        userName.sendKeys("Culea Viorel");
    }

    public void save() {
        saveButton.click();
        close.click();
        //Utils.sleep(100);
    }
}
