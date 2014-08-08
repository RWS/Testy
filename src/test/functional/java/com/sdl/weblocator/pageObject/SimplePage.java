package com.sdl.weblocator.pageObject;

import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SimplePage {
    private static final Logger logger = Logger.getLogger(SimplePage.class);

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
    }

    public void save() {
        driver.findElement(By.id("saveButton")).click();
        driver.findElement(By.id("close")).click();
        Utils.sleep(100);
    }
}
