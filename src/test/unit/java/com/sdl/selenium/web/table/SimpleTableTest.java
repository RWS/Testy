package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleTableTest {
    public static WebLocator container = new WebLocator("container");
    private static TableCell tableCell = new TableCell(3, "1234", SearchType.EQUALS).setTag("th");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new SimpleTable(), "//table"},
                {new SimpleTable(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//table"},
                {new SimpleTable().setId("ID"), "//table[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(SimpleTable simpleTable, String expectedXpath) {
        Assert.assertEquals(simpleTable.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {new SimpleTable().getRowLocator(1), "//table//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 1]"},
                {new SimpleTable().getTableRow("SearchElement"), "//table//tr[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new SimpleTable().getTableRow("SearchElement", SearchType.CONTAINS), "//table//tr[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new SimpleTable().getTableRow("SearchElement", SearchType.STARTS_WITH), "//table//tr[starts-with(text(),'SearchElement') or count(.//*[starts-with(text(),'SearchElement')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new SimpleTable().getTableRow("/SearchElement/text", SearchType.CONTAINS_ALL), "//table//tr[contains(text(),'SearchElement') and contains(text(),'text') or count(.//*[contains(text(),'SearchElement') and contains(text(),'text')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new SimpleTable().getTableRow("|SearchElement|text", SearchType.CONTAINS_ANY), "//table//tr[(contains(text(),'SearchElement') or contains(text(),'text')) or count(.//*[(contains(text(),'SearchElement') or contains(text(),'text'))]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},

                {new SimpleTable().getCell(1, 1), "//table//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 1]//td[1]"},
                {new SimpleTable().getTableCell("SearchElement", SearchType.EQUALS), "//table//tr//td[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0]"},
                {new SimpleTable().getTableCell("SearchElement", SearchType.CONTAINS), "//table//tr//td[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0]"},
                {new SimpleTable().getTableCell("SearchElement", SearchType.STARTS_WITH), "//table//tr//td[starts-with(text(),'SearchElement') or count(.//*[starts-with(text(),'SearchElement')]) > 0]"},
                {new SimpleTable().getTableCell(1, 1, "SearchElement"), "//table//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][position() = 1]//td[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0][1]"},

                {new SimpleTable().getTableCell("SearchElement", "text", SearchType.EQUALS), "//table//tr[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]//td[text()='text' or count(.//*[text()='text']) > 0]"},
                {new SimpleTable().getTableCell("SearchElement", 2, SearchType.EQUALS), "//table//tr[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]//td[2]"},

                {new SimpleTable().getRow(new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][count(td[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]"},
                {new SimpleTable().getTableCell(1, new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][count(td[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]//td[1]"},
                {new SimpleTable().getTableCell(1, "111", new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][count(td[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]//td[1][text()='111' or count(.//*[text()='111']) > 0]"},
                {new SimpleTable().getRow(tableCell, new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0][count(th[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(WebLocator simpleTable, String expectedXpath) {
        Assert.assertEquals(simpleTable.getPath(), expectedXpath);
    }
}
