package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;

public class SelectFilesHandlerTest {
    public static WebLocator container = new WebLocator().setElPath("//*[contains(concat(' ', @class, ' '), ' container ')]");

//    @DataProvider
//    public static Object[][] testConstructorPathDataProvider() {
//        return new Object[][]{
//                {new SelectFilesHandler(),          "//*"},
//                {new SelectFilesHandler(container), "//*"},
//        };
//    }

//    @Test(dataProvider = "testConstructorPathDataProvider")
//    public void getPathSelectorCorrectlyFromConstructors(SelectFilesHandler selectFilesHandler, String expectedXpath) {
////        Assert.assertEquals(selectFilesHandler.getPath(), expectedXpath);
//    }
//
//    @Test
//    public void getPathSelectorCorrectlySetId() {
//        //Assert.assertEquals(new SelectFilesHandler().setButtonElement(container).getPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]");
//    }
}
