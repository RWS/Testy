package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CellTest {
    private static Table table = new Table().setId("ID");
    private static Row tableRow = new Row(table, "Text", SearchType.EQUALS);

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Cell(),                       "//td"},
                {new Cell(tableRow, 1),            "//table[@id='ID']//tr[(.='Text' or count(*//text()[.='Text']) > 0)]//td[1]"},
                {new Cell(3, "Text", SearchType.EQUALS),        "//td[3][(.='Text' or count(*//text()[.='Text']) > 0)]"},
                {new Cell(3, "Text", SearchType.CONTAINS),  "//td[3][(contains(.,'Text') or count(*//text()[contains(.,'Text')]) > 0)]"},
                {new Cell(tableRow, 3, "Text", SearchType.EQUALS), "//table[@id='ID']//tr[(.='Text' or count(*//text()[.='Text']) > 0)]//td[3][(.='Text' or count(*//text()[.='Text']) > 0)]"},
                {new Cell(1, "Text", SearchType.DEEP_CHILD_NODE).setTag("th"), "//th[1][count(*//text()[contains(.,'Text')]) > 0]"},
                {new Cell(1, "Text", SearchType.DEEP_CHILD_NODE).setTag("td"), "//td[1][count(*//text()[contains(.,'Text')]) > 0]"},
                {new Cell(1, "Text", SearchType.DEEP_CHILD_NODE, SearchType.EQUALS), "//td[1][count(*//text()[.='Text']) > 0]"},
                {new Cell(1, "Text", SearchType.DEEP_CHILD_NODE, SearchType.EQUALS, SearchType.CASE_INSENSITIVE), "//td[1][count(*//text()[translate(.,'TEXT','text')='text']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Cell cell, String expectedXpath) {
        Assert.assertEquals(cell.getXPath(), expectedXpath);
    }
}
