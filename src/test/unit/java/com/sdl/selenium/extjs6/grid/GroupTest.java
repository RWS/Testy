package com.sdl.selenium.extjs6.grid;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class GroupTest {
    public static Grid container = new Grid();

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Grid().getGroup(3),                    "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ')]) > 0][position() = 3]"},
                {new Grid().getGroup("Cuisine: Coffee"),    "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ') and count(*//text()[contains(.,'Cuisine: Coffee')]) > 0]) > 0]"},
                {new Group(),                               "//table[count(.//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ')]) > 0]"},
                {new Group(container),                      "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ')]) > 0]"},
                {new Group(container, 2),                   "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ')]) > 0][position() = 2]"},
                {new Group(container, "Cuisine: Coffee"),   "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ') and count(*//text()[contains(.,'Cuisine: Coffee')]) > 0]) > 0]"},
                {new Group(container, 2, new Cell("Text")), "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]) > 0][position() = 2]"},
                {new Group(container, "Cuisine: Coffee", new Cell("Starbucks")),     "(//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ') and count(*//text()[contains(.,'Cuisine: Coffee')]) > 0]) > 0])[count(.//td[(contains(.,'Starbucks') or count(*//text()[contains(.,'Starbucks')]) > 0)]) > 0]"},
                {new Group(container, "Cuisine: Coffee", 2),"(//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[contains(concat(' ', @class, ' '), ' x-group-hd-container ') and count(*//text()[contains(.,'Cuisine: Coffee')]) > 0]) > 0])[2]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Group row, String expectedXpath) {
        assertThat(row.getXPath(), equalTo(expectedXpath));
    }
}
