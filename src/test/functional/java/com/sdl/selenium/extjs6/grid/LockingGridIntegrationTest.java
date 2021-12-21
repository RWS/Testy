package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LockingGridIntegrationTest extends TestBase {

    private final Grid grid = new Grid().setTitle("Locking Grid").setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#locking-grid");
        driver.switchTo().frame("examples-iframe");
        grid.ready(Duration.ofSeconds(10));
    }

    @Test
    void getCellTexts() {
        List<List<String>> cellsText = grid.getCellsText(1, 2);
        assertThat(cellsText.size(), is(100));
    }

    @Test
    void getCellTextsFromRow() {
        List<String> expectedList = List.of("2", "Voomm", "$41.31", "2.64", "6.83", "10/18/2021");
        List<String> cellsText = grid.getRow(2).getCellsText();
        assertThat("Actual values: " + cellsText.toString(), cellsText, containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    void getRow() {
        grid.scrollTop();
        Row row = grid.getRow(new Cell(2, "Voomm"), new Cell(5, "6.83"));
        boolean ready = row.ready();
        assertThat(ready, is(true));
    }

    @Test
    void findRow() {
        grid.scrollTop();
        Row row = grid.getRow(new Cell(2, "oo", SearchType.CONTAINS), new Cell(5, "4.02"), new Cell(6, "21"));
        String value = row.getCell(2).getText();
        assertThat(value, equalTo("Oloo"));
    }

    @Test
    void getRow1() {
        Row row = grid.getRow(new Cell(1, "2"), new Cell(2, "Voomm"));
        boolean ready = row.ready();
        assertThat(ready, is(true));
        String text = row.getCell(4).getText();
        assertThat(text, equalTo("2.64"));
    }

    @Test
    void getRowByText() {
        Row row = grid.getRow("Voomm");
        boolean ready = row.ready();
        assertThat(ready, is(true));
    }

    @Test
    void getRowSize() {
        Row row = grid.getRow(true, new Cell(6, "21", SearchType.CONTAINS));
        int size = row.size();
        assertThat(size, is(43));
    }

    @Test
    void getCell() {
        Row row = grid.getRow("Voomm");
        String text = row.getCell(3).getText();
        assertThat(text, equalTo("$41.31"));
    }
}