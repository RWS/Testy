package com.sdl.unit.materialui.form;

import com.sdl.selenium.materialui.form.ComboBox;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ComboBoxTest  {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new ComboBox(), "//*[contains(concat(' ', @class, ' '), ' MuiSelect-select ')]"},
           {new ComboBox().setClasses("cls"), "//*[contains(concat(' ', @class, ' '), ' MuiSelect-select ') and contains(concat(' ', @class, ' '), ' cls ')]"},
           {new ComboBox(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' MuiSelect-select ')]"},
           {new ComboBox(container, "Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Text']//following-sibling::*//*[contains(concat(' ', @class, ' '), ' MuiSelect-select ')]"},
           {new ComboBox(container, "Text", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'Text')]//following-sibling::*//*[contains(concat(' ', @class, ' '), ' MuiSelect-select ')]"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(ComboBox combobox, String expectedXpath) {
       assertThat(combobox.getXPath(), equalTo(expectedXpath));
   }
}
