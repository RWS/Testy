package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Row;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RowTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Grid().getRow(1, new Cell(2, "Test")), "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[2][(contains(.,'Test') or count(*//text()[contains(.,'Test')]) > 0)]) > 0][position() = 1]"},
                {new Grid().getRow(new Cell(2, "Test")),    "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[2][(contains(.,'Test') or count(*//text()[contains(.,'Test')]) > 0)]) > 0]"},
                {new Grid(container).getRow(1),             "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[position() = 1]"},
                {new Grid(container).getRow("Text"),        "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[(.='Text' or count(*//text()[.='Text']) > 0)]"},
                {new Grid(container).getRow("Text", SearchType.STARTS_WITH),   "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[(starts-with(.,'Text') or count(*//text()[starts-with(.,'Text')]) > 0)]"},
                {new Grid(container).getRow(),              "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Row row, String expectedXpath) {
        assertThat(row.getXPath(), equalTo(expectedXpath));
    }
}
