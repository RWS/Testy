package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class GridIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridIntegrationTest.class);

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

        Grid grid1 = new Grid().setHeaders("Company", "Price", "Change", "% Change", "Last Updated", "");
        grid1.assertReady();
    }

    @Test(dependsOnMethods = "headerTest")
    void selectTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#spreadsheet-checked");
        Grid spreadsheet = new Grid().setTitle("Spreadsheet");
        spreadsheet.ready(true);
        Row row = spreadsheet.getRow(new Cell(3, "1900"));
        row.select();
        assertThat(row.isSelected(), is(true));
        row.unSelect();
        assertThat(row.isSelected(), is(false));

        row = spreadsheet.getRow(new Cell(3, "2017"));
        row.select();
        assertThat(row.isSelected(), is(true));
        row.unSelect();
        assertThat(row.isSelected(), is(false));
    }

    @Test(dependsOnMethods = "selectTest")
    void scrollToCellTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#spreadsheet-checked");
        Grid spreadsheet = new Grid().setTitle("Spreadsheet");
        spreadsheet.ready(true);
        Row row = spreadsheet.getRow(new Cell(3, "2017"));
        boolean actual = row.scrollTo();
        assertThat(actual, is(true));
    }

    @Test(dependsOnMethods = "scrollToCellTest")
    void checkCellTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#cell-editing");
        Grid spreadsheet = new Grid().setTitle("Edit Plants");
        spreadsheet.ready(true);
        Cell cell = new Cell(1, "Anemone", SearchType.EQUALS);
        spreadsheet.unCheck(cell);
        assertThat(spreadsheet.isChecked(cell), is(false));

        spreadsheet.check(cell);
        assertThat(spreadsheet.isChecked(cell), is(true));

        cell = spreadsheet.getCell(5, new Cell(1, "Anemone", SearchType.EQUALS));
        cell.unCheck();
        assertThat(cell.isChecked(), is(false));

        cell.check();
        assertThat(cell.isChecked(), is(true));
    }

    //    @Test//(dependsOnMethods = "checkCellTest")
    void performanceIsCheckedTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#cell-editing");
        Grid spreadsheet = new Grid().setTitle("Edit Plants");
        spreadsheet.ready(true);

        Cell cell = spreadsheet.getCell(5, new Cell(1, "Anemone", SearchType.EQUALS));
        long startMs = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            cell.isChecked();
        }
        long endMs = System.currentTimeMillis();
        LOGGER.info("performanceIsCheckedTest took {} ms", endMs - startMs);
    }
}
