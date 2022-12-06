package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GridIntegrationWithAuthorTest extends TestBase {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GridIntegrationWithAuthorTest.class);
    private final Grid spreadsheet = new Grid().setTitle("XML Grid");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#xml-grid");
        driver.switchTo().frame("examples-iframe");
        spreadsheet.ready(Duration.ofSeconds(10));
        spreadsheet.ready(true);
    }

    @Test
    void getCellsTest() {
        long startMs = System.currentTimeMillis();
        List<Author> cellsText1 = spreadsheet.getCellsText(Author.class, 1);
        assertThat(cellsText1.size(), is(10));
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.debug("performanceIsCheckedTest took {} ms", rez);
    }

    @Test
    void getCellsValues() {
        Author author1 = new Author(null, "Master of the Game", "Warner Books", "Book");
        Author author = spreadsheet.getRow(1).getCellsValues(author1, 1);
        assertThat(author, is(author1));
    }
}
