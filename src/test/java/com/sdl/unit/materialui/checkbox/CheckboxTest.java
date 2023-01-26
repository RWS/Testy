package com.sdl.unit.materialui.checkbox;

import com.sdl.selenium.materialui.checkbox.Checkbox;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CheckboxTest  {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new Checkbox(), "//input[@type='checkbox']"},
           {new Checkbox().setClasses("cls"), "//input[contains(concat(' ', @class, ' '), ' cls ') and @type='checkbox']"},
           {new Checkbox(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
           {new Checkbox(container, "Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//span[text()='Text']/preceding-sibling::*//input[@type='checkbox']"},
           {new Checkbox(container, "Text", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//span[contains(text(),'Text')]/preceding-sibling::*//input[@type='checkbox']"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(Checkbox checkbox, String expectedXpath) {
       assertThat(checkbox.getXPath(), equalTo(expectedXpath));
   }
}
