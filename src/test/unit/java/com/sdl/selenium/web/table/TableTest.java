package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableTest {
    public static WebLocator container = new WebLocator("container");
    private static TableCell tableCell = new TableCell(3, "1234", SearchType.EQUALS).setTag("th");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Table(), "//table"},
                {new Table(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//table"},
                {new Table().setId("ID"), "//table[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Table table, String expectedXpath) {
        Assert.assertEquals(table.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {new Table().getRowLocator(1), "//table//tr[position() = 1]"},
                {new Table().getTableRow("SearchElement"), "//table//tr[(text()='SearchElement' or count(*//text()[.='SearchElement']) > 0)]"},
                {new Table().getTableRow("SearchElement", SearchType.CONTAINS), "//table//tr[(contains(text(),'SearchElement') or count(*//text()[contains(.,'SearchElement')]) > 0)]"},
                {new Table().getTableRow("SearchElement", SearchType.STARTS_WITH), "//table//tr[(starts-with(text(),'SearchElement') or count(*//text()[starts-with(.,'SearchElement')]) > 0)]"},
                {new Table().getTableRow("/SearchElement/text", SearchType.CONTAINS_ALL), "//table//tr[(contains(text(),'SearchElement') and contains(text(),'text') or count(*//text()[contains(.,'SearchElement') and contains(.,'text')]) > 0)]"},
                {new Table().getTableRow("|SearchElement|text", SearchType.CONTAINS_ANY), "//table//tr[((contains(text(),'SearchElement') or contains(text(),'text')) or count(*//text()[(contains(.,'SearchElement') or contains(.,'text'))]) > 0)]"},

                {new Table().getCell(1, 1), "//table//tr[position() = 1]//td[1]"},
                {new Table().getTableCell("SearchElement", SearchType.EQUALS), "//table//tr//td[(text()='SearchElement' or count(*//text()[.='SearchElement']) > 0)]"},
                {new Table().getTableCell("SearchElement", SearchType.CONTAINS), "//table//tr//td[(contains(text(),'SearchElement') or count(*//text()[contains(.,'SearchElement')]) > 0)]"},
                {new Table().getTableCell("SearchElement", SearchType.STARTS_WITH), "//table//tr//td[(starts-with(text(),'SearchElement') or count(*//text()[starts-with(.,'SearchElement')]) > 0)]"},
                {new Table().getTableCell(1, 1, "SearchElement"), "//table//tr[position() = 1]//td[(text()='SearchElement' or count(*//text()[.='SearchElement']) > 0)][1]"},

                {new Table().getTableCell("SearchElement", "text", SearchType.EQUALS), "//table//tr[(contains(text(),'SearchElement') or count(*//text()[contains(.,'SearchElement')]) > 0)]//td[(text()='text' or count(*//text()[.='text']) > 0)]"},
                {new Table().getTableCell("SearchElement", 2, SearchType.EQUALS), "//table//tr[(text()='SearchElement' or count(*//text()[.='SearchElement']) > 0)]//td[2]"},

                {new Table().getRow(new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(td[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]"},
                {new Table().getTableCell(1, new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(td[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]//td[1]"},
                {new Table().getTableCell(1, new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)).setVisibility(true), "//table//tr[count(td[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]//td[1][count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Table().getTableCell(1, "111", new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(td[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]//td[1][(text()='111' or count(*//text()[.='111']) > 0)]"},
                {new Table().getRow(tableCell, new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(th[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(WebLocator simpleTable, String expectedXpath) {
        Assert.assertEquals(simpleTable.getPath(), expectedXpath);
    }
}
