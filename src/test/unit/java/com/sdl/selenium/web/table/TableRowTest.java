package com.sdl.selenium.web.table;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableRowTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    public static WebLocator table = new Table().setId("ID");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TableRow(),      "//tr[not(@style='display: none;')]"},
                {new TableRow(table), "//table[@id='ID']//tr[not(@style='display: none;')]"},
                {new TableRow(table).setElPathSuffix("not(@style='display: none;')"), "//table[@id='ID']//tr[not(@style='display: none;')]"},
                {new TableRow(table, "Text", SearchType.EQUALS), "//table[@id='ID']//tr[not(@style='display: none;') and text()='Text' or count(.//*[text()='Text']) > 0]"},
                {new TableRow(table, new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)), "//table[@id='ID']//tr[count(td[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]"},
                {new TableRow(table, new TableCell(), new TableCell()), "//table[@id='ID']//tr[]"},
                {new TableRow(table, 1), "//table[@id='ID']//tr[not(@style='display: none;')][position() = 1]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TableRow tableRow, String expectedXpath) {
        Assert.assertEquals(tableRow.getPath(), expectedXpath);
    }
}
