package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class GridTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Grid(), "//*[contains(concat(' ', @class, ' '), ' x-grid ')]"},
                {new Grid().setClasses("GridClass"), "//*[contains(concat(' ', @class, ' '), ' x-grid ') and contains(concat(' ', @class, ' '), ' GridClass ')]"},
                {new Grid(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-grid ')]"},
                {new Grid(container).setElPath("//table//tr[1]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new Grid().setTitle("Array Grid"), "//*[contains(concat(' ', @class, ' '), ' x-grid ') and count(.//*[contains(concat(' ', @class, ' '), ' x-title ')]//*[contains(.,'Array Grid')]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Grid grid, String expectedXpath) {
        assertThat(grid.getXPath(), equalTo(expectedXpath));
    }
}
