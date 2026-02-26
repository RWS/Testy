package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.utils.Functions;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Result;
import com.sdl.selenium.web.utils.Retry;
import com.sdl.selenium.web.utils.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
public class GridSteps extends TestBase {
    private final Grid grid = new Grid().setTitle("Cell Editing Plants");
    private final Grid numberedRows = new Grid().setTitle("Grid with Numbered Rows");
    private final Grid editingRows = new Grid().setTitle("Row Editing Employees");

    @Given("I open extjs6 app and add {string} path")
    public void iOpenExtjs6AppAndAddPath(String path) {
        String url = InputData.EXTJS_EXAMPLE_URL + path;
        log.info("Url: {}", url);
        driver.get(url);
        Utils.sleep(500);
        driver.navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        WebLocator mask = new WebLocator().setId("loadingSplashBottom");
        Result<Boolean> maskStatus = Retry.retryUntilOneIs(Duration.ofSeconds(30), () -> !mask.isPresent());
        log.info("maskStatus: {}", maskStatus);
    }

    @Then("I verify if grid has values:")
    public void iVerifyIfGridHasValues(List<List<String>> values) {
        grid.ready(true);
        long startMs = System.currentTimeMillis();
        List<List<String>> cellsText = grid.getCellsText(t -> t == 5, Functions.getBooleanValue(), 6);
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values", cellsText, contains(values.toArray()));
    }


    @Then("I verify parallel if grid has values:")
    public void iVerifyParallelIfGridHasValues(List<List<String>> values) {
        long startMs = System.currentTimeMillis();
        List<List<String>> cellsText = numberedRows.getParallelValues(t -> t == 0, Functions.getBooleanValue());
//        List<List<String>> cellsText = numberedRows.getCellsText();
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values", cellsText, contains(values.toArray()));
    }

    @Then("I verify parallel if grid has size {int}")
    public void iVerifyParallelIfGridHasValues(int size) {
        long startMs = System.currentTimeMillis();
        List<List<String>> cellsText = editingRows.getParallelValues(t -> t == 5, Functions.getBooleanValue());
//        List<List<String>> cellsText = editingRows.getCellsText(t -> t == 5, getBooleanValue());
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThat("Actual values", cellsText.size(), is(size));
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
        assertThatList("Actual values", actualHeaders, contains(headers.toArray()));
    }

    @Then("I verify if grid has object values:")
    public void iVerifyIfGridHasObjectValues(List<Plant> values) {
        grid.ready(true);
        long startMs = System.currentTimeMillis();
        Options<Plant> options = new Options<>(values.get(0), t -> t == 5, Functions.getBooleanValue());
        options.setAlignment(false);
        List<Plant> cellsText = Retry.retry(2, () -> grid.getValues(options, 3, 6));
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values", cellsText, containsInAnyOrder(values.toArray()));
    }


    @Then("I verify if grid has only one row object values:")
    public void iVerifyIfGridHasOnlyOneRowObjectValues(List<Plant> values) {
        grid.ready(true);
        long startMs = System.currentTimeMillis();
        Options<Plant> options = new Options<>(values.get(0), t -> t == 5, Functions.getBooleanValue());
        options.setAlignment(false);
        options.setAuditor(Functions.auditor(values));
        List<Plant> cellsText = Retry.retry(2, () -> grid.getValues(options, 6));
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values", cellsText, containsInAnyOrder(values.toArray()));
    }
}
