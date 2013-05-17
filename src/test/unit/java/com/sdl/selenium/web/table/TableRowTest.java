package com.sdl.selenium.web.table;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableRowTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    public static Table table = new Table().setId("ID");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TableRow(),             "//tr"},
                {new TableRow(table, "Text", "eq"), "//table[@id='ID']//tr[text()='Text' or count(.//*[text()='Text']) > 0]"},
                {new TableRow(table, new TableCell(3, "1234", "eq"), new TableCell(4, "Eng-Fra", "eq")), "//table[@id='ID']//tr[count(td[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]"},
                {new TableRow(table, new TableCell(), new TableCell()), "//table[@id='ID']//tr[]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TableRow tableRow, String expectedXpath) {
        Assert.assertEquals(tableRow.getPath(), expectedXpath);
    }
}
