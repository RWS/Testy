package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CellTest {
    public static WebLocator container = new WebLocator("container");
    private static final WebLocator iconEl = new WebLocator().setClasses("fa-icon");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Cell(1, "Text"),                                "//td[1][(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]"},
                {new Grid().getCell(1, 2),                                "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[position() = 1]//td[2]"},
                {new Grid().getCell("Test"),                                     "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table//td[(.='Test' or count(*//text()[.='Test']) > 0)]"},
                {new Grid().getCell("Test", SearchType.DEEP_CHILD_NODE_OR_SELF), "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table//td[(contains(.,'Test') or count(*//text()[contains(.,'Test')]) > 0)]"},
                {new Grid().getCell("Test", SearchType.HTML_NODE),               "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table//td[contains(.,'Test')]"},
                {new Grid().getCell("Test", SearchType.HTML_NODE, SearchType.STARTS_WITH),"//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table//td[starts-with(.,'Test')]"},
                {new Grid().getCell(1, 2, "Test"),                             "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[position() = 1]//td[2][(.='Test' or count(*//text()[.='Test']) > 0)]"},
                {new Grid().getCell("Text", "ColumnText", SearchType.CONTAINS),  "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]//td[(contains(.,'ColumnText') or count(*//text()[contains(.,'ColumnText')]) > 0)]"},
                {new Grid().getCell("Text", 2, SearchType.CONTAINS),            "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]//td[2]"},
                {new Grid().getCell(1, new Cell(2, "Text")),            "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[2][(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]) > 0]//td[1]"},
                {new Grid().getCell(1, new Cell("Text")),                         "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]) > 0]//td[1]"},
                {new Grid().getCell(1, "Test", new Cell(2, "Text")),"//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[2][(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]) > 0]//td[1][(.='Test' or count(*//text()[.='Test']) > 0)]"},
                {new Grid().getRow(new Cell("Header", "Text")).getCell(1),     "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[count(ancestor::*/*[contains(concat(' ', @class, ' '), ' x-grid-header-ct ')]//*[contains(concat(' ', @class, ' '), ' x-column-header ') and count(*//text()[.='Header']) > 0]/preceding-sibling::*[@aria-hidden='false']) + number(boolean(ancestor::*/*[contains(concat(' ', @class, ' '), ' x-grid-header-ct ')]//*[contains(concat(' ', @class, ' '), ' x-column-header ') and count(*//text()[.='Header']) > 0]/preceding-sibling::*[@aria-hidden='false']))][(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]) > 0]//td[1]"},
                {new Grid().getRow(new Cell("Header", "")).getCell(1),         "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td) > 0]//td[1]"},
                {new Grid().getRow(new Cell(1, iconEl)).getCell(2),         "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[1][count(.//*[contains(concat(' ', @class, ' '), ' fa-icon ')]) > 0]) > 0]//td[2]"},
                {new Grid(container).getRow(1).getCell(2),                          "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[position() = 1]//td[2]"},
                {new Grid().getCell(1, new Cell(1, "&Text&Test", SearchType.CONTAINS_ALL_CHILD_NODES)), "//*[contains(concat(' ', @class, ' '), ' x-grid ')]//table[count(.//td[1][count(*//text()[contains(.,'Text')]) > 0 and count(*//text()[contains(.,'Test')]) > 0]) > 0]//td[1]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Cell cell, String expectedXpath) {
        assertThat(cell.getXPath(), equalTo(expectedXpath));
    }
}
