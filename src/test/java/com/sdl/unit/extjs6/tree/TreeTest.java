package com.sdl.unit.extjs6.tree;

import com.sdl.selenium.extjs6.tree.Tree;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TreeTest  {

   public static WebLocator container = new WebLocator("container");

   @DataProvider
   public static Object[][] testConstructorPathDataProvider() {
       return new Object[][]{
           {new Tree(), "//*[contains(concat(' ', @class, ' '), ' x-tree-panel ')]"},
           {new Tree().setClasses("cls"), "//*[contains(concat(' ', @class, ' '), ' x-tree-panel ') and contains(concat(' ', @class, ' '), ' cls ')]"},
           {new Tree(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-tree-panel ')]"},
       };
   }

   @Test(dataProvider = "testConstructorPathDataProvider")
   public void getPathSelectorCorrectlyFromConstructors(Tree tree, String expectedXpath) {
       assertThat(tree.getXPath(), equalTo(expectedXpath));
   }
}
