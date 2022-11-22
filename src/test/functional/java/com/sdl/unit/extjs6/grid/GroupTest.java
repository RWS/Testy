package com.sdl.unit.extjs6.grid;

import com.sdl.selenium.extjs6.grid.Grid;
import com.sdl.selenium.extjs6.grid.Group;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class GroupTest {
    public static Grid container = new Grid();

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Grid().getGroup(3),                    "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//*[contains(concat(' ', @class, ' '), ' x-grid-group-title ')]) > 0][position() = 3]"},
                {new Grid().getGroup("Cuisine: Coffee"),    "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//*[contains(concat(' ', @class, ' '), ' x-grid-group-title ') and contains(.,'Cuisine: Coffee')]) > 0]"},
                {new Group(),                               "//table[count(.//*[contains(concat(' ', @class, ' '), ' x-grid-group-title ')]) > 0]"},
                {new Group(container),                      "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//*[contains(concat(' ', @class, ' '), ' x-grid-group-title ')]) > 0]"},
                {new Group(container, 2),                   "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//*[contains(concat(' ', @class, ' '), ' x-grid-group-title ')]) > 0][position() = 2]"},
                {new Group(container, "Cuisine: Coffee"),   "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//*[contains(concat(' ', @class, ' '), ' x-grid-group-title ') and contains(.,'Cuisine: Coffee')]) > 0]"},
                {new Group(container, "Cuisine: Coffee", 2),"(//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//*[contains(concat(' ', @class, ' '), ' x-grid-group-title ') and contains(.,'Cuisine: Coffee')]) > 0])[2]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Group group, String expectedXpath) {
        assertThat(group.getXPath(), equalTo(expectedXpath));
    }
}
