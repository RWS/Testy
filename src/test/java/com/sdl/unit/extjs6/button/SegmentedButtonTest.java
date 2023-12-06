package com.sdl.unit.extjs6.button;

import com.sdl.selenium.extjs6.button.SegmentedButton;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SegmentedButtonTest  {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new SegmentedButton(), "//*[contains(concat(' ', @class, ' '), ' x-segmented-button ')]"},
           {new SegmentedButton().setClasses("cls"), "//*[contains(concat(' ', @class, ' '), ' x-segmented-button ') and contains(concat(' ', @class, ' '), ' cls ')]"},
           {new SegmentedButton(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-segmented-button ')]"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(SegmentedButton segmentedButton, String expectedXpath) {
       assertThat(segmentedButton.getXPath(), equalTo(expectedXpath));
   }
}
