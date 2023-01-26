package com.sdl.unit.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.table.Table;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CellTest {
    private static final Table table = new Table().setId("ID");
    private static final Row tableRow = new Row(table, "Text", SearchType.EQUALS);
    private static final WebLocator iconEl = new WebLocator().setClasses("fa-icon");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Cell(), "//td"},
                {new Cell(tableRow, 1), "//table[@id='ID']//tr[(.='Text' or count(*//text()[.='Text']) > 0)]//td[1]"},
                {new Cell(3, iconEl), "//td[3][count(.//*[contains(concat(' ', @class, ' '), ' fa-icon ')]) > 0]"},
                {new Cell(3, "Text", SearchType.EQUALS), "//td[3][(.='Text' or count(*//text()[.='Text']) > 0)]"},
                {new Cell(3, "Text", SearchType.CONTAINS), "//td[3][(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]"},
                {new Cell(tableRow, 3, "Text", SearchType.EQUALS), "//table[@id='ID']//tr[(.='Text' or count(*//text()[.='Text']) > 0)]//td[3][(.='Text' or count(*//text()[.='Text']) > 0)]"},
                {new Cell(1, "Text", SearchType.DEEP_CHILD_NODE).setTag("th"), "//th[1][count(*//text()[contains(.,'Text')]) > 0]"},
                {new Cell(1, "Text", SearchType.DEEP_CHILD_NODE).setTag("td"), "//td[1][count(*//text()[contains(.,'Text')]) > 0]"},
                {new Cell(1, "Text", SearchType.DEEP_CHILD_NODE, SearchType.EQUALS), "//td[1][count(*//text()[.='Text']) > 0]"},
                {new Cell(1, "Text", SearchType.DEEP_CHILD_NODE, SearchType.EQUALS, SearchType.CASE_INSENSITIVE), "//td[1][count(*//text()[translate(.,'TEXT','text')='text']) > 0]"},
                {new Cell("Text", SearchType.EQUALS), "//td[(.='Text' or count(*//text()[.='Text']) > 0)]"},
                {new Cell("Header", "Cell", SearchType.EQUALS), "//td[count(//th[text()='Header']/preceding-sibling::*) + number(boolean(//th[text()='Header']/preceding-sibling::*))][(.='Cell' or count(*//text()[.='Cell']) > 0)]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Cell cell, String expectedXpath) {
        assertThat(cell.getXPath(), equalTo(expectedXpath));
    }
}