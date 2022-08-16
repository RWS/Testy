package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.button.Button;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RowTest {

    private static Table table = new Table().setId("ID");
    private static Cell tableCell = new Cell(3, "1234", SearchType.EQUALS).setTag("th");
    private static Button button = new Button(null, "Plateste");
    private static Cell cell = new Cell().setTemplateValue("tagAndPosition", "7").setChildNodes(button);

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
                {new Row(table, new Cell(), new Cell()), "//table[@id='ID']//tr"},
                {new Row(table, new Cell(), cell), "//table[@id='ID']//tr[count(.//td[7][count(.//button[contains(text(),'Plateste')]) > 0]) > 0]"},
                {new Row(table, 1, new Cell(), new Cell()), "//table[@id='ID']//tr[position() = 1]"},
                {new Row(table, 1), "//table[@id='ID']//tr[position() = 1]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Row tableRow, String expectedXpath) {
        assertThat(tableRow.getXPath(), equalTo(expectedXpath));
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProviderCell() {
        return new Object[][]{
                {new Row(table, tableCell).getCell(1), "//table[@id='ID']//tr[count(.//th[3][(.='1234' or count(*//text()[.='1234']) > 0)]) > 0]//td[1]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProviderCell")
    public void getPathSelectorCorrectlyFromConstructorsCell(Cell cell, String expectedXpath) {
        assertThat(cell.getXPath(), equalTo(expectedXpath));
    }
}
