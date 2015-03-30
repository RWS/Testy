package com.sdl.selenium.web.table;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableRowTest {

    private static Table table = new Table().setId("ID");
    private static TableCell tableCell = new TableCell(3, "1234", SearchType.EQUALS).setTag("th");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TableRow(),      "//tr"},
                {new TableRow().setVisibility(true),      "//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new TableRow(table), "//table[@id='ID']//tr"},
                {new TableRow(table, "Text", SearchType.EQUALS), "//table[@id='ID']//tr[(text()='Text' or count(*//text()[.='Text']) > 0)]"},
                {new TableRow(table, new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table[@id='ID']//tr[count(.//td[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]"},
                {new TableRow(table, new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)).setVisibility(true), "//table[@id='ID']//tr[count(.//td[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new TableRow(table, tableCell, new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table[@id='ID']//tr[count(.//th[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]"},
                {new TableRow(table, new TableCell(), new TableCell()), "//table[@id='ID']//tr[count(.//td) > 0 and count(.//td) > 0]"},
                {new TableRow(table, 1, new TableCell(), new TableCell()), "//table[@id='ID']//tr[count(.//td) > 0 and count(.//td) > 0][position() = 1]"},
                {new TableRow(table, 1), "//table[@id='ID']//tr[position() = 1]"},

                {new TableRow(By.visibility(true)),      "//tr[count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new TableRow(By.container(table)), "//table[@id='ID']//tr"},
                {new TableRow(table, By.text("Text", SearchType.EQUALS, SearchType.DEEP_CHILD_NODE_OR_SELF)), "//table[@id='ID']//tr[(text()='Text' or count(*//text()[.='Text']) > 0)]"},
                {new TableRow(table, By.childNodes(new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS))), "//table[@id='ID']//tr[count(.//td[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]"},
                {new TableRow(table, By.childNodes(new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), By.visibility(true)), "//table[@id='ID']//tr[count(.//td[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]"},
                {new TableRow(table, By.childNodes(tableCell, new TableCell(4, "Eng-Fra", SearchType.EQUALS))), "//table[@id='ID']//tr[count(.//th[3][(text()='1234' or count(*//text()[.='1234']) > 0)]) > 0 and count(.//td[4][(text()='Eng-Fra' or count(*//text()[.='Eng-Fra']) > 0)]) > 0]"},
                {new TableRow(table, By.childNodes(new TableCell(), new TableCell())), "//table[@id='ID']//tr[count(.//td) > 0 and count(.//td) > 0]"},
                {new TableRow(table, By.position(1), By.childNodes(new TableCell(), new TableCell())), "//table[@id='ID']//tr[count(.//td) > 0 and count(.//td) > 0][position() = 1]"},
                {new TableRow(table, By.position(1)), "//table[@id='ID']//tr[position() = 1]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TableRow tableRow, String expectedXpath) {
        Assert.assertEquals(tableRow.getPathBuilder().getPath(), expectedXpath);
    }
}
