package com.sdl.unit.web.button;

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
////        assertThat(selectFilesHandler.getXPath(), equalTo(expectedXpath));
//    }
//
//    @Test
//    public void getPathSelectorCorrectlySetId() {
//        //assertThat(new SelectFilesHandler().setButtonElement(container).getXPath(), "//*[contains(concat(' ', @class, ' '), ' container ')]");
//    }
}
