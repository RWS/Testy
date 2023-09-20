package com.sdl.unit.materialui.form;

import com.sdl.selenium.materialui.form.TextArea;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TextAreaTest {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new TextArea(), "//textarea[contains(concat(' ', @class, ' '), ' MuiInputBase-inputMultiline ') and not(contains(@aria-hidden,'true'))]"},
           {new TextArea().setClasses("cls"), "//textarea[contains(concat(' ', @class, ' '), ' MuiInputBase-inputMultiline ') and contains(concat(' ', @class, ' '), ' cls ') and not(contains(@aria-hidden,'true'))]"},
           {new TextArea(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//textarea[contains(concat(' ', @class, ' '), ' MuiInputBase-inputMultiline ') and not(contains(@aria-hidden,'true'))]"},
           {new TextArea(container, "Text"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='Text']//following-sibling::*//textarea[contains(concat(' ', @class, ' '), ' MuiInputBase-inputMultiline ') and not(contains(@aria-hidden,'true'))]"},
           {new TextArea(container, "Text", SearchType.CONTAINS), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[contains(text(),'Text')]//following-sibling::*//textarea[contains(concat(' ', @class, ' '), ' MuiInputBase-inputMultiline ') and not(contains(@aria-hidden,'true'))]"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(TextArea textarea, String expectedXpath) {
       assertThat(textarea.getXPath(), equalTo(expectedXpath));
   }
}
