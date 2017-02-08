package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableTest {
    private static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Table(), "//table"},
                {new Table(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//table"},
                {new Table().withId("ID"), "//table[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Table table, String expectedXpath) {
        Assert.assertEquals(table.getXPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProviderCell() {
        return new Object[][]{
                {new Table().getCell(1, 1), "//table//tr[position() = 1]//td[1]"},
                {new Table().getCell("SearchElement", SearchType.EQUALS), "//table//tr//td[(.='SearchElement' or count(*//text()[.='SearchElement']) > 0)]"},
                {new Table().getCell("SearchElement", SearchType.CONTAINS), "//table//tr//td[(contains(.,'SearchElement') or count(*//text()[contains(.,'SearchElement')]) > 0)]"},
                {new Table().getCell("SearchElement", SearchType.STARTS_WITH), "//table//tr//td[(starts-with(.,'SearchElement') or count(*//text()[starts-with(.,'SearchElement')]) > 0)]"},

                {new Table().getCell(1, new Cell(3, "1234", SearchType.EQUALS), new Cell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(.//td[3][(.='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(.='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]//td[1]"},
                {new Table().getCell(1, new Cell(3, "1234", SearchType.EQUALS), new Cell(4, "Eng-Fra", SearchType.EQUALS)).withVisibility(true), "//table//tr[count(.//td[3][(.='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(.='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]//td[1][count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Table().getCell(1, "111", new Cell(3, "1234", SearchType.EQUALS), new Cell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(.//td[3][(.='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(.='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]//td[1][(.='111' or count(*//text()[.='111']) > 0)]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProviderCell")
    public void getPathSelectorCorrectlyFromConstructorsCell(Cell cell, String expectedXpath) {
        Assert.assertEquals(cell.getXPath(), expectedXpath);
    }
}
