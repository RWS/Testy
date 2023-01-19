package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.utils.Storage;
import com.sdl.selenium.web.WebLocator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.Matchers.contains;

@Slf4j
public class GridSteps extends TestBase {

    @Autowired
    private Storage storage;
    Grid grid = new Grid().setTitle("Row Editing Employees");
    ;

    @Given("I open extjs6 app and add {string} path")
    public void iOpenExtjs6AppAndAddPath(String path) {
        storage.set("email", "nige.white@sentcha.com");
        String url = InputData.EXTJS_EXAMPLE_URL + path;
        log.info("Url: {}", url);
        driver.get(url);
        driver.switchTo().frame("examples-iframe");
        grid.ready(true);
    }

    @Then("I verify if grid has values:")
    public void iVerifyIfGridHasValues(List<List<String>> values) {
        long startMs = System.currentTimeMillis();
        List<List<String>> cellsText = grid.getCellsText(t -> t == 5, getBooleanValue());
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.debug("performance took {} ms", rez);
        assertThatList("Actual values: ", cellsText, contains(values.toArray()));
    }

    private Function<Cell, String> getBooleanValue() {
        return cell -> {
            Map<String, String[]> templatesValues = cell.getPathBuilder().getTemplatesValues();
            String[] tagAndPositions = templatesValues.get("tagAndPosition");
            List<String> positions = List.of(tagAndPositions);
            if (positions.size() == 1) {
                String position = positions.get(0);
                if (position.equals("5")) {
                    WebLocator check = new WebLocator(cell).setClasses("x-grid-checkcolumn");
                    boolean checked = check.getAttributeClass().contains("x-grid-checkcolumn-checked");
                    return checked ? "true" : "false";
                }
            }
            return null;
        };
    }
}
