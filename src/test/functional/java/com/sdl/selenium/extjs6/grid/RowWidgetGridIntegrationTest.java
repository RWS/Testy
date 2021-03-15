package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RowWidgetGridIntegrationTest extends TestBase {

    private final Grid spreadsheet = new Grid().setTitle("Expander rows to show company orders");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#row-widget-grid");
        driver.switchTo().frame("examples-iframe");
        spreadsheet.ready(Duration.ofSeconds(10));
        spreadsheet.ready(true);
    }

    @Test
    void getCellsTest1() {
        spreadsheet.getRow(1).expand();
        List<List<String>> cellsText = spreadsheet.getCellsText(true, 1);
        assertThat(cellsText.size(), is(100));
    }
}
