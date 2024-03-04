package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Result;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

@Slf4j
public class GridSteps extends TestBase {
    private final Grid grid = new Grid().setTitle("Cell Editing Plants");
    private final Grid numberedRows = new Grid().setTitle("Grid with Numbered Rows");

    @Given("I open extjs6 app and add {string} path")
    public void iOpenExtjs6AppAndAddPath(String path) {
        String url = InputData.EXTJS_EXAMPLE_URL + path;
        log.info("Url: {}", url);
        driver.get(url);
        Utils.sleep(500);
        driver.navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        WebLocator mask = new WebLocator().setId("loadingSplashBottom");
        Result<Boolean> maskStatus = RetryUtils.retryUntilOneIs(Duration.ofSeconds(10), () -> !mask.isPresent());
        log.info("maskStatus: {}", maskStatus);
    }

    @Then("I verify if grid has values:")
    public void iVerifyIfGridHasValues(List<List<String>> values) {
        grid.ready(true);
        long startMs = System.currentTimeMillis();
        List<List<String>> cellsText = grid.getCellsText(t -> t == 5, getBooleanValue(), 6);
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values: ", cellsText, contains(values.toArray()));
    }

    public static Function<Cell, String> getBooleanValue() {
        return f -> {
            WebLocator check = new WebLocator(f).setClasses("x-grid-checkcolumn");
            boolean checked = check.getAttributeClass().contains("x-grid-checkcolumn-checked");
            return checked ? "true" : "false";
        };
    }

    @Then("I verify parallel if grid has values:")
    public void iVerifyParallelIfGridHasValues(List<List<String>> values) {
        long startMs = System.currentTimeMillis();
        List<List<String>> cellsText = numberedRows.getParallelValues(t -> t == 0, getBooleanValue());
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values: ", cellsText, contains(values.toArray()));
    }

    @And("I stop")
    public void iStop() {
        Utils.sleep(1);
    }

    @Then("I verify if grid has headers {list}")
    public void iVerifyIfGridHasHeaders(List<String> headers) {
        long startMs = System.currentTimeMillis();
        List<String> actualHeaders = numberedRows.getHeaders();
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values: ", actualHeaders, contains(headers.toArray()));
    }

    @Then("I verify if grid has object values:")
    public void iVerifyIfGridHasObjectValues(List<Plant> values) {
        grid.ready(true);
        long startMs = System.currentTimeMillis();
        List<Plant> cellsText = grid.getCellsValues(values.get(0), t -> t == 5, getBooleanValue(), 6);
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values", cellsText, containsInAnyOrder(values.toArray()));
    }
}
