package com.sdl.weblocator.pageObject;

import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SimplePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePage.class);

    WebDriver driver = null;


    public SimplePage(WebDriver driver) {
        this.driver = driver;
    }

    public void complete() {
        driver.findElement(By.id("open")).click();
//        driver.findElement(By.id("myModal")).isDisplayed();
        Utils.sleep(1000);

        driver.findElement(By.id("email")).sendKeys("culeaviorel@gmail.com");
        driver.findElement(By.id("userName")).sendKeys("Culea Viorel");


        WebElement webElement = driver.findElement(By.id("email"));

        webElement.sendKeys("culeaviorel@gmail.com2");

        save();

       // driver.findElement(By.id("open")).click();
        Utils.sleep(1000);

        webElement.sendKeys("culeaviorel@gmail.com3");

        webElement.sendKeys("culeaviorel@gmail.com4");

        webElement.sendKeys("culeaviorel@gmail.com5");
    }

    public void save() {
        driver.findElement(By.id("saveButton")).click();
        driver.findElement(By.id("close")).click();
        Utils.sleep(100);
    }
}
