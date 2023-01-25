package com.sdl.unit.materialui.button;

import com.sdl.selenium.materialui.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ButtonTest  {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new Button(), "//button[@type='button']"},
           {new Button().setClasses("cls"), "//button[contains(concat(' ', @class, ' '), ' cls ') and @type='button']"},
           {new Button(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[@type='button']"},
           {new Button(container, "Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[@type='button' and text()='Text']"},
           {new Button(container, "Text", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//button[@type='button' and contains(text(),'Text')]"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
       assertThat(button.getXPath(), equalTo(expectedXpath));
   }
}
