package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
public class GridIntegrationTest extends TestBase {

    private Grid grid = new Grid().setTitle("Basic Grid").setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#array-grid");
        driver.switchTo().frame("examples-iframe");
        grid.ready(10);
        grid.ready(true);
    }

//    @Test
//    void rowTest1() {
//        long startMs = System.currentTimeMillis();
//        String row = RetryUtils.retryIfNotSame(3, "ee", () -> grid.getRow(1).getText());
//        long endMs = System.currentTimeMillis();
//        log.info("performanceIsCheckedTest took {} ms", endMs - startMs);
//    }

    @Test
    void rowTest() {
        grid.getRow(new Cell(1, "Lajo"), new Cell(2, "$65.51")).assertReady();
        grid.getRow(1, new Cell(1, "Lajo"), new Cell(2, "$65.51")).assertReady();
        grid.getRow("Lajo").assertReady();
        grid.getRow(2).assertReady();
    }

    @Test(dependsOnMethods = "rowTest")
    void cellTest() {
        String cellValue = grid.getCell(4, new Cell(1, "Lajo"), new Cell(2, "$65.51")).getText();
        assertThat(cellValue, equalTo("2.31%"));
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
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#spreadsheet-checked");
        driver.navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        Grid spreadsheet = new Grid().setTitle("Spreadsheet");
        spreadsheet.ready(true);
        Row row = spreadsheet.getRow(new Cell(3, "1900"));
        row.select();
        assertThat(row.isSelected(), is(true));
        row.unSelect();
        assertThat(row.isSelected(), is(false));

        row = spreadsheet.getRow(new Cell(3, "1901"));
        row.select();
        assertThat(row.isSelected(), is(true));
        row.unSelect();
        assertThat(row.isSelected(), is(false));
    }

    @Test(dependsOnMethods = "selectTest")
    void scrollToCellTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#spreadsheet-checked");
        driver.switchTo().frame("examples-iframe");
        Grid spreadsheet = new Grid().setTitle("Spreadsheet");
        spreadsheet.ready(true);
        Row row = spreadsheet.getRow(new Cell(3, "2010"));
        row.ready(10);
        boolean actual = row.scrollTo();
        assertThat(actual, is(true));
    }

    @Test(dependsOnMethods = "scrollToCellTest")
    void checkCellTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#cell-editing");
        driver.navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        Grid spreadsheet = new Grid().setTitle("Cell Editing Plants");
        spreadsheet.ready(true);
        Cell cell = spreadsheet.getCell(5, new Cell(1, "Anemone", SearchType.EQUALS));
        cell.unCheck();
        assertThat(cell.isChecked(), is(false));

        cell.check();
        assertThat(cell.isChecked(), is(true));
    }

    @Test(dependsOnMethods = "checkCellTest")
    void getCellsTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#xml-grid");
//        driver.get(InputData.EXTJS_EXAMPLE_URL + "#array-grid");
//        driver.navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        Grid spreadsheet = new Grid().setTitle("XML Grid");
//        Grid spreadsheet = new Grid().setTitle("Basic Grid");
        Utils.sleep(1000);
        spreadsheet.ready(20);
        spreadsheet.ready(true);
        Utils.sleep(5000);
        long startMs = System.currentTimeMillis();
        List<List<String>> cellsText = spreadsheet.getCellsText();
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.debug("performanceIsCheckedTest took {} ms", rez);

        driver.navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        Utils.sleep(1000);
        spreadsheet.ready(20);
        spreadsheet.ready(true);
        Utils.sleep(5000);
//        spreadsheet.scrollTop();
        long startMs2 = System.currentTimeMillis();
        List<List<String>> cellsText1 = spreadsheet.getCellsText(true);
        long endMs2 = System.currentTimeMillis();
        long rez2 = endMs2 - startMs2;
        log.debug("performanceIsCheckedTest1 took {} ms", rez2);
        long o = rez2 - rez;
        log.debug("performanceIsCheckedTestFinal took {} ms", o);
        List<List<String>> expectedCellsText = Arrays.asList(
                Arrays.asList("Sidney Sheldon", "Master of the Game", "Warner Books", "Book"),
                Arrays.asList("Sidney Sheldon", "Are You Afraid of the Dark?", "Warner Books", "Book"),
                Arrays.asList("Sidney Sheldon", "If Tomorrow Comes", "Warner Books", "Book"),
                Arrays.asList("Sidney Sheldon", "Tell Me Your Dreams", "Warner Vision", "Book"),
                Arrays.asList("Sidney Sheldon", "Bloodline", "Warner Books", "Book"),
                Arrays.asList("Sidney Sheldon", "The Other Side of Me", "Warner Books", "Book"),
                Arrays.asList("Sidney Sheldon", "A Stranger in the Mirror", "Warner Books", "Book"),
                Arrays.asList("Sidney Sheldon", "The Sky Is Falling", "William Morrow & Company", "Book"),
                Arrays.asList("Sidney Sheldon", "Nothing Lasts Forever", "Warner Books", "Book"),
                Arrays.asList("Sidney Sheldon", "The Naked Face", "Warner Books", "Book")
        );
        assertThat(cellsText, contains(expectedCellsText.toArray()));


        cellsText = spreadsheet.getCellsText(1, 4);

        expectedCellsText = Arrays.asList(
                Arrays.asList("Master of the Game", "Warner Books"),
                Arrays.asList("Are You Afraid of the Dark?", "Warner Books"),
                Arrays.asList("If Tomorrow Comes", "Warner Books"),
                Arrays.asList("Tell Me Your Dreams", "Warner Vision"),
                Arrays.asList("Bloodline", "Warner Books"),
                Arrays.asList("The Other Side of Me", "Warner Books"),
                Arrays.asList("A Stranger in the Mirror", "Warner Books"),
                Arrays.asList("The Sky Is Falling", "William Morrow & Company"),
                Arrays.asList("Nothing Lasts Forever", "Warner Books"),
                Arrays.asList("The Naked Face", "Warner Books")
        );
        assertThat(cellsText, contains(expectedCellsText.toArray()));

        cellsText = spreadsheet.getCellsText(4);

        expectedCellsText = Arrays.asList(
                Arrays.asList("Sidney Sheldon", "Master of the Game", "Warner Books"),
                Arrays.asList("Sidney Sheldon", "Are You Afraid of the Dark?", "Warner Books"),
                Arrays.asList("Sidney Sheldon", "If Tomorrow Comes", "Warner Books"),
                Arrays.asList("Sidney Sheldon", "Tell Me Your Dreams", "Warner Vision"),
                Arrays.asList("Sidney Sheldon", "Bloodline", "Warner Books"),
                Arrays.asList("Sidney Sheldon", "The Other Side of Me", "Warner Books"),
                Arrays.asList("Sidney Sheldon", "A Stranger in the Mirror", "Warner Books"),
                Arrays.asList("Sidney Sheldon", "The Sky Is Falling", "William Morrow & Company"),
                Arrays.asList("Sidney Sheldon", "Nothing Lasts Forever", "Warner Books"),
                Arrays.asList("Sidney Sheldon", "The Naked Face", "Warner Books")
        );
        assertThat(cellsText, contains(expectedCellsText.toArray()));

        cellsText = spreadsheet.getCellsText(1, 3, 4);

        expectedCellsText = Arrays.asList(
                Collections.singletonList("Master of the Game"),
                Collections.singletonList("Are You Afraid of the Dark?"),
                Collections.singletonList("If Tomorrow Comes"),
                Collections.singletonList("Tell Me Your Dreams"),
                Collections.singletonList("Bloodline"),
                Collections.singletonList("The Other Side of Me"),
                Collections.singletonList("A Stranger in the Mirror"),
                Collections.singletonList("The Sky Is Falling"),
                Collections.singletonList("Nothing Lasts Forever"),
                Collections.singletonList("The Naked Face")
        );
        assertThat(cellsText, contains(expectedCellsText.toArray()));
    }

    //    @Test//(dependsOnMethods = "checkCellTest")
    void performanceIsCheckedTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#cell-editing");
        Grid spreadsheet = new Grid().setTitle("Edit Plants");
        spreadsheet.ready(true);

        Cell cell = spreadsheet.getCell(5, new Cell(1, "Anemone", SearchType.EQUALS));
        long startMs = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            cell.isChecked();
        }
        long endMs = System.currentTimeMillis();
        log.info("performanceIsCheckedTest took {} ms", endMs - startMs);
    }
}
