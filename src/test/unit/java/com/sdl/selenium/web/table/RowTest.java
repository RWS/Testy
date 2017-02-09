package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RowTest {

    private static Table table = new Table().setId("ID");
    private static Cell tableCell = new Cell(3, "1234", SearchType.EQUALS).setTag("th");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Row(),      "//tr"},
                {new Row().setVisibility(true),      "//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Row(table), "//table[@id='ID']//tr"},
                {new Row(table, "Text", SearchType.EQUALS), "//table[@id='ID']//tr[(.='Text' or count(*//text()[.='Text']) > 0)]"},
                {new Row(table, new Cell(3, "1234", SearchType.EQUALS), new Cell(4, "Eng-Fra", SearchType.EQUALS)), "//table[@id='ID']//tr[count(.//td[3][(.='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(.='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]"},
                {new Row(table, new Cell(3, "1234", SearchType.EQUALS), new Cell(4, "Eng-Fra", SearchType.EQUALS)).setVisibility(true), "//table[@id='ID']//tr[count(.//td[3][(.='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(.='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new Row(table, tableCell, new Cell(4, "Eng-Fra", SearchType.EQUALS)), "//table[@id='ID']//tr[count(.//th[3][(.='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(.='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]"},
                {new Row(table, new Cell(), new Cell()), "//table[@id='ID']//tr[count(.//td) > 0 and count(.//td) > 0]"},
                {new Row(table, 1, new Cell(), new Cell()), "//table[@id='ID']//tr[count(.//td) > 0 and count(.//td) > 0][position() = 1]"},
                {new Row(table, 1), "//table[@id='ID']//tr[position() = 1]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Row tableRow, String expectedXpath) {
        Assert.assertEquals(tableRow.getXPath(), expectedXpath);
    }
}
