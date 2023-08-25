package com.sdl.unit.materialui.menu;

import com.sdl.selenium.materialui.menu.Menu;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MenuTest  {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new Menu(), "//*[contains(concat(' ', @class, ' '), ' MuiMenu-root ') and contains(concat(' ', @class, ' '), ' MuiPopover-root ')]"},
           {new Menu().setClasses("cls"), "//*[contains(concat(' ', @class, ' '), ' MuiMenu-root ') and contains(concat(' ', @class, ' '), ' cls ')]"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(Menu menu, String expectedXpath) {
       assertThat(menu.getXPath(), equalTo(expectedXpath));
   }
}
