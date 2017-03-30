package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.table.Cell;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class GridIntegrationTest extends TestBase {

    private Grid grid = new Grid().setTitle("Array Grid");

    @BeforeClass
    public void startTests() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#array-grid");
        grid.ready(true);
    }

    @Test
    void rowTest() {
        grid.getRow(new Cell(1, "3m Co"), new Cell(2, "$71.72")).assertReady();
        grid.getRow(1, new Cell(1, "3m Co"), new Cell(2, "$71.72")).assertReady();
        grid.getRow("3m Co").assertReady();
        grid.getRow(2).assertReady();
    }

    @Test(dependsOnMethods = "rowTest")
    void cellTest() {
        String cellValue = grid.getCell(4, new Cell(1, "3m Co"), new Cell(2, "$71.72")).getText();
        assertThat(cellValue, equalTo("0.03%"));
    }

    @Test(dependsOnMethods = "cellTest")
    void headerTest() {
        List<String> headers = grid.getHeaders();
        assertThat(headers, contains(Arrays.asList("Company", "Price", "Change", "% Change", "Last Updated").toArray()));
    }

    @Test(dependsOnMethods = "headerTest")
    void checkTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#spreadsheet-checked");
        Grid spreadsheet = new Grid().setTitle("Spreadsheet");
        spreadsheet.ready(true);
        assertThat(spreadsheet.check(new Cell(3, "1900")), is(true));
        assertThat(spreadsheet.unCheck(new Cell(3, "1900")), is(true));
    }
}
