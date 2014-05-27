package com.sdl.selenium.web.table;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableCellTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    public static SimpleTable table = new SimpleTable().setId("ID");
    public static TableRow tableRow = new TableRow(table, "Text", SearchType.EQUALS);

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TableCell(),                       "//td"},
                {new TableCell(tableRow, 1),            "//table[@id='ID']//tr[text()='Text' or count(.//*[text()='Text']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]//td[1]"},
                {new TableCell(3, "Text", SearchType.EQUALS),        "//td[3][text()='Text' or count(.//*[text()='Text']) > 0]"},
                {new TableCell(3, "Text", SearchType.CONTAINS),  "//td[3][contains(text(),'Text') or count(.//*[contains(text(),'Text')]) > 0]"},
                {new TableCell(tableRow, 3, "Text", SearchType.EQUALS), "//table[@id='ID']//tr[text()='Text' or count(.//*[text()='Text']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0]//td[3][text()='Text' or count(.//*[text()='Text']) > 0]"},
                {new TableCell(1, "Text", SearchType.DEEP_CHILD_NODE).setTag("th"), "//th[1][count(*//text()[contains(.,'Text')]) > 0 or count(.//*[count(*//text()[contains(.,'Text')]) > 0]) > 0]"},
                {new TableCell(1, "Text", SearchType.DEEP_CHILD_NODE).setTag("td"), "//td[1][count(*//text()[contains(.,'Text')]) > 0 or count(.//*[count(*//text()[contains(.,'Text')]) > 0]) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TableCell tableCell, String expectedXpath) {
        Assert.assertEquals(tableCell.getPath(), expectedXpath);
    }
}
