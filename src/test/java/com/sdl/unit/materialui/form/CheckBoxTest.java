package com.sdl.unit.materialui.form;

import com.sdl.selenium.materialui.form.CheckBox;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CheckBoxTest {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new CheckBox(), "//input[@type='checkbox']"},
           {new CheckBox().setClasses("cls"), "//input[contains(concat(' ', @class, ' '), ' cls ') and @type='checkbox']"},
           {new CheckBox(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
           {new CheckBox(container, "Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//span[text()='Text']/preceding-sibling::*//input[@type='checkbox']"},
           {new CheckBox(container, "Text", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//span[contains(text(),'Text')]/preceding-sibling::*//input[@type='checkbox']"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(CheckBox checkbox, String expectedXpath) {
       assertThat(checkbox.getXPath(), equalTo(expectedXpath));
   }
}
