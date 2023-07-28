package com.sdl.unit.materialui.list;

import com.sdl.selenium.materialui.list.List;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ListTest  {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new List(), "//ul"},
           {new List().setClasses("cls"), "//ul[contains(concat(' ', @class, ' '), ' cls ')]"},
           {new List(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//ul"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(List list, String expectedXpath) {
       assertThat(list.getXPath(), equalTo(expectedXpath));
   }
}
